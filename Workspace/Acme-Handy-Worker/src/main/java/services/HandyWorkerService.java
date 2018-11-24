
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Curriculum;
import domain.Finder;
import domain.HandyWorker;
import domain.MessageBox;
import domain.SocialProfile;
import domain.Tutorial;

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
	private MessageBox messageBox;
	
	//Constructor ----------------------------------------------------
	public HandyWorkerService() {
		super();
	}
	
	//Simple CRUD methods-------
	public HandyWorker findOne(int handyWorkerId){
		HandyWorker result;
		
		Assert.isTrue(handyWorkerId!=0);
		
		result = this.handyWorkerRepository.findOne(handyWorkerId);
		
		return result;
	}
	public Collection<HandyWorker> findAll(){
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();

		Assert.notNull(result);

		return result;
		
	}
	public void delete(final HandyWorker handyWorker){
		
		this.handyWorkerRepository.delete(handyWorker);
		
	}
	
	public HandyWorker save(HandyWorker handyWorker){
		HandyWorker result;
		Assert.notNull(handyWorker);
		
		if(handyWorker.getId()==0){
			Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			handyWorker.getUserAccount().setPassword(passwordEncoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
		}else{
			result=this.findByPrincipal();
			Assert.notNull(result);
		}
		result=this.handyWorkerRepository.save(handyWorker);
		
		return result;
		
	}
	
	public HandyWorker create( ){
		HandyWorker result;
		UserAccount userAccount;
		
		result=new HandyWorker();
		
		result.setIsSuspicious(false);
		result.setMessageBoxes(this.messageBox.createSystemMessageBoxes());
		result.setApplications(new HashSet<Application>());
		result.setCurriculum(new Curriculum());
		result.setFinder(new Finder());
		result.setSocialProfiles(new HashSet<SocialProfile>());
		result.setTutorial(new HashSet<Tutorial>() );
		result.setUserAccount(userAccount);
		
		
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
