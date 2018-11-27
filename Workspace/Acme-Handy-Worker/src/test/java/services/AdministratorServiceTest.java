
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Managed services -------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@Autowired
	//private UserAccountService	userAccountService;
	@Test
	public void testCreate() {
		super.authenticate("admin1");
		Administrator a;

		a = this.administratorService.create();
		Assert.notNull(a);

		super.unauthenticate();
	}

	/*
	 * @Test
	 * public void saveTest() {
	 * 
	 * Administrator admin, savedAdmin;
	 * 
	 * final UserAccount adminAccount, savedAdminAccount;
	 * 
	 * Authority authorityAdmin;
	 * 
	 * Collection<Authority> authorities;
	 * 
	 * // Creamos un nuevo userAccount
	 * 
	 * authorities = new HashSet<>();
	 * 
	 * authorityAdmin = new Authority();
	 * 
	 * authorityAdmin.setAuthority(Authority.ADMINISTRATOR);
	 * 
	 * authorities.add(authorityAdmin);
	 * 
	 * //adminAccount = this.userAccountService.create();
	 * 
	 * adminAccount.setUsername("administratorTest");
	 * 
	 * adminAccount.setPassword("administratorTestPassword");
	 * 
	 * adminAccount.setAuthorities(authorities);
	 * 
	 * //savedAdminAccount = this.userAccountService.save(adminAccount);
	 * 
	 * // Creamos el nuevo administrador
	 * 
	 * admin = this.administratorService.create();
	 * 
	 * admin.setAddress("Calle Test Save");
	 * 
	 * admin.setEmail("admintest@gmail.com");
	 * 
	 * admin.setName("Jos�");
	 * 
	 * admin.setSurname("Calle");
	 * 
	 * admin.setPhoneNumber("955187469");
	 * 
	 * admin.setUserAccount(savedAdminAccount);
	 * 
	 * savedAdmin = this.administratorService.save(admin);
	 * 
	 * Assert.isTrue(this.actorService.findAll().contains(savedAdmin));
	 * 
	 * }
	 */
}
