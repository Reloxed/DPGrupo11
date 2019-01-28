package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.FixUpTask;
import domain.Observation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class ObservationServiceTest extends AbstractTest{

	@Autowired
	private ObservationService observationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FixUpTaskService fixService;

	//Tests --------------------------------------------------

	@Test
	public void testCreate(){
		super.authenticate("customer1");
		Actor principal;
		Observation result;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.observationService.create();
		result.setBody("hola chicos");

		Assert.notNull(result);
		super.authenticate(null);
	}

	@Test
	public void testSave(){
		super.authenticate("customer2");

		Observation result, saved;
		Actor principal;
		Collection<Observation> observationsBefore,observationsAfter;
		FixUpTask fixUpTask;

		observationsBefore = new ArrayList<Observation>();
		observationsAfter = new ArrayList<Observation>();

		fixUpTask = this.fixService.findOne(7219);
		Assert.notNull(fixUpTask);

		observationsBefore.addAll(fixUpTask.getObservations());


		principal= this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.observationService.create();
		Assert.notNull(result);
		result.setBody("hola chiquitines");
		result.setFixUpTask(fixUpTask);

		saved = this.observationService.save(result, true);

		observationsAfter = fixUpTask.getObservations();

		Assert.notNull(saved);
		Assert.isTrue(saved.getIsFinal() == true);
		Assert.isTrue(!observationsBefore.contains(saved));
		Assert.isTrue(observationsAfter.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testDelete(){
		super.authenticate("customer2");

		Observation result, saved;
		Actor principal;
		Collection<Observation> observationsBefore,observationsAfter;
		FixUpTask fixUpTask;

		observationsBefore = new ArrayList<Observation>();
		observationsAfter = new ArrayList<Observation>();

		fixUpTask = this.fixService.findOne(7219);
		Assert.notNull(fixUpTask);

		observationsBefore.addAll(fixUpTask.getObservations());


		principal= this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.observationService.create();
		Assert.notNull(result);
		result.setBody("hola chiquitines");
		result.setFixUpTask(fixUpTask);

		saved = this.observationService.save(result, true);
		
		observationsAfter = fixUpTask.getObservations();

		Assert.notNull(saved);
		Assert.isTrue(saved.getIsFinal() == true);
		Assert.isTrue(!observationsBefore.contains(saved));
		Assert.isTrue(observationsAfter.contains(saved));

		this.observationService.delete(saved);

		Assert.isTrue(!observationsAfter.contains(saved));

		super.authenticate(null);
	}
}
