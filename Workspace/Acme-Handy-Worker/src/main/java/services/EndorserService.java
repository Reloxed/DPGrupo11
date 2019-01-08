
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorsement;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Managed Repository

	@Autowired
	private EndorserRepository	endorserRepository;


	// Supporting Services

	// Constructors ------------------------------------

	public EndorserService() {
		super();
	}

	// Simple CRUD Methods

	public Collection<Endorser> findAll() {
		Collection<Endorser> result = new ArrayList<>();
		result = this.endorserRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Endorser findOne(final int endorserId) {
		final Integer id = endorserId;
		final Endorser result = this.endorserRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Endorser findByPrincipal() {
		Endorser res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findEndorserByUserAccount(userAccount.getId());
		Assert.notNull(res);
		
		return res;
	}

	public Endorser findEndorserByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Endorser result;

		result = this.endorserRepository.findEndorserByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}


	public Collection<Endorsement> findEndorsementsByEndorser(final int endorserId) {
		Collection<Endorsement> result;
		Assert.isTrue(endorserId != 0);

		result = this.endorserRepository.findEndorsementsByEndorser(endorserId);

		Assert.notNull(result);

		return result;
	}


	

	

	public Collection<Endorsement> findEndorsementsReceivedByEndorser(final int endorserId) {
		Collection<Endorsement> result;
		Assert.isTrue(endorserId != 0);

		result = this.endorserRepository.findEndorsementsReceivedByEndorser(endorserId);

		Assert.notNull(result);
		
		return result;


}
}