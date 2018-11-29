package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Application;
import domain.Curriculum;
import domain.Finder;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Tutorial;

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
	
	@Autowired
	private MessageBoxService messageBoxService;
	

	
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
	/*
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
	*/
	@Test
	public void testCreateAndSave(){
		HandyWorker result;
		HandyWorker saved;
		super.authenticate("handyWorker1");
		result=this.handyWorkerService.findByPrincipal();
		Assert.notNull(result);
		
		result=this.handyWorkerService.create();
		
		result.getUserAccount().setUsername("Carlos");
		result.getUserAccount().setPassword("p912yp3");
		result.setIsSuspicious(false);
		
		
		Collection <MessageBox> mensajes=new ArrayList<MessageBox>();
		mensajes.add(this.messageBoxService.findOne(2377));
		result.setMessageBoxes(mensajes);
		result.setApplications(new HashSet<Application>());
		result.setTutorial(new HashSet<Tutorial>());
		
		result.setMake("pepito");
		result.setFinder(this.finderService.findOne(2417));
		result.setCurriculum(this.curriculumService.findOne(2331));
		
		
		saved=this.handyWorkerService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}
	
	

	
	
	
	
}
