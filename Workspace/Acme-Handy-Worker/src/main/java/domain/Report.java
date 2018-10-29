
package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Report extends DomainEntity {

	private Date				publishedMoment;
	private String				description;
	private Collection<String>	attachments;
	private boolean				isFinal;
	private Collection<Note>	notes;


	@Past
	@NotNull
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachement(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	public boolean isFinal() {
		return this.isFinal;
	}

	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

}
