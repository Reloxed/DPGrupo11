package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TapuRepository;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.Tapu;

@Service
@Transactional
public class TapuService {

	// Managed repository ------------------------------------

	@Autowired
	private TapuRepository TapuRepository;

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

	public TapuService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Tapu create() {
		Actor principal;
		Tapu res;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.registered");
		Assert.isTrue(principal instanceof Customer, "not.allowed");

		res = new Tapu();
		res.setTicker("000000/00/aa");
		return res;
	}

	public Collection<Tapu> findAll() {
		Collection<Tapu> res;
		res = this.TapuRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Tapu findOne(int tapuId) {
		Tapu result;
		result = this.TapuRepository.findOne(tapuId);
		Assert.notNull(result);
		return result;
	}

	public Tapu save(Tapu tapu) {
		Tapu res;
		FixUpTask fixUpTask;
		Actor principal;

		Assert.notNull(tapu);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.registered");
		Assert.isTrue(principal instanceof Customer, "not.allowed");

		fixUpTask = tapu.getFixUpTask();
		Assert.notNull(fixUpTask);

		if (tapu.getId() == 0) {
			tapu.setTicker(this.utilityService.generateTickerTapu());
		} else {
			Assert.isTrue(tapu.getFixUpTask().equals(
					this.findOne(tapu.getId()).getFixUpTask()));
			Assert.isTrue(tapu.getTicker().equals(
					this.findOne(tapu.getId()).getTicker()));
			if (tapu.getIsFinal()) {
				Assert.isTrue(tapu.getTicker().equals(
						this.findOne(tapu.getId()).getTicker()));
				tapu.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
			}
		}

		Assert.isTrue(tapu.getBody().length() < 252);

		res = this.TapuRepository.save(tapu);
		Assert.notNull(res);

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(tapu.getBody());

		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam) {
			principal.setIsSuspicious(true);
		}

		return res;
	}

	// Completar cuando se tenga el enunciado
	public void delete(Tapu tapu) {
		Actor principal;

		Assert.notNull(tapu);
		Assert.isTrue(tapu.getId() != 0);
		Assert.isTrue(!tapu.getIsFinal());

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.TapuRepository.delete(tapu.getId());

	}

	public Collection<Tapu> findByFixUpTaskId(int fixuptaskID) {
		Collection<Tapu> res;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixuptaskID);
		Assert.notNull(fixUpTask);

		res = fixUpTask.getTapus();

		return res;
	}

	// Other business methods

	public Double[] operationsTapu() {
		Double[] result;

		result = this.TapuRepository.operationsTapus();

		return result;
	}

	public Double ratioFinalTapus() {
		Double result;

		result = this.TapuRepository.ratioTapusFinalMode();

		return result;
	}

	public Double ratioTapusDraftMode() {
		Double result;

		result = this.TapuRepository.ratioTapusDraftMode();

		return result;
	}

}
