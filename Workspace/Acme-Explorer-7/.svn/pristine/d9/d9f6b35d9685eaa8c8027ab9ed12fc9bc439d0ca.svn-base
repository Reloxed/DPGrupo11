
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

	private Date			moment;
	private String			status;
	private List<String>	comments;
	private String			rejectionReason;
	private CreditCard		creditcard;
	private Trip			trip;
	private Explorer		applicant;


	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^ACCEPTED|DUE|PENDING|REJECTED|CANCELLED$")
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@ElementCollection
	public List<String> getComments() {
		return this.comments;
	}
	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}
	public void setRejectionReason(final String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@NotNull
	@Valid
	public CreditCard getCreditcard() {
		return this.creditcard;
	}
	public void setCreditcard(final CreditCard creditcard) {
		this.creditcard = creditcard;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}
	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Explorer getApplicant() {
		return this.applicant;
	}
	public void setApplicant(final Explorer applicant) {
		this.applicant = applicant;
	}

}
