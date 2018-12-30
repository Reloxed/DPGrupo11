
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed Repository

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting Services

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public EducationRecordService() {
		super();
	}

	// Simple CRUD Methods

	public EducationRecord create() {
		HandyWorker principal;
		EducationRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		res = new EducationRecord();

		return res;
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;
		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);
		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		HandyWorker principal;
		EducationRecord res;
		Collection<EducationRecord> educationRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		if (educationRecord.getEndDate() != null)
			Assert.isTrue(educationRecord.getStartDate().before(educationRecord.getEndDate()));

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] diplomaTitle = educationRecord.getDiplomaTitle().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : diplomaTitle)
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
			final String[] institutionName = educationRecord.getInstitutionName().split("(¿¡,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : institutionName)
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}
		if (educationRecord.getComments() != null)
			if (!containsSpam) {
				final String[] comments = educationRecord.getComments().split("(¿¡,.-_/!?) ");
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
			}
		}

		res = this.educationRecordRepository.save(educationRecord);
		this.educationRecordRepository.flush();

		educationRecords = principal.getCurriculum().getEducationRecords();

		if (educationRecord.getId() == 0) {
			educationRecords.add(res);
			Curriculum curriculum;
			curriculum = principal.getCurriculum();
			curriculum.setEducationRecords(educationRecords);
			this.curriculumService.save(curriculum);
		}
		return res;
	}

	public void delete(final EducationRecord educationRecord) {
		HandyWorker principal;
		Collection<EducationRecord> educationRecords;

		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		educationRecords = principal.getCurriculum().getEducationRecords();
		Assert.isTrue(educationRecords.contains(educationRecord));

		this.educationRecordRepository.delete(educationRecord);

	}
	// Other business methods

}
