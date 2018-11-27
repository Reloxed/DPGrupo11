package services;

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

		Assert.isTrue(!personalRecord.getFullName().isEmpty());
		Assert.isTrue(!personalRecord.getEmail().isEmpty());
		Assert.isTrue(!personalRecord.getPhoneNumber().isEmpty());
		Assert.isTrue(!personalRecord.getLinkedinLink().isEmpty());

		res = personalRecord;

		return this.personalRecordRepository.save(res);
	}

	public void delete(PersonalRecord personalRecord) {
		HandyWorker principal;

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		this.personalRecordRepository.delete(personalRecord);
	}

	// Other business methods -------------------------------

	public PersonalRecord findByPrincipal() {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = principal.getCurriculum().getPersonalRecord();

		return res;
	}
}
