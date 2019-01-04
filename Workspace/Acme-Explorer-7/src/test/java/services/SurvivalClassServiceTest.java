package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;


import domain.Explorer;
import domain.Location;
import domain.SurvivalClass;
import domain.Trip;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	
	@Autowired
	private SurvivalClassService survivalClassService;
	
	@Autowired
	private ExplorerService explorerService; 
	
	@Autowired
	private TripService tripService; 
	
	@Autowired
	private SurvivalClassRepository survivalClassRepository;
	

	
	// Tests
	
	@Test
	public void testFindByPrincipal1() {
		super.authenticate("auditor3");
		Collection<SurvivalClass> result = null;
		try{
			result = this.survivalClassService.findByPrincipal();
		} catch (RuntimeException e) {}
		
		Assert.isNull(result);
		super.authenticate(null);
	}
	
	
	@Test
	public void testFindByPrincipal2() {
		super.authenticate("manager2");
		Collection<SurvivalClass> result = null;
		try{
			result = this.survivalClassService.findByPrincipal();
		} catch (RuntimeException e) {}
		
		Assert.isTrue(result.size() == 2);
		super.authenticate(null);
		
	}
	
	
	@Test
	public void testSave1() {
		super.authenticate("manager1");
		
		SurvivalClass survivalClass, saved = null;
		Location location;
		Date date;
		Trip trip;
		Collection<Explorer> explorers;
		
		Collection<SurvivalClass> classes;

		
		location = new Location();
		location.setName("Sevilla");
		location.setLatitude(65.984);
		location.setLongitude(54.324);
		
		date = new Date(1544713200000L); // 13/12/2018 15:00h
		
		trip = this.tripService.findOne(1203);  // Trip 5
		
		explorers = new ArrayList<Explorer>();
		
		survivalClass = new SurvivalClass();
		survivalClass.setTitle("title");
		survivalClass.setDescription("description");
		survivalClass.setTrip(trip);
		survivalClass.setPlace(location);
		survivalClass.setMoment(date);
		survivalClass.setExplorers(explorers);
		
		// Manager1 organises Trip1, Trip2 y Trip3, but not Trip5. Therefore, the survival class won't be created
		try {
		saved = this.survivalClassService.save(survivalClass);
		} catch (RuntimeException e) {}
		
		classes = this.survivalClassRepository.findAll();
		Assert.isTrue(!classes.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testSave2() {
		super.authenticate("manager3");
		
		SurvivalClass survivalClass, saved = null;
		Location location;
		Date date;
		Trip trip;
		Collection<Explorer> explorers;
		Explorer e1;
		
		
		Collection<SurvivalClass> classes;

		
		location = new Location();
		location.setName("Sevilla");
		location.setLatitude(65.984);
		location.setLongitude(54.324);
		
		
		date = new Date(1544713200000L); // 13/12/2018 15:00h
		
		trip = this.tripService.findOne(1203);  // Trip 5
		
		explorers = new ArrayList<Explorer>();
		e1 = this.explorerService.findOne(1242); // Explorer1
		explorers.add(e1);
		
		survivalClass = new SurvivalClass();
		survivalClass.setTitle("title");
		survivalClass.setDescription("description");
		survivalClass.setTrip(trip);
		survivalClass.setPlace(location);
		survivalClass.setMoment(date);
		survivalClass.setExplorers(explorers);
		
		// Explorer1 doesn't have accepted application for Trip5. The class won't be created
		try {
		saved = this.survivalClassService.save(survivalClass);
		} catch (RuntimeException e) {}
		classes = this.survivalClassRepository.findAll();
		Assert.isTrue(!classes.contains(saved));
		super.authenticate(null);
	}
	
	
	@Test
	public void testSave3() {
		super.authenticate("manager3");
		
		SurvivalClass survivalClass, saved = null;
		Location location;
		Date date;
		Trip trip;
		Collection<Explorer> explorers;
		Explorer e1;
		
		
		Collection<SurvivalClass> classes;

		
		location = new Location();
		location.setName("Sevilla");
		location.setLatitude(65.984);
		location.setLongitude(54.324);
		
		
		date = new Date(1544713200000L); // 13/12/2018 15:00h
		
		trip = this.tripService.findOne(1203);
		
		explorers = new ArrayList<Explorer>();
		e1 = this.explorerService.findOne(1243); // Explorer 2
		explorers.add(e1);
		
		survivalClass = new SurvivalClass();
		survivalClass.setTitle("title");
		survivalClass.setDescription("description");
		survivalClass.setTrip(trip);
		survivalClass.setPlace(location);
		survivalClass.setMoment(date);
		survivalClass.setExplorers(explorers);
		
		try {
		saved = this.survivalClassService.save(survivalClass);
		} catch (RuntimeException e) {}
		classes = this.survivalClassRepository.findAll();
		Assert.isTrue(classes.contains(saved));
		Assert.isTrue(this.tripService.findOne(1203).getSurvivalClasses().size() == 1);
		Assert.isTrue(this.explorerService.findOne(1243).getSurvivalClasses().size() == 1);
		super.authenticate(null);
	}
	
	
	@Test
	public void testDelete1() {
		super.authenticate("explorer2");
		SurvivalClass sc = this.survivalClassRepository.findOne(1219); // Survival Class 3 (organised by Manager 1)
		try {
		survivalClassService.delete(sc);
		} 
		catch (RuntimeException e) {};
		Assert.isTrue(this.survivalClassRepository.findAll().contains(sc));
		super.authenticate(null);	
	}
	
	@Test
	public void testDelete2() {
		super.authenticate("manager2");
		SurvivalClass sc = this.survivalClassRepository.findOne(1219); // Survival Class 3 (organised by Manager 1)
		try {
		survivalClassService.delete(sc);
		} 
		catch (RuntimeException e) {};
		Assert.isTrue(this.survivalClassRepository.findAll().contains(sc));
		super.authenticate(null);	
	}
	
	
	@Test
	public void testDelete3() {
		super.authenticate("manager1");
		SurvivalClass sc = this.survivalClassRepository.findOne(1219);  // Survival Class 3 (organised by Manager 1)
		try {
		survivalClassService.delete(sc);
		} 
		catch (RuntimeException e) {};
		Assert.isTrue(!this.survivalClassRepository.findAll().contains(sc));
		
		                                                               
		                                                              

		super.authenticate(null);	
	}
	
	
	

	
	

}
