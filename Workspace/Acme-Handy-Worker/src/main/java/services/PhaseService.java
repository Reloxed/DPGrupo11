package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PhaseRepository;
import domain.PersonalRecord;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository ------------------------------------

	@Autowired
	private PhaseRepository phaseRepository;

	// Supporting services -----------------------------------

	@Autowired
	private FixUpTaskService fixUpTaskService;

	// Simple CRUD methods -----------------------------------

	public Phase create() {
		return new Phase();
	}

	public Collection<Phase> findAll() {
		return phaseRepository.findAll();
	}

	public Phase findOne(int phaseId) {
		return phaseRepository.findOne(phaseId);
	}

	public Phase save(Phase p) {
		return phaseRepository.save(p);
	}

	// TODO
	public void delete(Phase p) {
		phaseRepository.delete(p);
	}

	// TODO
	// Other business methods -------------------------------
}
