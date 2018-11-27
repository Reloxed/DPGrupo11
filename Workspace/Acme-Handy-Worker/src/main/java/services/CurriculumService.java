package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository

	@Autowired
	private CurriculumRepository curriculumRepository;

	// Supporting Services

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private EducationRecordService educationRecordService;

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Autowired
	private UtilityService utilityService;

	// Simple CRUD Methods

	public Curriculum create() {
		final Curriculum result;
		HandyWorker author;

		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);

		final PersonalRecord personalRecord = new PersonalRecord();

		final Collection<EducationRecord> collER = new ArrayList<>();
		final Collection<ProfessionalRecord> collProR = new ArrayList<>();
		final Collection<EndorserRecord> collEndR = new ArrayList<>();
		final Collection<MiscellaneousRecord> collMR = new ArrayList<>();
		final String ticker = this.utilityService.generateTicker();

		result = new Curriculum();
		result.setTicker(ticker);
		result.setEducationRecords(collER);
		result.setEndorserRecords(collEndR);
		result.setMiscellaneousRecords(collMR);
		result.setPersonalRecord(personalRecord);
		result.setProfessionalRecords(collProR);

		return result;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> curriculums;

		curriculums = this.curriculumRepository.findAll();

		return curriculums;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		HandyWorker author;
		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);
		if (curriculum.getId() != 0)
			Assert.isTrue(curriculum.getTicker().equals(
					author.getCurriculum().getTicker()));
		Assert.notNull(curriculum.getPersonalRecord());

		return this.curriculumRepository.save(curriculum);

	}

	public void delete(final Curriculum curriculum) {
		HandyWorker author;

		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);

		Assert.isTrue(author.getCurriculum().equals(curriculum));

		this.personalRecordService.delete(curriculum.getPersonalRecord());
		for (final EducationRecord edRec : curriculum.getEducationRecords())
			this.educationRecordService.delete(edRec);
		for (final ProfessionalRecord profRec : curriculum
				.getProfessionalRecords())
			this.professionalRecordService.delete(profRec);
		for (final EndorserRecord endRec : curriculum.getEndorserRecords())
			this.endorserRecordService.delete(endRec);
		for (final MiscellaneousRecord miscRec : curriculum
				.getMiscellaneousRecords())
			this.miscellaneousRecordService.delete(miscRec);
		this.curriculumRepository.delete(curriculum);
	}

	// Other business methods

}
