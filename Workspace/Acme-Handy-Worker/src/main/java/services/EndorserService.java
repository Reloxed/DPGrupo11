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
import domain.Actor;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {
	
	// Managed Repository
	
	@Autowired
	private EndorserRepository endorserRepository;
		
	// Supporting Services
		
	// Simple CRUD Methods
	
	public Endorser create() {
		Endorser result;
		Actor principal;

		//		principal = this.actorService.findByPrincipal();
//		Assert.isNull(principal);

		result = new Endorser();

		return result;
	}
	
	public Collection<Endorser> findAll(){
		Collection<Endorser> collEnd = new ArrayList<>();
		collEnd = endorserRepository.findAll();
		return collEnd;
	}
	
	public Endorser findOne(int endorserId) {
		Integer id = endorserId;
		Endorser endorser = endorserRepository.findOne(id);	
		return endorser;
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
}
