
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	// Supported services ---------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private WarrantyService		warrantyService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		final Application a;

		a = this.applicationService.create();

		Assert.notNull(a);

		super.unauthenticate();

	}

	@Test
	public void testSave() {
		// Creating fixUpTask 
		FixUpTask result;
		Customer principal;
		FixUpTask fixUpTaskSaved;

		super.authenticate("customer2");

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.create();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		result.setDescription("descripcion");
		result.setAddress("Mairena");
		result.setStartMoment(new Date(System.currentTimeMillis() - 1));
		result.setEndMoment(new Date(203984203402L));
		result.setCategory(this.categoryService.findOne(2391));
		result.setWarranty(this.warrantyService.findOne(2415));
		result.setApplications(this.applicationService.findAll());

		fixUpTaskSaved = this.fixUpTaskService.save(result);
		Assert.notNull(fixUpTaskSaved);

		super.unauthenticate();

		super.authenticate("handyWorker1");

		Application a;
		Application saved;
		Collection<Application> applications;
		final MessageBox inBoxCustomer, inBoxHW, outBoxCustomer, outBoxHW;
		a = this.applicationService.create();

		inBoxCustomer = this.messageBoxService.findInBoxActor(this.customerService.findCustomerByApplicationId(a.getId()));
		inBoxHW = this.messageBoxService.findInBoxActor(a.getApplicant());

		a.setFixUpTask(fixUpTaskSaved);

		saved = this.applicationService.save(a);
		applications = this.applicationService.findAll();
		Assert.isTrue(applications.contains(saved));

		super.unauthenticate();
	}
	@Test
	public void testFindAll() {
		super.authenticate("handyWorker1");
		Collection<Application> applications;

		applications = this.applicationService.findAll();
		Assert.isTrue(applications.size() == 4);

		super.unauthenticate();
	}

	@Test
	public void testAccept() {
		super.authenticate("handyWorker1");
		Application a, saved;
		final FixUpTask fixUpTask;

		a = this.applicationService.create();
		a.setFixUpTask(this.fixUpTaskService.create());
		saved = this.applicationService.save(a);
		Assert.isTrue(saved.getStatus() == "PENDING");

		super.unauthenticate();

		super.authenticate("customer1");
		this.applicationService.accept(saved);
		Assert.isTrue(saved.getStatus() == "ACCEPTED");

		super.unauthenticate();
	}

	@Test
	public void testReject() {
		super.authenticate("handyWorker1");
		Application a;

		a = this.applicationService.create();
		a.setFixUpTask(this.fixUpTaskService.findOne(2427));
		this.applicationService.save(a);
		Assert.isTrue(a.getStatus() == "PENDING");
		super.unauthenticate();

		super.authenticate("customer1");
		this.applicationService.reject(a);
		Assert.isTrue(a.getStatus() == "REJECTED");

		super.unauthenticate();
	}
}
