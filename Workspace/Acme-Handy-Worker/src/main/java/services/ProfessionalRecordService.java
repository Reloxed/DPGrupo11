package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRecordRepository;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------------------

	public ProfessionalRecord create() {
		return new ProfessionalRecord();
	}

	public Collection<ProfessionalRecord> findAll() {
		return professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(int professionalRecordId) {
		return professionalRecordRepository.findOne(professionalRecordId);
	}

	public ProfessionalRecord save(ProfessionalRecord p) {
		return professionalRecordRepository.save(p);
	}

	// TODO
	public void delete(ProfessionalRecord p) {
		professionalRecordRepository.delete(p);
	}

	// TODO
	// Other business methods -------------------------------
}
