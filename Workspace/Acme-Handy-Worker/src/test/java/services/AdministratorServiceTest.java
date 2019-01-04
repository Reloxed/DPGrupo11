
package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	// Supporting services --------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private EndorserService			endorserService;

	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private CustomerService			customerService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");
		Administrator a;

		a = this.administratorService.create();
		Assert.notNull(a);

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("admin1");

		Administrator admin, savedAdmin;
		final UserAccount adminAccount;
		Authority authorityAdmin;
		Collection<Authority> authorities;

		// Creamos un nuevo userAccount

		authorities = new HashSet<>();
		authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authorityAdmin);
		adminAccount = new UserAccount();
		adminAccount.setUsername("administratorTest");
		adminAccount.setPassword("administratorTestPassword");
		adminAccount.setAuthorities(authorities);

		// Creamos el nuevo administrador

		admin = this.administratorService.create();
		admin.setAddress("Avenida 1");
		admin.setEmail("admint1@gmail.com");
		admin.setName("Lucia");
		admin.setSurname("del Carmen");
		admin.setPhoneNumber("954123456");
		admin.setUserAccount(adminAccount);
		savedAdmin = this.administratorService.save(admin);
		Assert.isTrue(this.actorService.findAll().contains(savedAdmin));

		super.unauthenticate();
	}

	// Intentando crear un administrador estando logueado con otro rol

	@Test(expected = IllegalArgumentException.class)
	public void testSaveFail() {
		super.authenticate("customer1");

		Administrator admin, savedAdmin;
		final UserAccount adminAccount;
		Authority authorityAdmin;
		Collection<Authority> authorities;

		// Creamos un nuevo userAccount

		authorities = new HashSet<>();
		authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authorityAdmin);
		adminAccount = new UserAccount();
		adminAccount.setUsername("administratorTest");
		adminAccount.setPassword("administratorTestPassword");
		adminAccount.setAuthorities(authorities);

		// Creamos el nuevo administrador

		admin = this.administratorService.create();
		admin.setAddress("Avenida 1");
		admin.setEmail("admint1@gmail.com");
		admin.setName("Lucia");
		admin.setSurname("del Carmen");
		admin.setPhoneNumber("954123456");
		admin.setUserAccount(adminAccount);
		savedAdmin = this.administratorService.save(admin);
		Assert.isTrue(this.actorService.findAll().contains(savedAdmin));

		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		super.authenticate("admin1");

		Administrator admin, savedAdmin, found;
		final UserAccount adminAccount;
		Authority authorityAdmin;
		Collection<Authority> authorities;

		// Creamos un nuevo userAccount

		authorities = new HashSet<>();
		authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMINISTRATOR);
		authorities.add(authorityAdmin);
		adminAccount = new UserAccount();
		adminAccount.setUsername("administratorTest");
		adminAccount.setPassword("administratorTestPassword");
		adminAccount.setAuthorities(authorities);

		// Creamos el nuevo administrador

		admin = this.administratorService.create();
		admin.setAddress("Avenida 1");
		admin.setEmail("admint1@gmail.com");
		admin.setName("Lucia");
		admin.setSurname("del Carmen");
		admin.setPhoneNumber("954123456");
		admin.setUserAccount(adminAccount);
		savedAdmin = this.administratorService.save(admin);
		Assert.isTrue(this.actorService.findAll().contains(savedAdmin));

		found = this.administratorService.findOne(savedAdmin.getId());
		Assert.isTrue(found.getName().equals("Lucia"));
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		super.authenticate("admin1");
		Collection<Administrator> administrators = null;

		administrators = this.administratorService.findAll();

		Assert.notNull(administrators);
		super.unauthenticate();
	}

	@Test
	public void testCalculateScore() {
		// Creating customer
		Customer customer = this.customerService.create();
		Customer saved;
		final String username = "customer1";

		super.authenticate(username);

		customer.setName("Pedro");
		customer.setSurname("Picapiedra");
		customer.setEmail("pedropicapiedra@yahoo.es");
		customer.setMiddleName("Vientos");
		customer.setAddress("Calle mi casa 28");
		customer.getUserAccount().setUsername("Pedro28");
		customer.getUserAccount().setPassword("adsadd252f");
		saved = this.customerService.save(customer);
		super.unauthenticate();
		customer = this.customerService.findOne(saved.getId());
		Assert.notNull(customer);

		// Creating endorsement to customer
		Endorser principal;
		Endorsement result;

		Endorsement saved1;

		super.authenticate("handyWorker2");

		principal = this.endorserService.findByPrincipal();
		Assert.notNull(principal);

		result = this.endorsementService.create();
		result.setSender(principal);
		result.setRecipient(customer);
		result.setComments("amazing amazing bad");
		saved1 = this.endorsementService.save(result);
		Assert.notNull(saved1);
		super.unauthenticate();

		super.authenticate("admin1");
		Endorser endorser;
		Double score;

		endorser = customer;
		score = this.administratorService.calculateScore(endorser);
		/**
		 * Positive words: 2
		 * Negative words: 1
		 * Score hoped = (2 - 1) / (2 + 1) = 0.3333333333333
		 */
		Assert.isTrue(score == 0.3333333333333333);
		super.unauthenticate();
	}
}
