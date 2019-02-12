package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.Nust;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NustServiceTest extends AbstractTest{

	// Service under test ---------------------------------------------

		@Autowired
		private NustService	nustService;

		// Supported services ---------------------------------------------
		
		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private FixUpTaskService fixUpTaskService;
	
	@Test
	public void testCreate(){
		super.authenticate("customer1");
		Actor principal;
		Nust result;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.nustService.create();
		result.setBody("hola chicos");

		Assert.notNull(result);
		super.authenticate(null);
	}
	
	@Test
	  public void testSave(){
	    super.authenticate("customer2");

	    Nust result, saved;
	    Customer principal;
	    Collection<Nust> observationsBefore,observationsAfter;
	    FixUpTask fixUpTask;

	    observationsBefore = new ArrayList<Nust>();
	    observationsAfter = new ArrayList<Nust>();

	    fixUpTask = this.fixUpTaskService.findOne(7219);
	    Assert.notNull(fixUpTask);

	    observationsBefore.addAll(fixUpTask.getNusts());


	    principal= this.customerService.findByPrincipal();
	    Assert.notNull(principal);

	    result = this.nustService.create();
	    Assert.notNull(result);
	    result.setBody("hola chiquitines");
	    result.setFixUpTask(fixUpTask);

	    saved = this.nustService.save(result, true);

	    observationsAfter = fixUpTask.getNusts();

	    Assert.notNull(saved);
	    Assert.isTrue(saved.getIsFinal() == true);
	    Assert.isTrue(!observationsBefore.contains(saved));
	    Assert.isTrue(observationsAfter.contains(saved));

	    super.authenticate(null);
	  }
	
	@Test
	  public void testDelete(){
	    super.authenticate("customer2");

	    Nust result, saved;
	    Customer principal;
	    Collection<Nust> observationsBefore,observationsAfter;
	    FixUpTask fixUpTask;

	    observationsBefore = new ArrayList<Nust>();
	    observationsAfter = new ArrayList<Nust>();

	    fixUpTask = this.fixUpTaskService.findOne(7219);
	    Assert.notNull(fixUpTask);

	    observationsBefore.addAll(fixUpTask.getNusts());


	    principal= this.customerService.findByPrincipal();
	    Assert.notNull(principal);

	    result = this.nustService.create();
	    Assert.notNull(result);
	    result.setBody("hola chiquitines");
	    result.setFixUpTask(fixUpTask);

	    saved = this.nustService.save(result, true);
	    
	    observationsAfter = fixUpTask.getNusts();

	    Assert.notNull(saved);
	    Assert.isTrue(saved.getIsFinal() == true);
	    Assert.isTrue(!observationsBefore.contains(saved));
	    Assert.isTrue(observationsAfter.contains(saved));

	    this.nustService.delete(saved);

	    Assert.isTrue(!observationsAfter.contains(saved));

	    super.authenticate(null);
	  }

}
