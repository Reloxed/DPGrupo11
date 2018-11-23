package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.LoginService;
import security.UserAccount;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository ------------------------------------

	@Autowired
	private RefereeRepository refereeRepository;

	// Supporting services -----------------------------------

	// Simple CRUD methods -----------------------------------

	public Referee create() {
		return new Referee();
	}

	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(int refereeId) {
		return this.refereeRepository.findOne(refereeId);
	}

	public Referee save(Referee r) {
		return this.refereeRepository.save(r);
	}

	// TODO
	public void delete(Referee r) {
		this.refereeRepository.delete(r);
	}

	// TODO
	// Other business methods -------------------------------

	public Referee findByPrincipal() {
		Referee res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findRefereeByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Referee findRefereeByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Referee result;

		result = this.refereeRepository.findRefereeByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}
}
