package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private SponsorService sponsorService;
		
	// Supporting services ----------------------------------------------------
	
	// Tests ------------------------------------------------------------------
		
	//Crear un objeto sin loguear
	@Test
	public void testCreate1(){
		Sponsor res;
		res = this.sponsorService.create();
		Assert.notNull(res);
	}
	
	//Crear un objeto siendo un actor logueado
	@Test
	public void testCreate2(){
		Sponsor res;
		super.authenticate("handyWorker1");
		res = this.sponsorService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

}
