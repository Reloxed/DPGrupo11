package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.HandyWorker;
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

	public PersonalRecord findOne(int personalRecordId) {
		PersonalRecord res;
		res = this.personalRecordRepository.findOne(personalRecordId);
		return res;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> res;
		res = this.personalRecordRepository.findAll();
		return res;
	}

	public PersonalRecord create() {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new PersonalRecord();

		return res;
	}

	public PersonalRecord save(PersonalRecord personalRecord) {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(personalRecord);

		res = personalRecord;

		return this.personalRecordRepository.saveAndFlush(res);
	}

	public void delete(PersonalRecord personalRecord) {
		HandyWorker principal;

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getCurriculum().getPersonalRecord()
				.equals(personalRecord));

		this.personalRecordRepository.delete(personalRecord);

		principal.getCurriculum().setPersonalRecord(null);
	}

	// Other business methods -------------------------------

}
