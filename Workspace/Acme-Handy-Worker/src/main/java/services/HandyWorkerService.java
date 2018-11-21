
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;

public class HandyWorkerService {

	//Managed repository-----------
	
	
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;
	
	
	
	//Supporting services ----------
	
	
	
	//Simple CRUD methods-------
	
	
	
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
