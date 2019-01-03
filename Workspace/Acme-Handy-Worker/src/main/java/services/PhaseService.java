package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Application;
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
	private SystemConfigurationService systemConfigurationService;

	// Constructors ------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Phase create() {
		HandyWorker principal;
		Phase res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new Phase();

		return res;
	}

	public Phase findOne(final int phaseId) {
		Phase result;
		result = this.phaseRepository.findOne(phaseId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;
		result = this.phaseRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Phase save(final Phase phase) {
		HandyWorker principal;
		FixUpTask fixUpTask;
		Collection<Application> applications;
		boolean canBeSaved;

		Assert.notNull(phase);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService
				.findMySystemConfiguration().getSpamWords().split(",");
		final String[] description = phase.getDescription().split(
				"(��,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : description)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		if (!containsSpam) {
			final String[] title = phase.getTitle().split("(��,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : title)
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}
		}

		fixUpTask = phase.getFixUpTask();
		applications = fixUpTask.getApplications();
		canBeSaved = false;

		for (final Application a : applications)
			if (a.getStatus().equals("ACCEPTED")) {
				canBeSaved = true;
				break;
			}
		Assert.isTrue(canBeSaved);
		Assert.isTrue(phase.getStartMoment().after(fixUpTask.getStartMoment()));
		Assert.isTrue(phase.getEndMoment().after(fixUpTask.getEndMoment()));
		return this.phaseRepository.saveAndFlush(phase);
	}

	public void delete(final Phase phase) {
		HandyWorker principal;

		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();

		Assert.notNull(principal);

		this.phaseRepository.delete(phase);
	}

	// Other business methods
	public Collection<Phase> findPhasesFixUpTask(int fixUpTaskID) {
		Collection<Phase> res;

		res = this.phaseRepository.findAllPhasesByFixUpTaskId(fixUpTaskID);
		Assert.notEmpty(res);

		return res;
	}

	public HandyWorker creator(int phaseID) {
		HandyWorker res = null;
		Phase phase;

		phase = this.findOne(phaseID);
		Assert.notNull(phase);

		for (Application a : phase.getFixUpTask().getApplications()) {
			if (a.getStatus().contentEquals("ACCEPTED")) {
				res = a.getApplicant();
				break;
			}
		}
		return res;
	}

}
