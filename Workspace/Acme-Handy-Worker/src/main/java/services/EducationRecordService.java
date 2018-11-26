package services;

import java.util.Collection;

import org.joda.time.LocalDate;
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
	private EducationRecordRepository educationRecordRepository;
		
	// Supporting Services

	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private CurriculumService curriculumService;
		
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

	public EducationRecord findOne(int educationRecordId) {
		EducationRecord res;
		res = this.educationRecordRepository.findOne(educationRecordId);
		return res;
	}

	public EducationRecord save(EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		HandyWorker principal;
		EducationRecord res;
		Collection<EducationRecord> educationRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.notNull(educationRecord.getDiplomaTitle());
		Assert.notNull(educationRecord.getStartDate());
		Assert.notNull(educationRecord.getInstitutionName());
		if (educationRecord.getEndDate()!=null){
			Assert.isTrue(educationRecord.getStartDate().before(educationRecord.getEndDate()));
		} else {
			Assert.isTrue(educationRecord.getStartDate().before(LocalDate.now().toDate()));
		}
		
		res = this.educationRecordRepository.save(educationRecord);

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

	public void delete(EducationRecord educationRecord) {
		HandyWorker principal;
		Collection<EducationRecord> educationRecords;

		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		educationRecords = principal.getCurriculum().getEducationRecords();
		Assert.isTrue(educationRecords.contains(educationRecord));

		this.educationRecordRepository.delete(educationRecord);

//		educationRecords.remove(educationRecord);
//
//		principal.getCurriculum().setEducationRecords(educationRecords);
//		
//		this.handyWorkerService.save(principal);
	}
	
	// Other business methods

}
