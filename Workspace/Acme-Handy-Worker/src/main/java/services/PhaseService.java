package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
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

	@Autowired
	private CustomerService customerService;

	// Simple CRUD methods -----------------------------------

	public Phase create() {
		HandyWorker principal;
		Phase res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new Phase();

		return res;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> res;
		HandyWorker principal;
		Collection<Application> applications;
		Collection<FixUpTask> fixUpTasks;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		applications = principal.getApplications();
		Assert.notNull(applications);

		fixUpTasks = new HashSet<FixUpTask>();
		for (Application application : applications) {
			if (application.getStatus() == "ACCEPTED") {
				fixUpTasks.add(application.getFixUpTask());
			}
		}

		res = new ArrayList<Phase>();
		for (FixUpTask fixUpTask : fixUpTasks) {
			res.addAll(this.phaseRepository.findAllPhases(fixUpTask.getId()));
		}
		return res;
	}

	public Phase findOne(int phaseId) {
		HandyWorker principalHW;
		Customer principalC;
		Assert.isTrue(phaseId != 0);

		principalHW = this.handyWorkerService.findByPrincipal();

		if (principalHW == null) {
			principalC = this.customerService.findByPrincipal();
			Assert.notNull(principalC);
		}
		return this.phaseRepository.findOne(phaseId);
	}

	public Phase save(Phase phase) {
		HandyWorker principal;

		Assert.notNull(phase);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(!phase.getTitle().isEmpty());
		Assert.isTrue(!phase.getDescription().isEmpty());
		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));

		return this.phaseRepository.save(phase);
	}

	public void delete(Phase phase) {
		HandyWorker principal;

		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();

		Assert.notNull(principal);

		this.phaseRepository.delete(phase);
	}
}
