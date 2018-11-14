package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date sendMoment;
	private String subject;
	private String body;
	private String priority;
	private String tags;
	private Actor sender;
	private Collection<Actor> recipients;
	private boolean isSpam;
	
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendMoment() {
		return sendMoment;
	}

	public void setSendMoment(Date sendMoment) {
		this.sendMoment = sendMoment;
	}

	@NotBlank
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^HIGH|NEUTRAL|LOW$")
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotEmpty
	@ManyToMany
	public Collection<Actor> getRecipients() {
		return recipients;
	}

	public void setRecipients(Collection<Actor> recipients) {
		this.recipients= recipients;
	}
	
	
	public boolean getIsSpam() {
		return isSpam;
	}

	public void setIsSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}

	
}
