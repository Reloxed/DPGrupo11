package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class EndorserServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private EndorserService endorserService;	
	
	// Tests ------------------------------------------------------------------
	
	@Test
	public void testFindAllEndorsers(){
		Collection<Endorser> endorsers;
		endorsers = this.endorserService.findAll();
		Assert.notEmpty(endorsers);
	}
	
	@Test
	public void testFindOneEndorser(){
		Collection<Endorser> endorsers;
		Endorser endorser1, endorser2;
		
		endorsers = this.endorserService.findAll();
		Assert.notEmpty(endorsers);
		endorser1 = endorsers.iterator().next();
		Assert.notNull(endorser1);
		endorser2 = this.endorserService.findOne(endorser1.getId());
		Assert.notNull(endorser2);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNotFindOneEndorser(){
		Endorser endorser, endorser0;
		endorser0 = this.endorserService.findByPrincipal();
		endorser = this.endorserService.findOne(endorser0.getId());
		Assert.notNull(endorser);
		
	}
	
	

}
