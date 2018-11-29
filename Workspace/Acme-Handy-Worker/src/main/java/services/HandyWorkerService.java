
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Curriculum;
import domain.Finder;
import domain.HandyWorker;
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
	private MessageBoxService		messageBoxService;

	@Autowired
	private ActorService		actorService;

	//Constructor ----------------------------------------------------
	public HandyWorkerService() {
		super();
	}

	//Simple CRUD methods-------
	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker result;

		Assert.isTrue(handyWorkerId != 0);

		result = this.handyWorkerRepository.findOne(handyWorkerId);

		return result;
	}
	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();

		Assert.notNull(result);

		return result;

	}
	
	public HandyWorker create() {
		HandyWorker result;
		Actor principal;
		
		Authority authority = new Authority();
		UserAccount ua = new UserAccount();
		
		authority.setAuthority(Authority.HANDYWORKER);
		ua.addAuthority(authority);
	
		
		try{
			principal=this.actorService.findByPrincipal();
			Assert.isNull(principal);
			
			return null;
			
		}catch(IllegalArgumentException e){
			
			result = new HandyWorker();
			
			result.setUserAccount(ua);
			
			result.setIsSuspicious(false);
			result.setMessageBoxes(this.messageBoxService.createSystemMessageBoxes());
			result.setApplications(new HashSet<Application>());
			result.setTutorial(new HashSet<Tutorial>());
			
			return result;
		}
		
		

	
		

	}

	public HandyWorker save(final HandyWorker handyWorker) {
		HandyWorker saved;
		Assert.notNull(handyWorker);
		
		if (handyWorker.getId() == 0) {
			try{
				Actor principal;
				principal =this.actorService.findByPrincipal();
				Assert.isNull(principal);
			} catch(IllegalArgumentException e){
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				
				handyWorker.getUserAccount()
				    .setPassword(passwordEncoder.encodePassword
						(handyWorker.getUserAccount().getPassword(),null));
			}
			
		} else {
			
			HandyWorker principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount().getId() == handyWorker.getUserAccount().getId());
			Assert.isTrue(principal.getIsSuspicious() == handyWorker.getIsSuspicious());
			handyWorker.setMake(principal.getName());
			handyWorker.setFinder(new Finder());
			handyWorker.setCurriculum(new Curriculum());
			handyWorker.setMessageBoxes(this.messageBoxService.createSystemMessageBoxes());
			
		}
		saved = this.handyWorkerRepository.saveAndFlush(handyWorker);
		
		return saved;

	}

	

	//Other business methods--------
	//handyworker can write a tutorial,a note,may endorse,
	//search in a finder ,can register a cv,apply for a fixuptask
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
	public Collection<HandyWorker> findMoreApplicationsThanAvg(){
		Collection<HandyWorker> colHandys=this.handyWorkerRepository.findMoreApplicationsThanAvg();
		return colHandys;
		
	}
	
	public Collection<HandyWorker> findTopComplaintsHandyWorkers(){
		Collection <HandyWorker> colHandys=this.handyWorkerRepository.findTopComplaintsHandyWorkers();
		return colHandys;
	}

	

}
