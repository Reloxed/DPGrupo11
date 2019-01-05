
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private UtilityService			utilityService;

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

		res = this.educationRecordRepository.save(educationRecord);		
		this.educationRecordRepository.flush();
		
		
		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(educationRecord.getDiplomaTitle());
		atributosAComprobar.add(educationRecord.getInstitutionName());
		if (educationRecord.getComments() != null)
			atributosAComprobar.add(educationRecord.getComments());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}
		
		educationRecords = principal.getCurriculum().getEducationRecords();
		if (educationRecord.getId() == 0) {
			educationRecords.add(res);
			Curriculum curriculum;
			curriculum = principal.getCurriculum();
			curriculum.setEducationRecords(educationRecords);
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

		educationRecords.remove(educationRecord);
		principal.getCurriculum().setEducationRecords(educationRecords);

	}
	// Other business methods

}
