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
import domain.Administrator;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private AdministratorService administratorService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindAll() {
		Collection<Referee> res;
		res = this.refereeService.findAll();
		Assert.notEmpty(res);
		Assert.isTrue(res.size() == 2);
	}

	@Test
	public void testCreateAndSave() {
		Referee res;
		Referee saved;
		Administrator principal;

		super.authenticate("admin1");
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		res = this.refereeService.create();
		res.setName("Name");
		res.setSurname("Surname");
		res.setEmail("wikiwiki@us.es");
		res.getUserAccount().setUsername("Username");
		res.getUserAccount().setPassword("12345abc");

		saved = this.refereeService.save(res);
		Assert.notNull(saved);

		res = this.refereeService.findOne(saved.getId());
		Assert.notNull(res);

		super.unauthenticate();
	}
}
