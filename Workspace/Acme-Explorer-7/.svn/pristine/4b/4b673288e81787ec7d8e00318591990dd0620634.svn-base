
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.TripRepository;
import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripRepository		tripService;

	@Autowired
	private ExplorerService		explorerService;


	// Tests

	@Test
	public void testSave() {
		super.authenticate("explorer4");

		Application application, saved;
		List<String> comments;
		final Trip trip = this.tripService.findOne(1202);  // Trip4

		application = this.applicationService.create();

		comments = new ArrayList<String>();
		comments.add("Comment 1");

		application.setComments(comments);
		application.setTrip(trip);

		Assert.isTrue(this.explorerService.findByPrincipal().getApplications().size() == 1); // Explorer4 has one application
		Assert.isTrue(this.tripService.findOne(1202).getApplications().size() == 5); //Trip4 has 5 applications

		saved = this.applicationService.save(application);

		Assert.notNull(saved);
		super.authenticate(null);
		super.authenticate("administrator1");
		Assert.isTrue(this.applicationService.findAll().contains(saved));
		super.authenticate(null);
		super.authenticate("explorer4");

		Assert.isTrue(this.explorerService.findByPrincipal().getApplications().size() == 2); // Explorer4 now has 2 applications
		Assert.isTrue(this.tripService.findOne(1202).getApplications().size() == 6); //Trip4 now has 6 applications

		Assert.isTrue(saved.getStatus().equals("PENDING"));
		super.authenticate(null);
	}

	@Test
	public void testGroupByStatus() {
		super.authenticate("explorer3");
		final Map<String, List<Application>> applicationsGroupedByStatus;

		applicationsGroupedByStatus = this.applicationService.groupByStatus();

		Assert.notNull(applicationsGroupedByStatus);
		// Explorer3 has 2 accepted applications, one rejected and one pending application
		Assert.isTrue(applicationsGroupedByStatus.get("ACCEPTED").size() == 2);
		Assert.isTrue(applicationsGroupedByStatus.get("PENDING").size() == 1);
		Assert.isTrue(applicationsGroupedByStatus.get("REJECTED").size() == 1);
		super.authenticate(null);

	}

	@Test
	public void testReject() {
		super.authenticate("manager3");
		Application application;

		application = this.applicationService.findOne(1259); // Application 13

		try {
			//	this.applicationService.reject(application, "reason");
		} catch (final RuntimeException e) {
		}

		// Application 13 is associated to Trip 4. Trip 4 is managed by manager2

		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isNull(application.getRejectionReason());
		// Nothing has changed

		super.authenticate(null);

		super.authenticate("manager2");

		//	this.applicationService.reject(application, "reason");

		Assert.isTrue(application.getStatus().equals("REJECTED"));
		Assert.notNull(application.getRejectionReason());

		super.authenticate(null);
	}

	@Test
	public void testExpect() {
		super.authenticate("manager3");
		Application application;

		application = this.applicationService.findOne(1259); // Application 13

		try {
			this.applicationService.expect(application);
		} catch (final RuntimeException e) {
		}

		// Application 13 is associated to Trip 4. Trip 4 is managed by manager2

		Assert.isTrue(application.getStatus().equals("PENDING"));
		// Nothing has changed

		super.authenticate(null);

		super.authenticate("manager2");

		this.applicationService.expect(application);

		Assert.isTrue(application.getStatus().equals("DUE"));

		super.authenticate(null);
	}

	@Test
	public void testAccept() {
		super.authenticate("explorer2");
		Application application;

		application = this.applicationService.findOne(1249); // Application 3 status=DUE, applicant = explorer2

		final CreditCard c = new CreditCard();
		c.setBrandName("MASTERCARD");
		c.setCVV(442);
		c.setExpirationMonth(12);
		c.setExpirationYear(20);
		c.setHolderName("explorer2");
		c.setNumber("372365864049173");

		Assert.isTrue(application.getStatus().equals("DUE"));

		try {
			this.applicationService.accept(application);
		} catch (final RuntimeException e) {
		}

		Assert.isTrue(application.getStatus().equals("ACCEPTED"));

		super.authenticate(null);

	}

	@Test
	public void testCancel() {
		super.authenticate("explorer3");
		Application application;

		application = this.applicationService.findOne(1252); // Application 6 status=PENDING, applicant = explorer3

		try {
			this.applicationService.cancel(application);
		} catch (final RuntimeException e) {
		}

		Assert.isTrue(!application.getStatus().equals("CANCELLED"));

		application = this.applicationService.findOne(1251); // Application 5 status=ACCEPTED, applicant = explorer3

		try {
			this.applicationService.cancel(application);
		} catch (final RuntimeException e) {
		}

		Assert.isTrue(application.getStatus().equals("CANCELLED"));

		super.authenticate(null);

	}

	@Test
	public void testFindAll() {
		super.authenticate("administrator1");
		Collection<Application> result;
		result = this.applicationService.findAll();
		Assert.notNull(result);
		// There are 14 applications in the database
		Assert.isTrue(result.size() == 14);
		super.authenticate(null);
	}

	@Test
	public void testFindOne() {
		Application result;
		result = this.applicationService.findOne(1249);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1249);
	}

}
