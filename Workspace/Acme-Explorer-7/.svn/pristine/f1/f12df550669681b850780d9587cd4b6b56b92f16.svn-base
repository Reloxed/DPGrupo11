
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private Ranger						ranger;
	private String						ticker;
	private PersonalRecord				personalRecord;
	private List<ProfessionalRecord>	professionalRecords;
	private List<EducationRecord>		educationRecord;
	private List<EndorserRecord>		endorserRecord;
	private List<MiscellaneousRecord>	miscellaneousRecord;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public List<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(final List<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public List<EducationRecord> getEducationRecord() {
		return this.educationRecord;
	}

	public void setEducationRecord(final List<EducationRecord> educationRecord) {
		this.educationRecord = educationRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public List<EndorserRecord> getEndorserRecord() {
		return this.endorserRecord;
	}

	public void setEndorserRecord(final List<EndorserRecord> endorserRecord) {
		this.endorserRecord = endorserRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public List<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final List<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

}
