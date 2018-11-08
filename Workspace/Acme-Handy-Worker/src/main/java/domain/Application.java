package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;



public class Application extends DomainEntity {

	private Date registeredMoment;
	private String status;
	private Money offeredPrice;
	private String comment;
	private CreditCard creditCard;
	private Collection<Phase> phases;
	private FixUpTask fixUpTask;
	

	@NotBlank
	@Pattern(regexp = "\b(PENDING|ACCEPTED|REJECTED)\b")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@NotNull
	@Valid
	public Money getOfferedPrice() {
		return offeredPrice;
	}

	public void setOfferedPrice(Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	@Past
	public Date getRegisteredMoment() {
		return registeredMoment;
	}

	public void setRegisteredMoment(Date registeredMoment) {
		this.registeredMoment = registeredMoment;
	}

	@Valid
	
	public Collection<Phase> getPhases() {
		return phases;
	}

	public void setPhases(Collection<Phase> phases) {
		this.phases = phases;
	}

	@Valid
	@NotNull
	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	
	
}
