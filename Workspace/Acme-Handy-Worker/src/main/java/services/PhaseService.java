package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository ------------------------------------

	@Autowired
	private PhaseRepository phaseRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------------------

	public Phase create() {
		HandyWorker principal;
		Phase res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new Phase();

		return res;
	}

	public Phase findOne(int phaseId) {
		Phase res;
		res = this.phaseRepository.findOne(phaseId);
		return res;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> res;
		res = this.phaseRepository.findAll();
		return res;
	}

	public Phase save(Phase phase) {
		HandyWorker principal;

		Assert.notNull(phase);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));

		return this.phaseRepository.saveAndFlush(phase);
	}

	public void delete(Phase phase) {
		HandyWorker principal;

		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();

		Assert.notNull(principal);

		this.phaseRepository.delete(phase);
	}

	// Other business methods -----------------------------------

	public Collection<Phase> findPhasesFixUpTask(Phase phase) {
		Collection<Phase> res;
		Collection<Phase> phases;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		phases = this.findAll();
		Assert.notEmpty(phases);

		res = new ArrayList<Phase>();
		for (Phase phaseFor : phases) {
			if (phaseFor.getFixUpTask() == phase.getFixUpTask()) {
				res.add(phaseFor);
			}
		}
		return res;
	}
}
