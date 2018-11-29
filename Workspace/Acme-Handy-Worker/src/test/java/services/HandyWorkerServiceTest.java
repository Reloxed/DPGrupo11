package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.Finder;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// System under test -------------------------
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private FinderService finderService;
	@Autowired
	private CurriculumService curriculumService;
	

	
	//Tests ---------------------------------------
	
	@Test
	public void TestFindOne(){
		HandyWorker result;
		result=this.handyWorkerService.findOne(2423);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2423);
		
		
	}
	
	@Test
	public void TestFindAll(){
		Collection<HandyWorker> result;
		result = this.handyWorkerService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==2);
		
	}
	
	@Test
	public void testSave(){
		
	HandyWorker res;
	
	super.authenticate("handyWorker1");
	
	HandyWorker s=this.handyWorkerService.findOne(2423);
	s.setMake("pepe");
	s.setFinder(new Finder());
	s.setCurriculum(this.curriculumService.findOne(2321));
	//s.getUserAccount().setUsername("WNPGG");
	//s.getUserAccount().setPassword("123456abc");
	res = this.handyWorkerService.save(s);
	
	Assert.notNull(res);
	
	
	super.unauthenticate();
		
		
	}
	
	@Test
	public void testCreateAndSave(){
		HandyWorker result;
		HandyWorker saved;
		super.authenticate("handyWorker1");
		result=this.handyWorkerService.findByPrincipal();
		Assert.notNull(result);
		
		result=this.handyWorkerService.create();
		result.setMake("pepito");
		result.setFinder(new Finder());
		result.setCurriculum(new Curriculum());
		
		
		
		
		saved=this.handyWorkerService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}
	
	

	
	
	
	
}
