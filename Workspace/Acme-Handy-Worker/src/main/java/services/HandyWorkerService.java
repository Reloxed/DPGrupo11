
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository-----------
	
	
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;
	
	
	
	//Supporting services ----------
	
	
	//Constructor ----------------------------------------------------
	public HandyWorkerService() {
		super();
	}
	
	//Simple CRUD methods-------
	public HandyWorker findOne(int handyWorkerId){
		HandyWorker result;
		
		result = this.handyWorkerRepository.findOne(handyWorkerId);
		
		return result;
	}
	public Collection<HandyWorker> findAll(){
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();

		Assert.notNull(result);

		return result;
		
	}
	
	
	//Other business methods--------


	public HandyWorker findByPrincipal() {
		HandyWorker res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findHandyWorkerByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public HandyWorker findHandyWorkerByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}
}
