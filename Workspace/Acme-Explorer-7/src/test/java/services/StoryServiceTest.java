
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ExplorerService	explorerService;


	// Tests

	@Test
	public void testSave1() {
		super.authenticate("explorer3");

		Story story, saved = null;
		List<String> linkAttachments;
		Trip trip;

		Collection<Story> stories;

		linkAttachments = new ArrayList<String>();
		linkAttachments.add("https://linkAttachments.com/link1");
		linkAttachments.add("https://linkAttachments.com/link2");
		linkAttachments.add("https://linkAttachments.com/link3");

		trip = this.tripService.findOne(1201);   // <- Trip3

		// Explorer3 has an accepted application for Trip1 and Trip4, but not Trip3. 
		// Therefore, he shouldn't be able to create a story for Trip3

		story = this.storyService.create();
		story.setTitle("title");
		story.setText("text");
		story.setLinkAttachment(linkAttachments);
		story.setTrip(trip);

		try {
			saved = this.storyService.save(story);
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");
		stories = this.storyService.findAll();
		Assert.isTrue(!stories.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testSave2() {
		super.authenticate("explorer3");

		Story story, saved = null;
		List<String> linkAttachments;
		Trip trip;

		Collection<Story> stories;

		linkAttachments = new ArrayList<String>();
		linkAttachments.add("https://linkAttachments.com/link1");
		linkAttachments.add("https://linkAttachments.com/link2");
		linkAttachments.add("https://linkAttachments.com/link3");

		trip = this.tripService.findOne(1202); // Trip 4

		story = this.storyService.create();
		story.setTitle("title");
		story.setText("sex"); // The title contains a spam word
		story.setLinkAttachment(linkAttachments);
		story.setTrip(trip);

		Assert.isTrue(!this.explorerService.findByPrincipal().getIsSuspicious()); // Explorer 3 is not suspicious

		Assert.isTrue(this.tripService.findOne(1202).getStories().size() == 0); // Trip4 doesn't have any stories
		Assert.isTrue(this.explorerService.findByPrincipal().getStories().size() == 1); // Explorer3 has written 1 story.

		saved = this.storyService.save(story);

		super.authenticate(null);
		super.authenticate("administrator1");
		stories = this.storyService.findAll();
		Assert.isTrue(stories.contains(saved));
		super.authenticate(null);
		super.authenticate("explorer3");
		Assert.isTrue(this.tripService.findOne(1202).getStories().size() == 1); // Trip4 now has 1 story.
		Assert.isTrue(this.explorerService.findByPrincipal().getStories().size() == 2); //Explorer3 used to have 1 story. 
																						//Now he should have 2.

		Assert.isTrue(this.explorerService.findByPrincipal().getIsSuspicious()); // Now explorer3 is suspicious
		super.authenticate(null);

	}

}
