
package services;

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

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private MessageBoxService messageBoxService;
	
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
	
	public HandyWorker create( ){
		HandyWorker result;
		
		
		result=new HandyWorker();
		 
		result.setIsSuspicious(false);
		result.setMessageBoxes(this.messageBoxService.createSystemMessageBoxes());
		result.setApplications(new HashSet<Application>());
		result.setCurriculum(new Curriculum());
		result.setFinder(new Finder());
		result.setSocialProfiles(new HashSet<SocialProfile>());
		result.setTutorial(new HashSet<Tutorial>() );
		
		
		
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
