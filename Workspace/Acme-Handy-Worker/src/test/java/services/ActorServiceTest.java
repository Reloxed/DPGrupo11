
package services;

import java.util.ArrayList;
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
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Customer;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Referee;
import domain.Sponsor;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private CurriculumService		curriculumService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOneAdministrator() {

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

		Actor a;

		a = this.actorService.findOne(savedAdmin.getId());

		Assert.notNull(a);
	}

	@Test
	public void testFindOneHW() {
		HandyWorker result;
		HandyWorker saved;
		super.authenticate("handyWorker1");
		result = this.handyWorkerService.findByPrincipal();
		Assert.notNull(result);

		result = this.handyWorkerService.create();

		result.getUserAccount().setUsername("Carlos");
		result.getUserAccount().setPassword("p912yp3");
		result.setIsSuspicious(false);

		final Collection<MessageBox> messages = new ArrayList<MessageBox>();
		messages.add(this.messageBoxService.findAll().iterator().next());
		result.setMessageBoxes(messages);
		result.setApplications(new HashSet<Application>());
		result.setTutorial(new HashSet<Tutorial>());
		result.setName("Lucia");
		result.setSurname("del Carmen");
		result.setEmail("hola@hola.com");
		result.setMake("pepito");
		result.setFinder(this.finderService.findAll().iterator().next());
		result.setCurriculum(this.curriculumService.findAll().iterator().next());

		
		saved = this.handyWorkerService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();

		Actor a;

		a = this.actorService.findOne(saved.getId());

		Assert.notNull(a);
	}

	@Test
	public void testFindOneCustomer() {

		final Customer customer = this.customerService.create();
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

		Actor a;

		a = this.actorService.findOne(saved.getId());

		Assert.notNull(a);
	}

	@Test
	public void testFindOneReferee() {

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

		super.unauthenticate();

		Actor a;

		a = this.actorService.findOne(saved.getId());

		Assert.notNull(a);
	}

	@Test
	public void testFindOneSponsor() {
		Sponsor res;
		final Sponsor s = this.sponsorService.create();
		s.setName("Walabonso");
		s.setSurname("Nieto-Perez Gordo");
		s.setEmail("wakawaka@us.es");
		s.getUserAccount().setUsername("WNPGG");
		s.getUserAccount().setPassword("123456abc");
		res = this.sponsorService.save(s);
		Assert.notNull(res);

		Actor a;

		a = this.actorService.findOne(res.getId());

		Assert.notNull(a);
	}

	@Test
	public void testFindByPrincipalHW() {
		super.authenticate("handyWorker1");

		Assert.notNull(this.actorService.findByPrincipal());

		super.unauthenticate();
	}

	@Test
	public void testFindByPrincipalCustomer() {
		super.authenticate("customer1");

		Assert.notNull(this.actorService.findByPrincipal());

		super.unauthenticate();
	}

	@Test
	public void testBan() {
		final Customer customer = this.customerService.create();
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
		customer.setIsSuspicious(true);
		saved = this.customerService.save(customer);

		super.unauthenticate();

		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(saved.getId()); // Actor suspicious
		this.actorService.ban(a);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBanNotSuspicious() {
		final Customer customer = this.customerService.create();
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

		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(saved.getId()); // Actor not suspicious
		this.actorService.ban(a);

		super.unauthenticate();
	}

	@Test
	public void testUnban() {

		final Customer customer = this.customerService.create();
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
		customer.setIsSuspicious(true);
		saved = this.customerService.save(customer);

		super.unauthenticate();

		super.authenticate("admin1");

		final Actor a;

		a = this.actorService.findOne(saved.getId()); // Actor suspicious
		this.actorService.ban(a);
		this.actorService.unban(a);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnbanNotBanned() {

		final Customer customer = this.customerService.create();
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

		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(saved.getId());
		this.actorService.unban(a);

		super.unauthenticate();
	}
}
