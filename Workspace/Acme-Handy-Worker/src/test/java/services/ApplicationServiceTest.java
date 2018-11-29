
package services;

import java.util.ArrayList;
import java.util.Calendar;
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
import domain.CreditCard;
import domain.Customer;
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
	private CategoryService		categoryService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CreditCardService	creditCardService;


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
		FixUpTask saved;
		Calendar startMoment;
		Calendar endMoment;
		super.authenticate("customer2");

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.create();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		result.setDescription("descripcion");
		result.setAddress("Mairena");

		startMoment = Calendar.getInstance();
		startMoment.set(2019, 8, 22);
		endMoment = Calendar.getInstance();
		endMoment.set(2020, 8, 22);

		result.setStartMoment(startMoment.getTime());
		result.setEndMoment(endMoment.getTime());
		result.setCategory(this.categoryService.findAll().iterator().next());
		result.setWarranty(this.warrantyService.findAll().iterator().next());
		result.setApplications(new ArrayList<Application>());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

		super.authenticate("handyWorker1");

		Application a;
		Application apSaved;
		Collection<Application> applications;
		a = this.applicationService.create();

		a.setFixUpTask(saved);

		apSaved = this.applicationService.save(a);
		applications = this.applicationService.findAll();
		Assert.isTrue(applications.contains(apSaved));

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

		// Creating fixUpTask 
		FixUpTask result;
		Customer principal;
		FixUpTask saved;
		Calendar startMoment;
		Calendar endMoment;
		super.authenticate("customer2");

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.create();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		result.setDescription("descripcion");
		result.setAddress("Mairena");

		startMoment = Calendar.getInstance();
		startMoment.set(2019, 8, 22);
		endMoment = Calendar.getInstance();
		endMoment.set(2020, 8, 22);

		result.setStartMoment(startMoment.getTime());
		result.setEndMoment(endMoment.getTime());
		result.setCategory(this.categoryService.findAll().iterator().next());
		result.setWarranty(this.warrantyService.findAll().iterator().next());
		result.setApplications(new ArrayList<Application>());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

		super.authenticate("customer1");
		CreditCard savedCC;
		final CreditCard creditcard = this.creditCardService.create();

		creditcard.setHolderName("Pedro Picapiedra");
		creditcard.setBrandName("VISA");
		creditcard.setNumber("8731648964261256");
		creditcard.setExpirationMonth(12);
		creditcard.setExpirationYear(21);
		creditcard.setCVV(187);
		System.out.println(creditcard);
		savedCC = this.creditCardService.save(creditcard);
		System.out.println(savedCC);
		Assert.notNull(savedCC);

		super.authenticate("handyWorker1");

		Application a, apSaved;

		a = this.applicationService.create();
		a.setFixUpTask(saved);
		apSaved = this.applicationService.save(a);
		Assert.isTrue(apSaved.getStatus() == "PENDING");

		super.unauthenticate();

		super.authenticate("customer1");
		this.applicationService.accept(apSaved, savedCC);
		Assert.isTrue(apSaved.getStatus() == "ACCEPTED");

		super.unauthenticate();
	}

	@Test
	public void testReject() {
		// Creating fixUpTask 
		FixUpTask result;
		Customer principal;
		FixUpTask saved;
		Calendar startMoment;
		Calendar endMoment;
		super.authenticate("customer2");

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.create();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		result.setDescription("descripcion");
		result.setAddress("Mairena");

		startMoment = Calendar.getInstance();
		startMoment.set(2019, 8, 22);
		endMoment = Calendar.getInstance();
		endMoment.set(2020, 8, 22);

		result.setStartMoment(startMoment.getTime());
		result.setEndMoment(endMoment.getTime());
		result.setCategory(this.categoryService.findAll().iterator().next());
		result.setWarranty(this.warrantyService.findAll().iterator().next());
		result.setApplications(new ArrayList<Application>());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

		super.authenticate("handyWorker1");
		Application a, apSaved;

		a = this.applicationService.create();
		a.setFixUpTask(saved);
		apSaved = this.applicationService.save(a);
		Assert.isTrue(apSaved.getStatus() == "PENDING");

		super.unauthenticate();

		super.authenticate("customer2");
		this.applicationService.reject(apSaved);
		Assert.isTrue(apSaved.getStatus() == "REJECTED");

		super.unauthenticate();
	}
}
