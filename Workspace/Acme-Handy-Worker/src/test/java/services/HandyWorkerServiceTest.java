package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
	public void testCreateAndSave(){
	
		
		
	}
	
	
	@Test
	public void testDelete(){
		
		
		
		
	}
	
	
	
	
}
