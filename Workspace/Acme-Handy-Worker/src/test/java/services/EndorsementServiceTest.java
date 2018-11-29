package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Endorsement;
import domain.Endorser;


import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class EndorsementServiceTest extends AbstractTest {
	
	// System under test -------------------------
	
	@Autowired
	private EndorsementService endorsementService;
	
	@Autowired
	private EndorserService endorserService;
	

	
	
	//Tests ---------------------------------------
	
	@Test
	public void TestFindOne(){
		Endorsement result;
		result=this.endorsementService.findOne(2425);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2425);
		
	}
	
	@Test
	public void TestFindAll(){
		Collection<Endorsement>  result;
		result=this.endorsementService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==3);
	}
	
	@Test
	public void testCreateAndSave(){
		Endorser principal;
		Endorsement result;
		
		Endorsement saved;
		
		super.authenticate("handyWorker2");
		
		principal=this.endorserService.findByPrincipal();
		Assert.notNull(principal);
		
		result=this.endorsementService.create();
		result.setSender(this.endorsementService.findOne(2425).getSender());
		result.setRecipient(this.endorsementService.findOne(2425).getRecipient());
		result.setComment("saludos");
		saved=this.endorsementService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
		
	}
	
	
	@Test
	public void testDelete(){
		Endorser principal;
		Endorsement toDelete;
		
		super.authenticate("handyWorker2");
		
		principal=this.endorserService.findByPrincipal();
		Assert.notNull(principal);
		
		toDelete=this.endorsementService.findOne(2425);
		Assert.notNull(toDelete);
		this.endorsementService.delete(toDelete);
		
		
		
		super.unauthenticate();
		
		
		
	}
	

}
