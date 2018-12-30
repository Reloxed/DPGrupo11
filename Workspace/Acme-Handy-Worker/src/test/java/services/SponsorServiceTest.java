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
		Assert.notNull(this.sponsorService.findOne(res.getId()));
	}

	// Save actualizando
	@Test
	public void testSave2(){
		Sponsor res;
		Sponsor forId = this.sponsorService.create();
		forId.setName("A editar");
		forId.setSurname("Editado editado");
		forId.setEmail("editar@us.es");
		forId.getUserAccount().setUsername("editanding");
		forId.getUserAccount().setPassword("123456ab");
		Sponsor theId = this.sponsorService.save(forId);
		super.authenticate(theId.getUserAccount().getUsername());
		Sponsor s = this.sponsorService.findOne(theId.getId());
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
		Sponsor forId = this.sponsorService.create();
		forId.setName("A editar");
		forId.setSurname("Editado editado");
		forId.setEmail("editar@us.es");
		forId.getUserAccount().setUsername("editanding");
		forId.getUserAccount().setPassword("123456ab");
		Sponsor theId = this.sponsorService.save(forId);
		super.authenticate("handyWorker1");
		Sponsor s = this.sponsorService.findOne(theId.getId());
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
		Sponsor forId = this.sponsorService.create();
		forId.setName("A editar");
		forId.setSurname("Editado editado");
		forId.setEmail("editar@us.es");
		forId.getUserAccount().setUsername("editanding");
		forId.getUserAccount().setPassword("123456ab");
		Sponsor theId = this.sponsorService.save(forId);
		super.authenticate(theId.getUserAccount().getUsername());
		Sponsor s = this.sponsorService.findOne(theId.getId());
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
	// IMPORTANTE: SI SE HACE UN POPULATE, PUEDE DAR ERROR PORQUE LA ID SE HA ACTUALIZADO, ESTE TEST DEBE FUNCIONAR CON UNA ID DE UNA CREDITCARD VALIDA.
//	@Test
//	public void testFindByCreditCardId1() {
//		Sponsor res;
//		res = this.sponsorService.findByCreditCardId(2487);
//		Assert.notNull(res);
//	}
//
//	// FindByCreditCardId incorrecto
//	@Test(expected = IllegalArgumentException.class)
//	public void testFindByCreditCardId2() {
//		Sponsor res;
//		res = this.sponsorService.findByCreditCardId(23);
//		Assert.notNull(res);
//	}

	// FindCreditCardsBySponsorId correcto
	// IMPORTANTE: SI SE HACE UN POPULATE, PUEDE DAR ERROR PORQUE LA ID SE HA ACTUALIZADO, ESTE TEST DEBE FUNCIONAR CON UNA ID DE UN SPONSOR QUE TENGA SPONSORSHIPS CON CREDITCARDS
	@Test
	public void testFindCreditCardsBySponsorId1() {
		Collection<CreditCard> collCC = new ArrayList<>();
		Sponsor principal;
		super.authenticate("sponsor1");
		principal = this.sponsorService.findByPrincipal();
		collCC.addAll(this.sponsorService.findCreditCardsBySponsorId(principal.getId()));
		Assert.notEmpty(collCC);
		super.unauthenticate();
	}

	// FindCreditCardsBySponsorId incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindCreditCardsBySponsorId2() {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC.addAll(this.sponsorService.findCreditCardsBySponsorId(-2));
		Assert.notEmpty(collCC);
	}
}
