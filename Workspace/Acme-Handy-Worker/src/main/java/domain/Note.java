package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.jboss.logging.annotations.FormatWith;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date publishedMoment;
	private String refereeComment;
	private String customerComment;
	private String handyWorkerComment;

	/* Atributtes */

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
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
