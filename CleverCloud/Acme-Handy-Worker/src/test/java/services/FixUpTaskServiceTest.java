
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	// System under test -------------------------

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


	//Tests ---------------------------------------

	//id Incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void TestNotFindOne() {
		FixUpTask result;
		result = this.fixUpTaskService.findOne(-2);
		Assert.notNull(result);
		

	}

	@Test
	public void TestFindAll() {

		Collection<FixUpTask> result;
		result = this.fixUpTaskService.findAll();
		Assert.isTrue(!result.isEmpty());
	}
	
	@Test
	public void testCreateAndSave() {
		FixUpTask result;
		Customer principal;
		FixUpTask saved;
		Calendar startMoment;
		Calendar endMoment;
		Warranty war;
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
		war = this.warrantyService.findAll().iterator().next();
		war.setIsFinal(true);
		result.setWarranty(war);
		result.setApplications(this.applicationService.findAll());
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNotSave1() {
		FixUpTask result, saved;
		Warranty war;
		
		// Warranty not final
		
		super.authenticate("customer1");
		result = this.fixUpTaskService.findAll().iterator().next();
		war = result.getWarranty();
		war.setIsFinal(false);
		result.setWarranty(war);
		saved = this.fixUpTaskService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNotDelete1() {
		Customer principal;
		FixUpTask result;

		//Customer2 trying to delete a fixUpTask of Customer1
		super.authenticate("customer2");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.findAll().iterator().next();
		result.setApplications(new ArrayList<Application>());
		this.fixUpTaskService.delete(result);
		super.unauthenticate();

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNotDelete2() {
		Customer principal;
		FixUpTask result;

		//Trying to delete a fixUpTask with applications
		super.authenticate("customer2");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.findAll().iterator().next();
		this.fixUpTaskService.delete(result);

		super.unauthenticate();

	}
	
	@Test
	public void testDelete1() {
		Customer principal;

		super.authenticate("customer2");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		Collection<FixUpTask> collFix = principal.getFixUpTasks();
		
		this.fixUpTaskService.delete(collFix.iterator().next());

		super.unauthenticate();

	}
	
	@Test
	public void testDelete10() {
		Customer principal;

		super.authenticate("customer1");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		FixUpTask fix = this.fixUpTaskService.findOne(98304);
		
		this.fixUpTaskService.delete(fix);

		super.unauthenticate();

	}

	@Test
	public void testDelete2() {
		FixUpTask result;
		Customer principal;
		Collection<FixUpTask> fixs;

		super.authenticate("customer2");

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskService.findAll().iterator().next();

		try {
			this.fixUpTaskService.delete(result);
		} catch (final RuntimeException e) {
		}

		fixs = this.fixUpTaskService.findAll();

		Assert.isTrue(fixs.contains(result));

		super.unauthenticate();

	}
	@Test
	public void TestFindApplicationsNumberOperations(){
		Double[] res;

		res=this.fixUpTaskService.findApplicationsNumberOperations();
		Assert.notNull(res);

	}

	@Test
	public void TestFindComplaintsNumberOperations(){
		Double[] res;

		res=this.fixUpTaskService.findComplaintsNumberOperations();
		Assert.notNull(res);

	}
	@Test
	public void TestFindMaxPricesNumberOperations(){
		Double[] res;

		res=this.fixUpTaskService.findMaxPricesNumberOperations();
		Assert.notNull(res);

	}

}
