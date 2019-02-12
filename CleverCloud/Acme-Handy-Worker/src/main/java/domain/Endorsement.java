package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "Endorsement")
public class Endorsement extends DomainEntity {

	private Date publishedMoment;
	private String comments;
	private Endorser sender;
	private Endorser recipient;

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

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comment) {
		this.comments = comment;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Endorser getSender() {
		return this.sender;
	}

	public void setSender(final Endorser sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Endorser getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Endorser recipient) {
		this.recipient = recipient;
	}

}
