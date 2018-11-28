package services;

import java.util.ArrayList;
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SponsorService sponsorService;

	// Supporting services ----------------------------------------------------

	// Tests ------------------------------------------------------------------

	// Crear un objeto sin loguear
	@Test
	public void testCreate1() {
		Sponsor res;
		res = this.sponsorService.create();
		Assert.notNull(res);
	}

	@Test
	public void testFindAll() {
		Collection<Sponsor> res;
		res = this.sponsorService.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

	// FindOne Correcto
	@Test
	public void testFindOne1() {
		Sponsor res;
		res = this.sponsorService.findOne(2312);
		Assert.notNull(res);
	}

	// FindOne con ID inexistente
	@Test(expected = IllegalArgumentException.class)
	public void testFindOne2() {
		Sponsor res;
		res = this.sponsorService.findOne(233);
		Assert.notNull(res);
	}

	// FindByPrincipal con un actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal1() {
		Sponsor res;
		super.authenticate("handyWorker1");
		res = this.sponsorService.findByPrincipal();
		Assert.notNull(res);
		System.out.println(res);
		super.unauthenticate();
	}

	// FindByPrincipal con un sponsor
	@Test
	public void testFindByPrincipal2() {
		Sponsor res;
		super.authenticate("sponsor1");
		res = this.sponsorService.findByPrincipal();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Save correcto
	@Test
	public void testSave1() {
		Sponsor res;
		Sponsor s = this.sponsorService.create();
		s.setName("Walabonso");
		s.setSurname("Nieto-Perez Gordo");
		s.setEmail("wakawaka@us.es");
		s.getUserAccount().setUsername("WNPGG");
		s.getUserAccount().setPassword("123456abc");
		res = this.sponsorService.save(s);
		Assert.notNull(res);
	}

	// Save actualizando
	@Test
	public void testSave2() throws CloneNotSupportedException {
		Sponsor res;
		super.authenticate("sponsor1");
		Sponsor s = this.sponsorService.findOne(2312);
		s.setName("Walabonso");
		s.setSurname("Nieto-Perez Gordo");
		s.setEmail("wakawaka@us.es");
		s.getUserAccount().setUsername("WNPGG");
		s.getUserAccount().setPassword("123456abc");
		res = this.sponsorService.save(s);
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Save actualizando un sponsor que no le corresponde al usuario logueado
	@Test(expected = IllegalArgumentException.class)
	public void testSave3() {
		Sponsor res;
		super.authenticate("sponsor2");
		Sponsor s = this.sponsorService.findOne(2312);
		s.setName("Walabonso");
		s.setSurname("Nieto-Perez Gordo");
		s.setEmail("wakawaka@us.es");
		s.getUserAccount().setUsername("WNPGG");
		s.getUserAccount().setPassword("123456abc");
		res = this.sponsorService.save(s);
		Assert.notNull(res);
		super.unauthenticate();
	}

	// TODO Comprobar contraseña
	 //Contraseña incorrecta
//	 @Test
//	 public void testSave4() {
//	 Sponsor res;
//	 Sponsor s = this.sponsorService.create();
//	 s.setName("Walabonso");
//	 s.setSurname("Nieto-Perez Gordo");
//	 s.setEmail("wakawaka@us.es");
//	 s.getUserAccount().setUsername("WNPGG");
//	 s.getUserAccount().setPassword("1");
//	 res = this.sponsorService.save(s);
//	 Assert.notNull(res);
//	 }

	// Intentamos modificar el isSuspicious
	@Test(expected = IllegalArgumentException.class)
	public void testSave5() throws CloneNotSupportedException {
		Sponsor res;
		super.authenticate("sponsor1");
		Sponsor s = this.sponsorService.findOne(2312);
		Sponsor clone = s.clone();
		clone.setName("Walabonso");
		clone.setSurname("Nieto-Perez Gordo");
		clone.setEmail("wakawaka@us.es");
		clone.getUserAccount().setUsername("WNPGG");
		clone.getUserAccount().setPassword("123456abc");
		clone.setIsSuspicious(true);
		s = clone;
		res = this.sponsorService.save(s);
		Assert.notNull(res);
		super.unauthenticate();
	}

	// FindByCreditCardId correcto
	@Test
	public void testFindByCreditCardId1() {
		Sponsor res;
		res = this.sponsorService.findByCreditCardId(2333);
		Assert.notNull(res);
	}

	// FindByCreditCardId incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindByCreditCardId2() {
		Sponsor res;
		res = this.sponsorService.findByCreditCardId(23);
		Assert.notNull(res);
	}

	// FindCreditCardsBySponsorId correcto
	@Test
	public void testFindCreditCardsBySponsorId1() {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC.addAll(this.sponsorService.findCreditCardsBySponsorId(2312));
		Assert.notEmpty(collCC);
		Assert.isTrue(collCC.size() == 2);
	}

	// FindCreditCardsBySponsorId incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindCreditCardsBySponsorId2() {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC.addAll(this.sponsorService.findCreditCardsBySponsorId(23));
		Assert.notEmpty(collCC);
		Assert.isTrue(collCC.size() == 2);
	}
}
