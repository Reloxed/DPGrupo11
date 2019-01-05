
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
import domain.Actor;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------

	@Autowired
	private WarrantyService	warrantyService;
	
	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------

	// Create correcto
	@Test
	public void testCreate1() {
		super.authenticate("admin1");
		Warranty res;
		res = this.warrantyService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Create erroneo
	@Test(expected = IllegalArgumentException.class)
	public void testCreate2() {
		super.authenticate("customer1");
		Warranty res;
		res = this.warrantyService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// FindAll correcto
	@Test
	public void testFindAll() {
		super.authenticate("admin1");
		Collection<Warranty> res;
		res = this.warrantyService.findAll();
		Assert.notEmpty(res);
		super.unauthenticate();
	}

	// Save and FindOne correctos
	@Test
	public void testSaveAndFindOne() {
		super.authenticate("admin1");
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);
		final Warranty found = this.warrantyService.findOne(res.getId());
		Assert.notNull(found);
		super.unauthenticate();
	}

	// Save con actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testNotSave_1() {
		super.authenticate("customer1");
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);
		final Warranty found = this.warrantyService.findOne(res.getId());
		Assert.notNull(found);
		super.unauthenticate();
	}

	// Save con un campo erroneo
	@Test(expected = NullPointerException.class)
	public void testNotSave_2() {
		super.authenticate("admin1");
		final Warranty w = this.warrantyService.create();
		// w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);
		final Warranty found = this.warrantyService.findOne(res.getId());
		Assert.notNull(found);
		super.unauthenticate();
	}

	// Save con spam
	@Test
	public void testSave() {
		super.authenticate("admin1");
		Actor suspicious;
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo sexo-nigeria");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);

		final Warranty found = this.warrantyService.findOne(res.getId());

		Assert.notNull(found);
		suspicious = this.actorService.findByPrincipal();
		Assert.isTrue(suspicious.getIsSuspicious());
		super.unauthenticate();
	}

	// Delete un noFinal (correcto)
	@Test
	public void testDelete1() {
		super.authenticate("admin1");
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);
		this.warrantyService.delete(res);
		Assert.isTrue(!this.warrantyService.findAll().contains(res));
		super.unauthenticate();
	}

	// Delete un Final (incorrecto)
	@Test(expected = IllegalArgumentException.class)
	public void testDelete2() {
		super.authenticate("admin1");
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		w.setIsFinal(true);
		final Warranty res = this.warrantyService.save(w);
		Assert.notNull(res);
		this.warrantyService.delete(res);
		Assert.isTrue(!this.warrantyService.findAll().contains(res));
		super.unauthenticate();
	}

	// FindAllFinals
	@Test
	public void testFindFinals() {
		super.authenticate("admin1");
		final Warranty w = this.warrantyService.create();
		w.setTitle("Titulo guapo");
		w.setTerms("Terminos de uso bonitos");
		w.setLaws("Ley 1");
		w.setIsFinal(false);
		final Warranty res = this.warrantyService.save(w);
		Warranty clone = (Warranty) res.clone();
		clone.setIsFinal(true);
		this.warrantyService.save(clone);
		super.unauthenticate();
		final Collection<Warranty> finalWarranties = this.warrantyService.findFinalWarranties();
		Assert.notEmpty(finalWarranties);
	}
}
