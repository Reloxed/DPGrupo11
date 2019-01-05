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
import domain.SystemConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Supporting services ----------------------------------------------------

	// Tests ------------------------------------------------------------------

	// Create correcto
	@Test
	public void testCreate1() {
		SystemConfiguration res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Create con actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testCreate2() {
		SystemConfiguration res;
		super.authenticate("customer1");
		res = this.systemConfigurationService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// FindAll correcto
	@Test
	public void testFindAll1() {
		Collection<SystemConfiguration> res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.findAll();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 1);
		super.unauthenticate();
	}

	// FindAll correcto
	@Test
	public void testFindAll2() {
		Collection<SystemConfiguration> res;
		super.authenticate("sponsor1");
		res = this.systemConfigurationService.findAll();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 1);
		super.unauthenticate();
	}

	// FindOne incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneFail() {
		SystemConfiguration res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.findOne(23);
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Save and FindOne correctos
	@Test
	public void testSaveAndFindOne() {
		SystemConfiguration res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.create();
		res = this.systemConfigurationService.save(res);
		Assert.notNull(res);
		Assert.isTrue(this.systemConfigurationService.findAll().contains(
				this.systemConfigurationService.findOne(res.getId())));
		System.out.println(convert(res));
		super.unauthenticate();
	}

	// Save con un actor que no es admin
	@Test(expected = IllegalArgumentException.class)
	public void testSave2() {
		SystemConfiguration res;
		super.authenticate("sponsor1");
		res = this.systemConfigurationService.create();
		res = this.systemConfigurationService.save(res);
		Assert.notNull(res);
		Assert.isTrue(this.systemConfigurationService.findAll().contains(
				this.systemConfigurationService.findOne(res.getId())));
		super.unauthenticate();
	}

	// Save modificando
	@Test
	public void testSave3() {
		SystemConfiguration res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.create();
		res.setMaxResults(24);
		res.setVAT(0.1);
		res = this.systemConfigurationService.save(res);
		Assert.notNull(res);
		Assert.isTrue(this.systemConfigurationService.findAll().contains(
				this.systemConfigurationService.findOne(res.getId())));
		super.unauthenticate();
	}

	// Save modificando un dato erroneo
	@Test(expected = ConstraintViolationException.class)
	public void testSave4() {
		SystemConfiguration res;
		super.authenticate("admin1");
		res = this.systemConfigurationService.create();
		res.setMaxResults(-20);
		res = this.systemConfigurationService.save(res);
		Assert.notNull(res);
		Assert.isTrue(this.systemConfigurationService.findAll().contains(
				this.systemConfigurationService.findOne(res.getId())));
		super.unauthenticate();
	}

	// FindMySystemConfiguration correcto
	@Test
	public void testFindMySystemConfiguration1() {
		SystemConfiguration res;
		super.authenticate("customer1");
		res = this.systemConfigurationService.findMySystemConfiguration();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// FindMySystemConfiguration sin nadie logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindMySystemConfiguration2() {
		SystemConfiguration res;
		res = this.systemConfigurationService.findMySystemConfiguration();
		Assert.notNull(res);
	}

	// SpamWords correcto
	@Test
	public void testSpamWords1() {
		String res;
		res = this.systemConfigurationService.findSpamWords();
		Assert.isTrue(!res.isEmpty());
	}

	public String convert(final SystemConfiguration systemConfiguration) {
		String result;

		if (systemConfiguration == null)
			result = null;
		else
			result = String.valueOf(systemConfiguration.getId());
		return result;
	}
}
