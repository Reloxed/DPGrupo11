package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Application extends DomainEntity {

	private Date registeredMoment;
	private Status status;
	private Money offeredPrice;
	private Collection<String> comment;
	private CreditCard creditCard;

	@NotNull
	@Past
	public Date getRegisteredTime() {
		return registeredMoment;
	}

	public void setRegisteredTime(Date registeredTime) {
		this.registeredMoment = registeredTime;
	}

	@NotNull
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public Collection<String> getComment() {
		return comment;
	}

	public void setComment(Collection<String> comment) {
		this.comment = comment;
	}

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
