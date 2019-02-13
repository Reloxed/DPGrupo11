package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.EndorserRecord;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class EndorserRecordServiceTest extends AbstractTest {
	
	// System under test -------------------------
	
	@Autowired
	private EndorserRecordService endorserRecordService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	
	
	
	//Tests ---------------------------------------
	
	@Test
	public void testFindByPrincipal() {
		Collection<EndorserRecord> res;
		super.authenticate("handyWorker1");
		res = this.endorserRecordService.findByPrincipal();
		Assert.notNull(res);
		
		super.unauthenticate();
	}
	
	@Test
	public void  testFindAll(){
		Collection<EndorserRecord>  educationRecords;
		educationRecords=this.endorserRecordService.findAll();
		Assert.isTrue(!educationRecords.isEmpty());		
	}
	

	@Test
	public void testCreateAndSave(){
		EndorserRecord result;
		EndorserRecord saved;
		HandyWorker principal;
		super.authenticate("handyWorker1");
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result=this.endorserRecordService.create();
		result.setFullName("Jesus");
		result.setEmail("jesus9619@gmail.com");
		result.setLinkedinLink("https://www.linkedin.com/aids");
		result.setPhoneNumber("955686532");
		saved=this.endorserRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCurriculum().getEndorserRecords().contains(saved));
		super.unauthenticate();
		
	}
	
	@Test
	public void testDelete(){
		
			EndorserRecord toDelete= new EndorserRecord();
		    Collection<EndorserRecord> listEndorserRecord;
		    HandyWorker principal;
		   

		    
		    super.authenticate("handyWorker2");
		    principal = this.handyWorkerService.findByPrincipal();
		    
		    Assert.notNull(principal);
		    listEndorserRecord = principal.getCurriculum().getEndorserRecords();
		    
		    toDelete=listEndorserRecord.iterator().next();
		    
		    this.endorserRecordService.delete(toDelete);
		    
		    
		    Assert.isTrue(!listEndorserRecord.contains(toDelete));
		    
		    super.unauthenticate();
		

		
	}
	

}