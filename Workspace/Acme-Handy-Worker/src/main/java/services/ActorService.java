
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------
	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private RefereeService			refereeService;


	// Simple CRUDs methods ------------------------

	public Collection<Actor> findAll() {

		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);
		if (result == null) {
			result = this.handyWorkerService.findOne(actorId);
			if (result == null) {
				result = this.customerService.findOne(actorId);
				if (result == null) {
					result = this.administratorService.findOne(actorId);
					if (result == null) {
						result = this.sponsorService.findOne(actorId);
						if (result == null)
							result = this.refereeService.findOne(actorId);
					}
				}
			}
		}
		return result;
	}

	// Other business methods ---------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Actor findByUserAccount(final UserAccount userAccount) {

		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public void ban(Actor a) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getIsSuspicious());
		Assert.isTrue(!a.getUserAccount().getIsBanned());

		a.getUserAccount().setIsBanned(true);
		a = this.actorRepository.save(a);
	}

	public void unban(Actor a) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getUserAccount().getIsBanned());

		a.getUserAccount().setIsBanned(false);
		a = this.actorRepository.save(a);
	}
}
