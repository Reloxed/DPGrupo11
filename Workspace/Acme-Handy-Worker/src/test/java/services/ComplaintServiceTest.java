
package services;

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
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Managed services ------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private ApplicationService	applicationService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		Complaint c;

		c = this.complaintService.create();
		Assert.notNull(c);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateFail() {
		super.authenticate("handyWorker1");
		Complaint c;

		c = this.complaintService.create();
		Assert.notNull(c);

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		// Creating fix up task

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
		result.getWarranty().setIsFinal(true);
		result.setApplications(this.applicationService.findAll());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

		// Creating complaint 

		super.authenticate("customer1");
		Complaint c, savedComplaint;

		c = this.complaintService.create();
		Assert.notNull(c);

		c.setDescription("Description");
		c.setFixUpTask(this.fixUpTaskService.findOne(saved.getId()));
		savedComplaint = this.complaintService.save(c);

		Assert.isTrue(this.complaintService.findAll().contains(savedComplaint));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveFail() {

		// Creating fix up task

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
		result.setApplications(this.applicationService.findAll());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

		// Creating complaint

		super.authenticate("handyWorker1");
		Complaint c, savedComplaint;

		c = this.complaintService.create();
		Assert.notNull(c);

		c.setDescription("Description");
		c.setFixUpTask(this.fixUpTaskService.findOne(saved.getId()));
		savedComplaint = this.complaintService.save(c);

		Assert.isTrue(this.complaintService.findAll().contains(savedComplaint));

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		super.authenticate(null);

		Assert.notNull(this.complaintService.findAll());

		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		super.authenticate(null);
		Complaint c;
		Collection<Complaint> collCom;
		
		collCom = this.complaintService.findAll();
		c = this.complaintService.findOne(collCom.iterator().next().getId());
		Assert.notNull(c);

		super.unauthenticate();
	}
}
