
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private SystemConfigurationService		systemConfigurationService;


	// Simple CRUD methods -----------------------------------

	public ProfessionalRecord create() {
		HandyWorker principal;
		ProfessionalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new ProfessionalRecord();

		return res;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		ProfessionalRecord res;
		res = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(res);
		return res;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> res;
		res = this.professionalRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		ProfessionalRecord res;
		Collection<ProfessionalRecord> professionalRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.notNull(professionalRecord);

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		if (professionalRecord.getComments() != null) {
			final String[] comments = professionalRecord.getComments().split("(¿¡,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : comments)
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
				if (professionalRecord.getRole() != null) {
					final String[] role = professionalRecord.getRole().split("(¿¡,.-_/!?) ");
					for (final String word : spamWords) {
						for (final String titleWord : role)
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
				if (!containsSpam)
					if (professionalRecord.getCompanyName() != null) {
						final String[] companyName = professionalRecord.getRole().split("(¿¡,.-_/!?) ");
						for (final String word : spamWords) {
							for (final String titleWord : companyName)
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
			}
		}

		if (professionalRecord.getEndDate() != null)
			Assert.isTrue(professionalRecord.getStartDate().before(professionalRecord.getEndDate()));
		else
			Assert.isTrue(professionalRecord.getStartDate().before(new Date(System.currentTimeMillis())));

		professionalRecords = principal.getCurriculum().getProfessionalRecords();

		res = this.professionalRecordRepository.saveAndFlush(professionalRecord);
		Assert.notNull(res);

		if (professionalRecord.getId() == 0) {
			professionalRecords.add(res);
			principal.getCurriculum().setProfessionalRecords(professionalRecords);
		}

		return res;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		Collection<ProfessionalRecord> professionalRecords;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		professionalRecords = principal.getCurriculum().getProfessionalRecords();

		professionalRecords.remove(professionalRecord);

		this.professionalRecordRepository.delete(professionalRecord);

		principal.getCurriculum().setProfessionalRecords(professionalRecords);
	}

	// Other business methods -------------------------------

}
