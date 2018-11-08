package domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Endorsement extends DomainEntity {

	private Date publishedMoment;
	private String comment;
	private Customer customer;
	private HandyWorker handyWorker;

	@Past
	@NotNull
	public Date getPublishedMoment() {
		return publishedMoment;
	}

	public void setPublishedMoment(Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@NotNull
	@Valid
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@NotNull
	@Valid
	public HandyWorker getHandyWorker() {
		return handyWorker;
	}

	public void setHandyWorker(HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
