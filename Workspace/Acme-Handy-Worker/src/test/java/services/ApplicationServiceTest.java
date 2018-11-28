
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;

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
		super.authenticate("handyWorker1");

		Application a;
		Application saved;
		Collection<Application> applications;
		//final MessageBox inBoxCustomer, inBoxHW, outBoxCustomer, outBoxHW;
		a = this.applicationService.create();
		/*
		 * inBoxCustomer = this.messageBoxService.findInBoxActor(this.customerService.findCustomerByApplicationId(a.getId()));
		 * System.out.println("Tamaño inBoxCustomer = " + inBoxCustomer.getMessages().size());
		 * a.setFixUpTask(this.fixUpTaskService.findOne(2429));
		 */
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
