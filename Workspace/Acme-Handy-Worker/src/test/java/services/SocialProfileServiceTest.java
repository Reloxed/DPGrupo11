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
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SocialProfileServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private SocialProfileService socialProfileService;
	
	// Tests ------------------------------------------------------------------
	
	@Test
	public void testCreate(){
		SocialProfile res;
		super.authenticate("sponsor2");
		res = this.socialProfileService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}
	
	@Test
	public void testFindAll(){
		Collection<SocialProfile> res;
		res = this.socialProfileService.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}
	
	@Test
	public void testFindOne1(){
		SocialProfile res;
		res = this.socialProfileService.findOne(2383);
		Assert.notNull(res);
	}
	
	@Test
	public void testFindOne2(){
		SocialProfile res;
		res = this.socialProfileService.findOne(383);
		Assert.isNull(res);
	}
	
	@Test
	public void testFindByPrincipal1(){
		Collection<SocialProfile> res;
		super.authenticate("handyWorker1");
		res = this.socialProfileService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 0);
		super.unauthenticate();
	}
	
	@Test
	public void testFindByPrincipal2(){
		Collection<SocialProfile> res;
		super.authenticate("customer2");
		res = this.socialProfileService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		SocialProfile res;
		super.authenticate("handyWorker2");
		
	}

}
