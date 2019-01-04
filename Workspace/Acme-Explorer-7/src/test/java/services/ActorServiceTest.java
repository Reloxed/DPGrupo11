
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ActorRepository	actorRepository;


	// Tests

	@Test
	public void testBanSuspiciousActor() {
		Actor banned;
		super.authenticate("administrator1");
		banned = this.actorService.findOne(1178);
		Assert.notNull(banned);
		Assert.isTrue(banned.getIsSuspicious());
		Assert.isTrue(banned.getUserAccount().isAccountNonLocked());
		Assert.isTrue(banned.getUserAccount().getAuthorities().size() > 0);
		this.actorService.banSuspiciousActor(1178);
		banned = this.actorRepository.findOne(1178);
		Assert.isTrue(banned.getUserAccount().getAuthorities().size() == 0);
		super.unauthenticate();
		try {
			super.authenticate("ranger1");
		} catch (final Exception e) {

		}
	}

	public void testBanNotSuspiciousActor() {
		Actor notSuspicious;
		super.authenticate("administrator1");
		notSuspicious = this.actorService.findOne(1179);
		Assert.notNull(notSuspicious);
		Assert.isTrue(!notSuspicious.getIsSuspicious());
		try {
			this.actorService.banSuspiciousActor(1179);
		} catch (final Exception e) {

		}
		super.unauthenticate();
	}

	@Test
	public void testUnBanNotBannedActor() {
		Actor notBanned;
		super.authenticate("administrator1");
		notBanned = this.actorService.findOne(1179);
		Assert.notNull(notBanned);
		Assert.isTrue(!notBanned.getIsBanned());
		try {
			this.actorService.unBanBannedActor(1179);
		} catch (final Exception e) {

		}
		super.unauthenticate();
	}

	@Test
	public void testUnBanABannedActor() {
		Actor toUnBan;
		super.authenticate("administrator1");
		toUnBan = this.actorService.findOne(1178);
		Assert.notNull(toUnBan);
		Assert.isTrue(toUnBan.getIsSuspicious());
		Assert.isTrue(!toUnBan.getIsBanned());
		this.actorService.banSuspiciousActor(1178);
		toUnBan = this.actorService.findOne(1178);
		Assert.notNull(toUnBan);
		Assert.isTrue(toUnBan.getIsBanned());
		Assert.isTrue(toUnBan.getUserAccount().getAuthorities().size() == 0);

		this.actorService.unBanBannedActor(1178);
		toUnBan = this.actorService.findOne(1178);
		Assert.notNull(toUnBan);
		Assert.isTrue(!toUnBan.getIsBanned());
		Assert.isTrue(toUnBan.getUserAccount().getAuthorities().size() > 0);
		super.unauthenticate();
	}

	@Test
	public void testy() {
		super.authenticate("administrator1");
		final Actor a = this.actorService.findOne(513);
		System.out.println(this.actorService.getAuthorityAsString(a));
		super.authenticate(null);
	}

}
