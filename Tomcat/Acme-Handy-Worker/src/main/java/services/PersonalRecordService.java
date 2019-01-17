
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors ------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord result;
		result = this.personalRecordRepository.findOne(personalRecordId);
		Assert.notNull(result);
		return result;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result;
		result = this.personalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public PersonalRecord create() {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new PersonalRecord();

		return res;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getLinkedinLink().toLowerCase().startsWith("https://www.linkedin.com/") || personalRecord.getLinkedinLink().toLowerCase().startsWith("http://www.linkedin.com/"));

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(personalRecord.getFullName());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		res = personalRecord;

		return this.personalRecordRepository.save(res);
	}

	public void delete(final PersonalRecord personalRecord) {
		HandyWorker principal;

		Assert.notNull(personalRecord);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		this.personalRecordRepository.delete(personalRecord);
	}

}
