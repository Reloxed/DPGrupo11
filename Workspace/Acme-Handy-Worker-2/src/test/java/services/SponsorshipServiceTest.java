package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

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
		super.unauthenticate();
	}

	// FindByPrincipal con actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		Collection<Sponsorship> res;
		super.authenticate("handyWorker1");
		res = this.sponsorshipService.findByPrincipal();
		Assert.notNull(res);
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
		res = this.sponsorshipService.findOne(-2);
		Assert.notNull(res);
	}

	// Save correcto
	@Test
	public void testSave1() {
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

	// Save por un actor que no es un Sponsor
	@Test(expected = IllegalArgumentException.class)
	public void testSave2() {
		Sponsorship res;
		super.authenticate("handyWorker1");
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

	// Save con un campo err�neo
	@Test(expected = ConstraintViolationException.class)
	public void testSave3() {
		Sponsorship res;
		super.authenticate("sponsor1");
		Sponsorship s = this.sponsorshipService.create();
		s.setBanner("url");
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

	// Save sin una creditCard
	@Test(expected = ConstraintViolationException.class)
	public void testSave4() {
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
		// s.setCreditCard(cc);
		res = this.sponsorshipService.save(s);
		Assert.notNull(res);
		Assert.isTrue(res.getSponsor().getSponsorships().contains(s));
		super.unauthenticate();
	}

	// Delete correcto
	@Test
	public void testDelete1() {
		super.authenticate("sponsor1");
		Collection<Sponsorship> res = this.sponsorshipService.findByPrincipal();
		Assert.notEmpty(res);
		Sponsorship s = this.sponsorshipService.findOne(res.iterator().next()
				.getId());
		this.sponsorshipService.delete(s);
		res = this.sponsorshipService.findByPrincipal();
		Assert.isTrue(!res.contains(s));
		super.unauthenticate();
	}

	// Delete con actor erroneo
	@Test (expected = IllegalArgumentException.class)
	public void testDelete2() {
		super.authenticate("handyWorker1");
		Collection<Sponsorship> res = this.sponsorshipService.findByPrincipal();
		Assert.notEmpty(res);
		Sponsorship s = this.sponsorshipService.findOne(res.iterator().next()
				.getId());
		this.sponsorshipService.delete(s);
		res = this.sponsorshipService.findByPrincipal();
		Assert.isTrue(!res.contains(s));
		super.unauthenticate();
	}
	
	//FindByCreditCardId correcto
	//IMPORTANTE: FUNCIONA, SIMPLEMENTE SI FALLA HAY QUE CAMBIAR LA ID DEBIDO A QUE SE HA REALIZADO UN NUEVO POPULATE.
	@Test
	public void testFindByCreditCardId1(){
		Collection<CreditCard> collCC;
		collCC = this.creditCardService.findAll();
		Collection<Sponsorship> res = this.sponsorshipService.findByCreditCardId(collCC.iterator().next().getId());
		Assert.notEmpty(res);
	}
}
