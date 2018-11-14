package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
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
	@Column(unique = true)
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return personalRecord;
	}

	public void setPersonalRecord(PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return educationRecords;
	}

	public void setEducationRecords(Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return professionalRecords;
	}

	public void setProfessionalRecords(
			Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return endorserRecords;
	}

	public void setEndorserRecords(Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return miscellaneousRecords;
	}

	public void setMiscellaneousRecords(
			Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

}
