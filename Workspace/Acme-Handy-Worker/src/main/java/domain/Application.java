package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Application extends DomainEntity {

	private Date registeredMoment;
	private String status;
	private Money offeredPrice;
	private String comment;
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

}
