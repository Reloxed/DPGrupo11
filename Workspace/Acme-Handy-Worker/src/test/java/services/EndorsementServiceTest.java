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
	
	//id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void TestFindOne(){
		Endorsement result;
		result=this.endorsementService.findOne(-2);
		Assert.notNull(result);
		
	}
	
	@Test
	public void TestFindAll(){
		Collection<Endorsement>  result;
		result=this.endorsementService.findAll();
		Assert.isTrue(!result.isEmpty());

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
		result.setSender(this.endorsementService.findAll().iterator().next().getSender());
		result.setRecipient(this.endorsementService.findAll().iterator().next().getRecipient());
		result.setComments("saludos");
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
		
		toDelete=this.endorsementService.findAll().iterator().next();
		Assert.notNull(toDelete);
		this.endorsementService.delete(toDelete);
		
		super.unauthenticate();
		
		
		
	}
	

}
