
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private UtilityService					utilityService;


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

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(miscellaneousRecord.getTitle());
		if (miscellaneousRecord.getComments() != null)
			atributosAComprobar.add(miscellaneousRecord.getComments());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);

		if (miscellaneousRecord.getId() == 0) {
			miscellaneousRecords.add(result);
			curriculumHW.setMiscellaneousRecords(miscellaneousRecords);
		}
		return result;

	}
	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.notNull(result);
		return result;
	}
	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
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
		Assert.isTrue(miscellaneousRecords.contains(miscellaneousRecord));

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		miscellaneousRecords.remove(miscellaneousRecord);

		curriculumHW.setMiscellaneousRecords(miscellaneousRecords);

	}

	//Other business methods -----------------------------
}
