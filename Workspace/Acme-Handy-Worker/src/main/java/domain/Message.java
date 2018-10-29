package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Message extends DomainEntity {

	private Date sendMoment;
	private String subject;
	private String body;
	private Priority priority;
	private Collection<String> tags;
	private Collection<MessageBox> messageBoxes;
	private Actor sender;
	private Actor reciever;

	@NotNull
	@Past
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

	@NotNull
	@Valid
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	@NotNull
	public Collection<MessageBox> getMessageBoxes() {
		return messageBoxes;
	}

	public void setMessageBoxes(Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	@Valid
	@NotNull
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	public Actor getReciever() {
		return reciever;
	}

	public void setReciever(Actor reciever) {
		this.reciever = reciever;
	}

}
