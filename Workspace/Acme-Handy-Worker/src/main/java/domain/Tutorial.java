
package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Tutorial extends DomainEntity {

	private String				title;
	private Date				lastUpdated;
	private String				summary;
	private Collection<String>	pictures;
	private HandyWorker			writer;
	private Collection<Section>	sections;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Past
	@NotNull
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@NotNull
	@Valid
	public HandyWorker getWriter() {
		return this.writer;
	}

	public void setWriter(final HandyWorker writer) {
		this.writer = writer;
	}

	@NotNull
	public Collection<Section> getSections() {
		return this.sections;
	}

	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}

}
