package domain;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Report extends DomainEntity {

	private Date publishedDate;
	private String description;
	private Collection<String> attachement;
	private boolean isFinal;
	private Collection<Note> notes;

	@Past
	@NotNull
	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@URL
	public Collection<String> getAttachement() {
		return attachement;
	}

	public void setAttachement(Collection<String> attachement) {
		this.attachement = attachement;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Valid
	@NotNull
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

}
