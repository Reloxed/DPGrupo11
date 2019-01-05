
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.HandyWorker;
import domain.Tutorial;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository-----------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting services ----------

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------

	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods-------
	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public HandyWorker create() {
		HandyWorker result = new HandyWorker();
		Actor principal;

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		UserAccount ua = new UserAccount();
		ua.addAuthority(authority);

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isNull(principal);

			return null;

		} catch (final IllegalArgumentException e) {

			result.setUserAccount(ua);
			result.setMake(result.getName() + result.getMiddleName() + result.getSurname());
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

		if (handyWorker.getId() == 0)
			try {
				Actor principal;
				principal = this.actorService.findByPrincipal();
				Assert.isNull(principal);
			} catch (final IllegalArgumentException e) {
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				handyWorker.getUserAccount().setPassword(passwordEncoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
			}
		else {

			HandyWorker principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount().getId() == handyWorker.getUserAccount().getId());
			Assert.isTrue(principal.getIsSuspicious() == handyWorker.getIsSuspicious());
		}
		
		saved = this.handyWorkerRepository.save(handyWorker);
		Assert.notNull(saved);
		this.handyWorkerRepository.flush();

		return saved;

	}

	// Other business methods--------

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

	public Collection<HandyWorker> findMoreApplicationsThanAvg() {
		final Collection<HandyWorker> colHandys = this.handyWorkerRepository.findMoreApplicationsThanAvg();
		return colHandys;

	}

	public Collection<HandyWorker> findTopComplaintsHandyWorkers() {
		final Collection<HandyWorker> colHandys = this.handyWorkerRepository.findTopComplaintsHandyWorkers();
		return colHandys;
	}
	
	public HandyWorker findHandyWorkerByApplicationId(int applicationId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByApplicationId(applicationId);

		return result;
	}

}
