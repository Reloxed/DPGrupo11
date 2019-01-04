
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services
	@Autowired
	private RangerService			rangerService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public CurriculumService() {
		super();
	}

	// Additional functions
	private String generateTicker() {
		String result;
		final Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		final String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String date = String.valueOf(now.get(Calendar.DATE));
		date = date.length() == 1 ? "0".concat(date) : date;
		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d);
		code = code.toUpperCase();
		result = year + month + date + "-" + code;
		return result;
	}

	// Simple CRUD methods
	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}
	public Curriculum create() {
		Curriculum result;
		Ranger principal;

		PersonalRecord personalRecord;
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isNull(this.findCurriculumByPrincipal());
		result = new Curriculum();
		result.setRanger(principal);
		result.setTicker(this.generateTicker());
		personalRecord = new PersonalRecord();
		personalRecord.setName(principal.getName());
		personalRecord.setSurname(principal.getSurname());
		result.setPersonalRecord(personalRecord);
		result.setEducationRecord(new ArrayList<EducationRecord>());
		result.setEndorserRecord(new ArrayList<EndorserRecord>());
		result.setMiscellaneousRecord(new ArrayList<MiscellaneousRecord>());
		result.setProfessionalRecords(new ArrayList<ProfessionalRecord>());

		return result;

	}
	public Curriculum save(final Curriculum curriculum) {
		Collection<String> spamwords;
		Ranger principal;
		Curriculum result;
		Assert.notNull(curriculum);
		principal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculum.getRanger() == principal);
		spamwords = this.customisationService.find().getSpamWords();
		result = this.curriculumRepository.save(curriculum);
		result.getRanger().setCV(result);
		for (final String spamword : spamwords) {
			if (result.getPersonalRecord().getName().toLowerCase().contains(spamword.toLowerCase()) || result.getPersonalRecord().getSurname().toLowerCase().contains(spamword.toLowerCase())
				|| result.getPersonalRecord().getEmail().toLowerCase().contains(spamword.toLowerCase())) {

				principal.setIsSuspicious(true);
				break;

			}
			for (final ProfessionalRecord pr : result.getProfessionalRecords()) {
				if (pr.getComments() != null)
					for (final String comment : pr.getComments())
						if (comment.toLowerCase().contains(spamword.toLowerCase())) {
							principal.setIsSuspicious(true);
							break;
						}
				if (pr.getCompanyName().toLowerCase().contains(spamword.toLowerCase()) || pr.getCompanyName().toLowerCase().contains(spamword.toLowerCase()) || pr.getCompanyName().toLowerCase().contains(spamword.toLowerCase())) {

					principal.setIsSuspicious(true);
					break;
				}
			}
			for (final EducationRecord er : result.getEducationRecord()) {
				if (er.getComments() != null)
					for (final String comment : er.getComments())
						if (comment.toLowerCase().contains(spamword.toLowerCase())) {

							principal.setIsSuspicious(true);
							break;
						}
				if (er.getInstitutionName().toLowerCase().contains(spamword.toLowerCase()) || er.getDiplomaTitle().toLowerCase().contains(spamword.toLowerCase())) {

					principal.setIsSuspicious(true);
					break;
				}
			}

			for (final MiscellaneousRecord mr : result.getMiscellaneousRecord()) {
				if (mr.getComments() != null)
					for (final String comment : mr.getComments())
						if (comment.toLowerCase().contains(spamword.toLowerCase())) {

							principal.setIsSuspicious(true);
							break;
						}
				if (mr.getTitle().toLowerCase().contains(spamword.toLowerCase())) {

					principal.setIsSuspicious(true);
					break;
				}
			}

			for (final EndorserRecord er : result.getEndorserRecord()) {
				if (er.getComments() != null)
					for (final String comment : er.getComments())
						if (comment.toLowerCase().contains(spamword.toLowerCase())) {

							principal.setIsSuspicious(true);
							break;
						}
				if (er.getEmail().toLowerCase().contains(spamword.toLowerCase()) || er.getName().toLowerCase().contains(spamword.toLowerCase()) || er.getSurname().toLowerCase().contains(spamword.toLowerCase())) {

					principal.setIsSuspicious(true);
					break;
				}
			}
		}

		return result;

	}
	public void delete(final Curriculum curriculum) {
		Ranger principal;
		Curriculum result;
		Assert.isTrue(curriculum.getId() != 0);
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.findCurriculumByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(curriculum.equals(result));
		this.curriculumRepository.delete(curriculum);
		principal.setCV(null);
	}

	// Other business methods
	public Curriculum findCurriculumByPrincipal() {
		Curriculum result;
		Ranger principal;
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.curriculumRepository.findCurriculumByPrincipal(principal.getId());
		Assert.isTrue(principal.getCV() == result);
		return result;

	}

	public Curriculum findCurriculumByTrip(final int tripId) {
		Curriculum result;
		result = this.curriculumRepository.findCurriculumByTrip(tripId);
		Assert.notNull(result);
		return result;
	}

}
