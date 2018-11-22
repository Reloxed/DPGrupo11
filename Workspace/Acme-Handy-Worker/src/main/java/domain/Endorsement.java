
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Date			publishedMoment;
	private String			comment;
	private Endorser 		sender;
	private Endorser 		recipient;
	


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
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Endorser getSender() {
		return sender;
	}

	public void setSender(Endorser sender) {
		this.sender = sender;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Endorser getRecipient() {
		return recipient;
	}

	public void setRecipient(Endorser recipient) {
		this.recipient = recipient;
	}








}
