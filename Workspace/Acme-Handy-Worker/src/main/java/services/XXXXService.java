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
import domain.Customer;
import domain.FixUpTask;
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
		Assert.isTrue(principal instanceof Customer, "not.allowed");

		res = new XXXX();
		res.setTicker("000000-QWERTY");
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
		Assert.isTrue(principal instanceof Customer, "not.allowed");

		fixUpTask = xxxx.getFixUpTask();
		Assert.notNull(fixUpTask);

		if (xxxx.getId() == 0) {
			xxxx.setTicker(this.utilityService.generateTicker());
		} else {
			Assert.isTrue(xxxx.getFixUpTask().equals(
					this.findOne(xxxx.getId()).getFixUpTask()));
			Assert.isTrue(xxxx.getTicker().equals(
					this.findOne(xxxx.getId()).getTicker()));
			if (xxxx.getIsFinal()) {
				Assert.isTrue(xxxx.getTicker().equals(
						this.findOne(xxxx.getId()).getTicker()));
				xxxx.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
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

	// Other business methods

	public Double[] operationsXXXX() {
		Double[] result;

		result = this.XXXXRepository.operationsXXXXs();
		Assert.notNull(result);

		return result;
	}

	public Double ratioFinalXXXXs() {
		Double result;

		result = this.XXXXRepository.ratioXXXXsFinalMode();
		Assert.notNull(result);

		return result;
	}

	public Double ratioXXXXsDraftMode() {
		Double result;

		result = this.XXXXRepository.ratioXXXXsDraftMode();
		Assert.notNull(result);

		return result;
	}

}
