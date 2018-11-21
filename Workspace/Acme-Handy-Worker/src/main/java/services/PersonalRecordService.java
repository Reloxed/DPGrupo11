package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private PersonalRecordRepository personalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------------------

	public PersonalRecord create() {
		return new PersonalRecord();
	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(int personalRecordId) {
		return this.personalRecordRepository.findOne(personalRecordId);
	}

	public PersonalRecord save(PersonalRecord p) {
		return this.personalRecordRepository.save(p);
	}

	// TODO
	public void delete(PersonalRecord p) {
		this.personalRecordRepository.delete(p);
	}

	// TODO
	// Other business methods -------------------------------
}
