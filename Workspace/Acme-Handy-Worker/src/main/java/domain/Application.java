
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date				registeredMoment;
	private String				status;
	private Money				offeredPrice;
	private String				comment;
	private CreditCard			creditCard;
	private Collection<Phase>	phases;
	private FixUpTask			fixUpTask;
	private HandyWorker			applicant;


	@NotBlank
	@Pattern(regexp = "^PENDING|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@Valid
	public Money getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@NotNull
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegisteredMoment() {
		return this.registeredMoment;
	}

	public void setRegisteredMoment(final Date registeredMoment) {
		this.registeredMoment = registeredMoment;
	}

	@Valid
	@OneToMany
	public Collection<Phase> getPhases() {
		return this.phases;
	}

	public void setPhases(final Collection<Phase> phases) {
		this.phases = phases;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public HandyWorker getApplicant() {
		return this.applicant;
	}
	public void setApplicant(final HandyWorker applicant) {
		this.applicant = applicant;
	}

}
