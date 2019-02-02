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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Observation extends DomainEntity{

	private String ticker;
	private Date publishedMoment;
	private String body;
	private String picture;
	private boolean isFinal;
	private FixUpTask fixUpTask;
	
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9]{6}")
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	
	public Date getPublishedMoment() {
		return publishedMoment;
	}

	public void setPublishedMoment(Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}
	
	@NotBlank
	@Length(min=0,max = 100)
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}
	
	
}
