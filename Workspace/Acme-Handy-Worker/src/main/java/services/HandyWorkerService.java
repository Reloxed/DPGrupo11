
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
	private ApplicationService		applicationService;

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

	public HandyWorker save(final HandyWorker handyWorker) {
		HandyWorker result;
		Assert.notNull(handyWorker);

		if (handyWorker.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			handyWorker.getUserAccount().setPassword(passwordEncoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
		} else {
			result = this.findByPrincipal();
			Assert.notNull(result);
		}
		result = this.handyWorkerRepository.save(handyWorker);
		this.handyWorkerRepository.flush();
		return result;

	}

	public HandyWorker create() {
		HandyWorker result;
		Actor principal;
		
		principal=this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = new HandyWorker();
		
		
		result.setMessageBoxes(this.messageBoxService.createSystemMessageBoxes());
		result.setCurriculum(new Curriculum());
		result.setFinder(new Finder());
		result.setMake("");
		return result;

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
