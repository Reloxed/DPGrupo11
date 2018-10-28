package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;


@Entity
public class Message extends DomainEntity{
	
	private Date sendDate;
	private String subject;
	private String body;
	private Priority priority;
	private String tags;
	private Collection<MessageBox> messageBoxes;
	
	
	
	@NotNull
	@Past
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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
	
	
	@NotNull
	@Valid
	@ElementCollection
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	@NotNull
	@Valid
	public Collection<MessageBox> getMessageBoxes() {
		return messageBoxes;
	}
	public void setMessageBoxes(Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}
}
