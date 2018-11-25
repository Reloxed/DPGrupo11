package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.joda.time.LocalDate;
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
	
	// Simple CRUD Methods
	
	public Curriculum create() {
		final Curriculum result;
		HandyWorker author;
		
		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);
		
		PersonalRecord personalRecord = new PersonalRecord();
		EducationRecord educationRecord = new EducationRecord();
		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		EndorserRecord endorserRecord = new EndorserRecord();
		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		
		Collection<EducationRecord> collER = new ArrayList<>();
		Collection<ProfessionalRecord> collProR = new ArrayList<>();
		Collection<EndorserRecord> collEndR = new ArrayList<>();
		Collection<MiscellaneousRecord> collMR = new ArrayList<>();
		String ticker = this.generateTicker();
		
		collER.add(educationRecord);
		collProR.add(professionalRecord);
		collEndR.add(endorserRecord);
		collMR.add(miscellaneousRecord);

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

	public Curriculum findMySystemConfiguration() {
		final Curriculum result;

		result = this.curriculumRepository.findAll().get(0);

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);

		return result;
	}
	
	public Curriculum save (Curriculum curriculum){
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		
		HandyWorker author;
		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);
		
		Assert.isTrue(curriculum.getTicker().equals(author.getCurriculum().getTicker()));
		
		return this.curriculumRepository.save(curriculum);

	}
	
	public void delete (Curriculum curriculum) {
		HandyWorker author;
		
		author = this.handyWorkerService.findByPrincipal();
		Assert.notNull(author);
		
		Assert.isTrue(author.getCurriculum().equals(curriculum));
		
		this.personalRecordService.delete(curriculum.getPersonalRecord());
		for(EducationRecord edRec : curriculum.getEducationRecords()){
			this.educationRecordService.delete(edRec);
		}
		for(ProfessionalRecord profRec : curriculum.getProfessionalRecords()){
			this.professionalRecordService.delete(profRec);
		}
		for(EndorserRecord endRec : curriculum.getEndorserRecords()){
			this.endorserRecordService.delete(endRec);
		}
		for(MiscellaneousRecord miscRec : curriculum.getMiscellaneousRecords()){
			this.miscellaneousRecordService.delete(miscRec);
		}
		this.curriculumRepository.delete(curriculum);		
	}
	
	
	// Other business methods
	
	public String generateTicker() {
		final String result;
		Calendar date;
		String year;
		final String month, day;
		String alphaNum;

		date = Calendar.getInstance();
		date.setTime(LocalDate.now().toDate());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH)+1);
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

		alphaNum = this.RandomString();
		result = year + month + day + "-" + alphaNum;

		return result;
	}
	
	public String RandomString() {

		final String possibleChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		int length = 6; 
		
		StringBuilder stringBuilder = new StringBuilder(length);
		
		for(int i = 0; i < length;i++) {
			stringBuilder.append(possibleChars.charAt(rnd.nextInt(possibleChars.length())));
			
		}
		return stringBuilder.toString();

	}
}

