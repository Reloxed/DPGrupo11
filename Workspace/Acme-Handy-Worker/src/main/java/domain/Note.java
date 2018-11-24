package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date publishedMoment;
	private String refereeComment;
	private String customerComment;
	private String handyWorkerComment;
	private Report report;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	public String getRefereeComment() {
		return this.refereeComment;
	}

	public void setRefereeComment(final String refereeComment) {
		this.refereeComment = refereeComment;
	}

	public String getCustomerComment() {
		return this.customerComment;
	}

	public void setCustomerComment(final String customerComment) {
		this.customerComment = customerComment;
	}

	public String getHandyWorkerComment() {
		return this.handyWorkerComment;
	}

	public void setHandyWorkerComment(final String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
}
