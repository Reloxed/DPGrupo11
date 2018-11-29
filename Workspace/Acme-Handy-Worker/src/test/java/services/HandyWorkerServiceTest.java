package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Application;

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
	
	@Test(expected = IllegalArgumentException.class)
	public void TestFindOne(){
		HandyWorker result;
		result=this.handyWorkerService.findOne(323);
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
		mensajes.add(this.messageBoxService.findAll().iterator().next());
		result.setMessageBoxes(mensajes);
		result.setApplications(new HashSet<Application>());
		result.setTutorial(new HashSet<Tutorial>());
		
		result.setMake("pepito");
		result.setFinder(this.finderService.findAll().iterator().next());
		result.setCurriculum(this.curriculumService.findAll().iterator().next());
		
		
		saved=this.handyWorkerService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}
	
	
	
	

	
	
	
	
}
