package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Curriculum extends DomainEntity {

	// Atributos

	private String ticker;
	private PersonalRecord personalRecord;
	private Collection<EducationRecord> educationRecords;
	private Collection<ProfessionalRecord> professionalRecords;
	private Collection<EndorserRecord> endorserRecords;
	private Collection<MiscellaneousRecord> miscellaneousRecords;

	// Metodos

	@NotBlank
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9]{6}")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@NotNull
	public PersonalRecord getPersonalRecord() {
		return personalRecord;
	}

	public void setPersonalRecord(PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	public Collection<EducationRecord> getEducationRecords() {
		return educationRecords;
	}

	public void setEducationRecords(Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return professionalRecords;
	}

	public void setProfessionalRecords(
			Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	public Collection<EndorserRecord> getEndorserRecords() {
		return endorserRecords;
	}

	public void setEndorserRecords(Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return miscellaneousRecords;
	}

	public void setMiscellaneousRecords(
			Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

}
