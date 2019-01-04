
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.LegalText;
import domain.Ranger;
import domain.Stage;
import domain.Tag;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	@Autowired
	private TripService				tripService;

	@Autowired
	private StageService			stageService;

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private LegalTextService		legalTextService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private TagService				tagService;

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	@Test
	public void testFindAll() {
		Collection<Trip> allTrips;
		allTrips = this.tripService.findAll();
		Assert.isTrue(allTrips.size() == 6);
	}

	@Test
	public void testFindByManager() {
		Collection<Trip> result;
		super.authenticate("manager2");
		result = this.tripService.findByManager();
		Assert.isTrue(result.size() == 1);
		super.authenticate(null);
	}

	@Test
	public void testFindBySingleKeyFound() {
		Collection<Trip> result;
		result = this.tripService.findBySingleKey("trip 1");
		Assert.notEmpty(result);
		Assert.isTrue(result.size() == 1);
	}

	@Test
	public void testFindBySingleKeyNoResults() {
		Collection<Trip> result;
		result = this.tripService.findBySingleKey("asdflashdfl");
		Assert.isTrue(result.size() == 0);
	}

	@Test
	public void testSave() {
		super.authenticate("manager2");
		Trip trip, saved;
		List<String> requirements;
		Date publicationDate, startDate, endDate;
		List<Stage> stages;
		Stage stage1, stage2, stage3;
		Category category;
		Ranger ranger;
		LegalText legalText;
		Collection<Trip> trips;
		Collection<Tag> tags;
		Tag tag;

		trip = this.tripService.create();
		trip.setTitle("Trip 7");
		trip.setTitle("This is trip 7");
		trip.setDescription("This is the description");

		requirements = new ArrayList<String>();

		trip.setRequirements(requirements);

		publicationDate = new Date(1529712000000L);
		startDate = new Date(1531526400000L);
		endDate = new Date(1531872000000L);

		trip.setPublicationDate(publicationDate);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);

		stage1 = this.stageService.findOne(1196);
		stage2 = this.stageService.findOne(1197);
		stage3 = this.stageService.findOne(1198);

		stages = new ArrayList<Stage>();

		stages.add(stage2);
		stages.add(stage3);
		stages.add(stage1);

		trip.setStage(stages);

		ranger = this.rangerService.findOne(1180); //ranger3
		trip.setRanger(ranger);

		legalText = this.legalTextService.findOne(1146); // legalText2
		trip.setLegaltext(legalText);

		category = this.categoryService.findOne(1156);  // category9
		trip.setCategory(category);

		tags = new ArrayList<Tag>();
		tag = this.tagService.findOne(1207);
		tags.add(tag);

		trip.setTags(tags);

		Assert.isTrue(this.managerService.findByPrincipal().getTrips().size() == 1); //Manager2 has only one trip
		Assert.isTrue(this.rangerService.findOne(1180).getTrips().size() == 2); // Ranger3 guides 2 trips
		Assert.isTrue(this.categoryService.findOne(1156).getTrips().size() == 2); // Category9 is associated with 2 trips

		saved = this.tripService.save(trip);

		Assert.notNull(saved);

		trips = this.tripService.findAll();
		Assert.isTrue(trips.contains(saved));
		Assert.isTrue(saved.getPrice() == 121.00);
		Assert.isNull(saved.getCancellationReason());
		Assert.isTrue(this.categoryService.findOne(1156).getTrips().size() == 3); // Category9 is now associated with 3 trips
		Assert.isTrue(this.rangerService.findOne(1180).getTrips().size() == 3); // Ranger3 should now guide 3 trips
		Assert.isTrue(this.managerService.findByPrincipal().getTrips().size() == 2); //Manager2 should now have 2 trips

		final Iterator<Stage> savedStages = saved.getStage().iterator();
		Stage s1, s2, s3;
		s1 = savedStages.next();
		s2 = savedStages.next();
		s3 = savedStages.next();

		Assert.isTrue(s1.getNumber() == 1 && s2.getNumber() == 2 && s3.getNumber() == 3); // Checks that the stages have been ordered 

		super.authenticate(null);

	}

	@Test
	public void testDelete1() {
		super.authenticate("manager2");
		Trip trip;
		Collection<Trip> trips;

		trip = this.tripService.findOne(1204); // Trip6 -> managed by manager3

		try {
			this.tripService.delete(trip);
		} catch (final RuntimeException e) {
		}

		trips = this.tripService.findAll();

		Assert.isTrue(trips.contains(trip));

		super.authenticate(null);

	}

	@Test
	public void testDelete2() {
		super.authenticate("manager2");
		Trip trip;
		Collection<Trip> trips;

		trip = this.tripService.findOne(1202); // Trip4 -> managed by manager2. It's publication date is in the past

		try {
			this.tripService.delete(trip);
		} catch (final RuntimeException e) {
		}

		trips = this.tripService.findAll();

		Assert.isTrue(trips.contains(trip));

		super.authenticate(null);

	}

	@Test
	public void testDelete3() {
		super.authenticate("manager3");
		Trip trip;
		Collection<Trip> trips;

		trip = this.tripService.findOne(1204); // Trip6 -> managed by manager3. It's publication date is in the future.	

		Assert.isTrue(this.survivalClassService.findByPrincipal().size() == 1); // Manager3 has organised one survival class
																				// It's included in Trip6

		Assert.isTrue(this.categoryService.findOne(1156).getTrips().contains(trip));

		Assert.isTrue(this.rangerService.findOne(1180).getTrips().contains(trip));

		Assert.isTrue(this.managerService.findByPrincipal().getTrips().contains(trip));

		this.tripService.delete(trip);

		trips = this.tripService.findAll();

		Assert.isTrue(!trips.contains(trip));
		Assert.isTrue(this.survivalClassService.findByPrincipal().isEmpty()); // Now there are no classes associated with Manager3
		Assert.isNull(this.stageService.findOne(1195)); // The stages has been deleted too
		Assert.isTrue(!this.rangerService.findOne(1180).getTrips().contains(trip));
		Assert.isTrue(!this.managerService.findByPrincipal().getTrips().contains(trip));
		Assert.isTrue(!this.categoryService.findOne(1156).getTrips().contains(trip));

		super.authenticate(null);

		super.authenticate("sponsor3");

		Assert.isTrue(this.sponsorshipService.findByPrincipal().size() == 1); //Sponsor3 has made two sponsorships, but Trip6
																				// has been deleted, and now it has only one.

		super.authenticate(null);

	}

}
