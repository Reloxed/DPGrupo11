
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import repositories.StoryRepository;
import domain.Administrator;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class StoryService {

	// Managed Repository

	@Autowired
	private StoryRepository			storyRepository;

	// Supporting services
	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors
	public StoryService() {
		super();
	}

	// Simple CRUD methods

	public Story create() {
		Story result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Story();
		result.setExplorer(principal);
		Assert.notNull(result);

		return result;
	}

	public Collection<Story> findAll() {
		Administrator principal;
		Collection<Story> result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.storyRepository.findAll();

		Assert.notNull(result);

		return result;

	}

	public Story findOne(final int storyId) {

		Story result;

		result = this.storyRepository.findOne(storyId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Story> findByPrincipal() {
		Collection<Story> result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getStories();

		Assert.notNull(result);

		return result;
	}

	// Other business methods

	// An actor who is authenticated as an explorer must be able to write a story and associate it with any 
	// of the trips for which they have an accepted application as long as the trip is over.
	public Story save(final Story story) {
		Explorer principal;
		final Collection<String> spamwords;
		Story result;
		Collection<Trip> paidTrips;
		Collection<String> linkAttachments;
		Trip trip;
		Date date;
		Boolean areAllLinksValid;
		Collection<Story> stories;
		Collection<Story> updated;

		Assert.notNull(story);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		paidTrips = this.tripService.ExplorerfindPaidTripsByExplorerId(principal.getId());
		Assert.notNull(paidTrips);

		trip = story.getTrip();

		Assert.isTrue(paidTrips.contains(trip));

		date = new Date(System.currentTimeMillis() - 1);

		Assert.isTrue(trip.getEndDate().before(date));

		linkAttachments = story.getLinkAttachment();

		areAllLinksValid = true;
		for (final String link : linkAttachments)
			if (!ResourceUtils.isUrl(link)) {
				areAllLinksValid = false;
				break;
			}

		Assert.isTrue(areAllLinksValid);

		result = this.storyRepository.save(story);

		stories = principal.getStories();
		updated = new ArrayList<Story>(stories);
		updated.add(result);
		principal.setStories(updated);

		stories = trip.getStories();
		updated = new ArrayList<Story>(stories);
		updated.add(result);
		trip.setStories(updated);

		spamwords = this.customisationService.find().getSpamWords();
		for (final String word : spamwords)
			if (result.getText().toLowerCase().contains(word.toLowerCase()) || result.getTitle().toLowerCase().contains(word.toLowerCase())) {

				principal.setIsSuspicious(true);
				break;
			}

		return result;

	}

	public void delete(final Story story) {
		Explorer principal;
		Trip trip;
		Collection<Story> stories;

		Assert.notNull(story);
		Assert.isTrue(story.getId() != 0);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		trip = story.getTrip();

		stories = new ArrayList<Story>(trip.getStories());

		stories.remove(story);
		trip.setStories(stories);

		stories = new ArrayList<Story>(principal.getStories());
		stories.remove(story);
		principal.setStories(stories);

		this.storyRepository.delete(story);

	}

}
