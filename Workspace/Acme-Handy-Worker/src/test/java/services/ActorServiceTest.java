
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOneAdministrator() {
		Actor a;

		a = this.actorService.findOne(2316);

		Assert.notNull(a);
	}

	@Test
	public void testFindOneHW() {
		Actor a;

		a = this.actorService.findOne(2422);

		Assert.notNull(a);
	}

	@Test
	public void testFindOneCustomer() {
		Actor a;

		a = this.actorService.findOne(2304);

		Assert.notNull(a);
	}

	@Test
	public void testFindOneReferee() {
		Actor a;

		a = this.actorService.findOne(2308);

		Assert.notNull(a);
	}

	@Test
	public void testFindOneSponsor() {
		Actor a;

		a = this.actorService.findOne(2312);

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
		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(2423); // Actor suspicious
		this.actorService.ban(a);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBanNotSuspicious() {
		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(2422); // Actor not suspicious
		this.actorService.ban(a);

		super.unauthenticate();
	}

	@Test
	public void testUnban() {
		super.authenticate("admin1");

		final Actor a;

		a = this.actorService.findOne(2423); // Actor suspicious
		this.actorService.ban(a);
		this.actorService.unban(a);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnbanNotBanned() {
		super.authenticate("admin1");

		final Actor a;
		a = this.actorService.findOne(2422);
		this.actorService.unban(a);

		super.unauthenticate();
	}
}
