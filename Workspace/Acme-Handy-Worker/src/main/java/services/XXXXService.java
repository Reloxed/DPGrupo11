package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.XXXXRepository;
import domain.Actor;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.XXXX;

@Service
@Transactional
public class XXXXService {

	// Managed repository ------------------------------------

	@Autowired
	private XXXXRepository XXXXRepository;

	// Supporting services -----------------------------------

	// @Autowired
	// private FixUpTaskService fixUpTaskService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private UtilityService utilityService;

	// Constructors ------------------------------------

	public XXXXService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public XXXX create() {
		Actor principal;
		XXXX res;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.registered");
		Assert.isTrue(principal instanceof HandyWorker, "not.allowed");

		res = new XXXX();
		return res;
	}

	public Collection<XXXX> findAll() {
		Collection<XXXX> res;
		res = this.XXXXRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public XXXX findOne(int xxxxId) {
		XXXX result;
		result = this.XXXXRepository.findOne(xxxxId);
		Assert.notNull(result);
		return result;
	}

	public XXXX save(XXXX xxxx) {
		XXXX res;
		FixUpTask fixUpTask;
		Actor principal;

		Assert.notNull(xxxx);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.registered");
		Assert.isTrue(principal instanceof HandyWorker, "not.allowed");

		fixUpTask = xxxx.getFixUpTask();
		Assert.notNull(fixUpTask);

		if (xxxx.getId() == 0) {
			xxxx.setTicker(this.utilityService.generateTicker());
			xxxx.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		} else {
			Assert.isTrue(xxxx.getFixUpTask().equals(
					this.findOne(xxxx.getId()).getFixUpTask()));
			if (this.findOne(xxxx.getId()).getIsFinal()) {
				Assert.isTrue(xxxx.getIsFinal());
				Assert.isTrue(xxxx.getTicker().equals(
						this.findOne(xxxx.getId()).getTicker()));
				Assert.isTrue(xxxx.getBody().equals(
						this.findOne(xxxx.getId()).getBody()));
				Assert.isTrue(xxxx.getPhotoLink().equals(
						this.findOne(xxxx.getId()).getPhotoLink()));

			}
		}

		res = this.XXXXRepository.save(xxxx);
		Assert.notNull(res);

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(xxxx.getBody());

		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam) {
			principal.setIsSuspicious(true);
		}

		return res;
	}

	// Completar cuando se tenga el enunciado
	public void delete(XXXX xxxx) {
		Actor principal;

		Assert.notNull(xxxx);
		Assert.isTrue(xxxx.getId() != 0);
		Assert.isTrue(!xxxx.getIsFinal());

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.XXXXRepository.delete(xxxx.getId());

	}

	public Collection<XXXX> findByFixUpTaskId(int fixuptaskID) {
		Collection<XXXX> res;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixuptaskID);
		Assert.notNull(fixUpTask);

		res = fixUpTask.getXXXXs();
		Assert.notEmpty(res);

		return res;
	}
}
