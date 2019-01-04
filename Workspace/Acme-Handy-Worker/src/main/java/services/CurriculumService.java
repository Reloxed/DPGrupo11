
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
	private CurriculumRepository	curriculumRepository;

	// Supporting Services

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors ------------------------------------

	public CurriculumService() {
		super();
	}

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

		result = new Curriculum();
		result.setEducationRecords(collER);
		result.setEndorserRecords(collEndR);
		result.setMiscellaneousRecords(collMR);
		result.setPersonalRecord(personalRecord);
		result.setProfessionalRecords(collProR);

		return result;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Curriculum result;
		HandyWorker principal;
		
		Assert.notNull(curriculum);
		
		
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		if (curriculum.getId() == 0) {
			curriculum.setTicker(this.utilityService.generateTicker());
		} else {
			Assert.isTrue(curriculum.getTicker().equals(principal.getCurriculum().getTicker()));
		}
			
		Assert.notNull(curriculum.getPersonalRecord());
		result = this.curriculumRepository.save(curriculum);
		this.curriculumRepository.flush();
		return result;

	}

	public void delete(final Curriculum curriculum) {
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getCurriculum().equals(curriculum));

		this.curriculumRepository.delete(curriculum);
		principal.setCurriculum(null);
	}

	// Other business methods

}
