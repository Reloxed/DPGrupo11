
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
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public PersonalRecord findOne(final int personalRecordId) {
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

	public PersonalRecord save(final PersonalRecord personalRecord) {
		HandyWorker principal;
		PersonalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getLinkedinLink().startsWith("https://www.linkedin.com/"));

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] name = personalRecord.getFullName().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : name)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		
		res = personalRecord;

		return this.personalRecordRepository.saveAndFlush(res);
	}

	public void delete(final PersonalRecord personalRecord) {
		HandyWorker principal;

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getCurriculum().getPersonalRecord().equals(personalRecord));

		this.personalRecordRepository.delete(personalRecord);

		principal.getCurriculum().setPersonalRecord(null);
	}

	// Other business methods -------------------------------

}
