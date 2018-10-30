package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Note extends DomainEntity {

	private Date publishedMoment;
	private String refereeComment;
	private String customerComment;
	private String handyWorkerComment;

	/* Atributtes */

	@Past
	@NotNull
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	public String getRefereeComment() {
		return refereeComment;
	}

	public void setRefereeComment(String refereeComment) {
		this.refereeComment = refereeComment;
	}

	public String getCustomerComment() {
		return customerComment;
	}

	public void setCustomerComment(String customerComment) {
		this.customerComment = customerComment;
	}

	public String getHandyWorkerComment() {
		return handyWorkerComment;
	}

	public void setHandyWorkerComment(String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}

	
}
