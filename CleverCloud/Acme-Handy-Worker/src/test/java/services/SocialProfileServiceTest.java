package services;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SocialProfileServiceTest extends AbstractTest {
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private SocialProfileService socialProfileService;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Tests ------------------------------------------------------------------
	
	//Crear un objeto SocialProfile
	@Test
	public void testCreate(){
		SocialProfile res;
		super.authenticate("sponsor2");
		res = this.socialProfileService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}
	
	//FindAll SocialProfile
	@Test
	public void testFindAll(){
		Collection<SocialProfile> res;
		res = this.socialProfileService.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}
	
	//FindOne con ID inexistente
	@Test(expected=IllegalArgumentException.class)
	public void testFindOne2(){
		SocialProfile res;
		res = this.socialProfileService.findOne(-2);
		Assert.notNull(res);
	}
	
	//FindByPrincipal con colección vacía
	@Test
	public void testFindByPrincipal1(){
		Collection<SocialProfile> res;
		super.authenticate("handyWorker1");
		res = this.socialProfileService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 0);
		super.unauthenticate();
	}
	
	//FindByPrincipal con 2 entidades en la lista
	@Test
	public void testFindByPrincipal2(){
		Collection<SocialProfile> res;
		super.authenticate("customer2");
		res = this.socialProfileService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
		super.unauthenticate();
	}
	
	//Save correcto con el FindOne
	@Test
	public void testSaveAndFindOne1(){
		SocialProfile res;
		Actor principal;
		super.authenticate("handyWorker1");
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		SocialProfile s = this.socialProfileService.create();
		s.setNick("WalabonsoNPG");
		s.setSocialNetwork("Snapchat");
		s.setLink("http://www.snapchat.com/");
		
		res = this.socialProfileService.save(s);
		Assert.notNull(res);
		Assert.isTrue(principal.getSocialProfiles().contains(s));
		Assert.notNull(this.socialProfileService.findOne(res.getId()));
		
		super.unauthenticate();
	}
	
	Collection<SocialProfile> collSoc;
	Actor actor;
	
//	super.authenticate("handyWorker1");
//	actor = this.actorService.findByPrincipal();
//	collSoc = this.socialProfileService.findAll();
//	for(SocialProfile soc : collSoc) {
//		if (!actor.getSocialProfiles().contains(soc)) {
//			this.socialProfileService.delete(soc);
//			break;
//		}
//	}
//	super.unauthenticate();	

	//Save con uno de los campos en Blank
	@Test(expected = ConstraintViolationException.class)
	public void testSave2(){
		SocialProfile res;
		super.authenticate("handyWorker1");
		SocialProfile s = this.socialProfileService.create();
		s.setNick("WalabonsoNPG");
		s.setLink("http://www.snapchat.com/");
		res = this.socialProfileService.save(s);
		Assert.notNull(res);
		super.unauthenticate();
	}
	
	//Save con Link inválido
	@Test(expected = ConstraintViolationException.class)
	public void testSave3(){
		SocialProfile res;
		super.authenticate("handyWorker1");
		SocialProfile s = this.socialProfileService.create();
		s.setNick("WalabonsoNPG");
		s.setSocialNetwork("Snapchat");
		s.setLink("Hola");
		res = this.socialProfileService.save(s);
		Assert.notNull(res);
		super.unauthenticate();
	}
	
	//Editar el socialProfile de otro
	@Test(expected = IllegalArgumentException.class)
	public void testSave4(){
		Collection<SocialProfile> collSoc;
		Actor actor;
		
		super.authenticate("handyWorker1");
		actor = this.actorService.findByPrincipal();
		collSoc = this.socialProfileService.findAll();
		for(SocialProfile soc : collSoc) {
			if (!actor.getSocialProfiles().contains(soc)) {
				this.socialProfileService.save(soc);
				break;
			}
		}
		super.unauthenticate();	

	}
	
	//Borrar un socialProfile existente en la lista del principal
	@Test
	public void testDelete1(){
		Actor principal;
		SocialProfile profile;
		Collection<SocialProfile> s;
		super.authenticate("handyWorker2");
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		s = this.socialProfileService.findByPrincipal();
		profile = s.iterator().next();
		this.socialProfileService.delete(profile);
		s = this.socialProfileService.findByPrincipal();
		Assert.isTrue(!s.contains(profile));
		super.unauthenticate();	
	}
	
	//Borrar un socialProfile en una lista vacia
	@Test(expected = NoSuchElementException.class)
	public void testDelete2(){
		Actor principal;
		SocialProfile profile;
		Collection<SocialProfile> s;
		super.authenticate("handyWorker1");
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		s = this.socialProfileService.findByPrincipal();
		profile = s.iterator().next();
		this.socialProfileService.delete(profile);
		s = this.socialProfileService.findByPrincipal();
		super.unauthenticate();	
	}
	
	//Borrar el socialProfile de otro
	@Test(expected = IllegalArgumentException.class)
	public void testDelete3(){
		Collection<SocialProfile> collSoc;
		Actor actor;
		
		super.authenticate("handyWorker1");
		actor = this.actorService.findByPrincipal();
		collSoc = this.socialProfileService.findAll();
		for(SocialProfile soc : collSoc) {
			if (!actor.getSocialProfiles().contains(soc)) {
				this.socialProfileService.delete(soc);
				break;
			}
		}
		super.unauthenticate();	
	}
	
	//TODO DELETE AND REVIEW FOR MORE TESTS
}
