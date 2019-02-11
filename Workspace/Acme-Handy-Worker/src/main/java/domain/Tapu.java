package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tapu extends DomainEntity {

	private String ticker;
	private Date publishedMoment;
	private String body;
	private String photoLink;
	private boolean isFinal;
	private FixUpTask fixUpTask;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^(\\d{6})[/](\\d{2})[/](\\w{2})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@URL
	public String getPhotoLink() {
		return this.photoLink;
	}

	public void setPhotoLink(final String photoLink) {
		this.photoLink = photoLink;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}
}
