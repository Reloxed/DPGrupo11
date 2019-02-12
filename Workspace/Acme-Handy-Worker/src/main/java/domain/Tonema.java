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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tonema extends DomainEntity {
	
	private String ticker;
	private Date publicationMoment;
	private String body;
	private String picture;
	private boolean isFinal;	
	private FixUpTask fixUpTask;
		
	@NotBlank
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationMoment() {
		return publicationMoment;
	}
	
	public void setPublicationMoment(Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}
	
	@NotBlank
	@Length (max = 251)
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
		return isFinal;
	}
	
	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	@NotNull
	@Valid
	@ManyToOne (optional = true)
	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}
	
}
