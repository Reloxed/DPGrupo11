
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Supporting services -------------------

	@Autowired
	private HandyWorkerService				handyWorkerService;
	
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//Constructor

	public MiscellaneousRecordService() {
		super();
	}

	//CRUD Methods --------------------------------
	public MiscellaneousRecord create() {
		MiscellaneousRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousRecord();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {

		MiscellaneousRecord result;
		HandyWorker principal;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Curriculum curriculumHW;

		Assert.notNull(miscellaneousRecord);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		curriculumHW = principal.getCurriculum();
		Assert.notNull(curriculumHW);
		miscellaneousRecords = curriculumHW.getMiscellaneousRecords();
		
		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] title = miscellaneousRecord.getTitle().split("(¿¡,.-_/!?) ");
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
		
		if (!containsSpam) {
			final String[] comments = miscellaneousRecord.getComments().split("(¿¡,.-_/!?) ");
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
		
		result = this.miscellaneousRecordRepository.saveAndFlush(miscellaneousRecord);
		Assert.notNull(result);

		miscellaneousRecords.add(result);
		curriculumHW.setMiscellaneousRecords(miscellaneousRecords);

		return result;

	}
	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		return result;
	}
	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		HandyWorker principal;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Curriculum curriculumHW;

		Assert.notNull(miscellaneousRecord);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		curriculumHW = principal.getCurriculum();
		Assert.notNull(curriculumHW);

		miscellaneousRecords = curriculumHW.getMiscellaneousRecords();

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		miscellaneousRecords.remove(miscellaneousRecord);

		curriculumHW.setMiscellaneousRecords(miscellaneousRecords);

	}

	//Other business methods -----------------------------
}
