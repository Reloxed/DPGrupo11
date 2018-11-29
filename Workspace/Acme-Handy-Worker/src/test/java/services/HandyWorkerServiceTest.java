package services;


import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// System under test -------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;


	@Autowired
	private CurriculumService curriculumService;

	// Tests ---------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void TestFindOne() {
		HandyWorker result;
		result = this.handyWorkerService.findOne(323);
		Assert.notNull(result);

	}

	// objeto sin loguear
	@Test
	public void testCreate() {
		HandyWorker res;
		res = this.handyWorkerService.create();
		Assert.notNull(res);
	}

	// id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testFindOne() {
		HandyWorker res;
		res = this.handyWorkerService.findOne(23);
		Assert.notNull(res);
	}

	@Test
	public void TestFindAll() {
		Collection<HandyWorker> result;
		result = this.handyWorkerService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size() == 2);

	}

	@Test
	public void testCreateAndSave1() {
		HandyWorker result;
		HandyWorker saved;
		// super.authenticate("handyWorker1");
		// result = this.handyWorkerService.findByPrincipal();
		// Assert.notNull(result);

		result = this.handyWorkerService.create();

		result.getUserAccount().setUsername("Carlos");
		result.getUserAccount().setPassword("p912yp3");
		result.setSurname("Pepe");
		result.setEmail("carlitos@gmail.com");
		result.setName("Pepito");

		result.setMake("pepito");
		result.setCurriculum(this.curriculumService.findAll().iterator().next());

		saved = this.handyWorkerService.save(result);
		Assert.notNull(saved);

		// super.unauthenticate();
	}
	
	
	
	@Test
	public void testCreateAndSave2() {
		HandyWorker result;
		HandyWorker saved;
		super.authenticate("handyWorker1");
		result = this.handyWorkerService.findByPrincipal();
		Assert.notNull(result);

		result = this.handyWorkerService.create();

		result.getUserAccount().setUsername("Jesus");
		result.getUserAccount().setPassword("p912yp3");
		result.setSurname("Carlito");
		result.setEmail("carlitos@gmail.com");
		result.setName("Pepito");

		result.setMake("pepito");
		result.setCurriculum(this.curriculumService.findAll().iterator().next());

		saved = this.handyWorkerService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();
	}

}
