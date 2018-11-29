
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
		Assert.isTrue(administrators.size() == 2);
		super.unauthenticate();
	}

}
