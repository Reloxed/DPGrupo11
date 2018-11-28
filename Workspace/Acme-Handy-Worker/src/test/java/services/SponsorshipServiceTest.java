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
import domain.CreditCard;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SponsorshipService sponsorshipService;

	// Supporting services -----------------------------------------------------
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private SponsorService sponsorService;

	// Tests -------------------------------------------------------------------

	// Create correcto
	@Test
	public void testCreate1() {
		super.authenticate("sponsor1");
		Sponsorship res;
		res = this.sponsorshipService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Create con un actor que no es sponsor
	@Test(expected = IllegalArgumentException.class)
	public void testCreate2() {
		super.authenticate("customer1");
		Sponsorship res;
		res = this.sponsorshipService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Create sin actor logueado
	@Test(expected = IllegalArgumentException.class)
	public void testCreate3() {
		Sponsorship res;
		res = this.sponsorshipService.create();
		Assert.notNull(res);
	}

	// FindByPrincipal correcto
	@Test
	public void testFindByPrincipal1() {
		Collection<Sponsorship> res;
		super.authenticate("sponsor1");
		res = this.sponsorshipService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
		super.unauthenticate();
	}

	// FindByPrincipal con actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		Collection<Sponsorship> res;
		super.authenticate("handyWorker1");
		res = this.sponsorshipService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
		super.unauthenticate();
	}

	// FindByPrincipal correcto
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		Collection<Sponsorship> res;
		res = this.sponsorshipService.findByPrincipal();
		Assert.notNull(res);
	}

	// FindAll correcto
	@Test
	public void testFindAll() {
		Collection<Sponsorship> res;
		res = this.sponsorshipService.findAll();
		Assert.notNull(res);
	}

	// FindOne con id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testFindOne2() {
		Sponsorship res;
		res = this.sponsorshipService.findOne(23);
		Assert.notNull(res);
	}
	
	// Save correcto
	@Test
	public void testSave1(){
		Sponsorship res;
		super.authenticate("sponsor1");
		Sponsorship s = this.sponsorshipService.create();
		s.setBanner("http://www.url.com");
		s.setTargetPage("http://www.targetpage.com");
		CreditCard cc = this.creditCardService.create();
		cc.setBrandName("VISA");
		cc.setHolderName("Mario Casasje");
		cc.setNumber("1234567890123456");
		cc.setExpirationMonth(1);
		cc.setExpirationYear(24);
		cc.setCVV(100);
		cc = this.creditCardService.save(cc);
		s.setCreditCard(cc);
		res = this.sponsorshipService.save(s);
		Assert.notNull(res);
		Assert.isTrue(res.getSponsor().getSponsorships().contains(s));
		super.unauthenticate();
	}
}
