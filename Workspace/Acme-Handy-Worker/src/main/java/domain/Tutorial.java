package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Tutorial {

	private String title;
	private Date lastUpdated;
	private String summary;
	private String picture;
	private HandyWorker writer;
	private Collection<Seccion> sections;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Past
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@NotBlank
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@NotNull
	@Valid
	public HandyWorker getWriter() {
		return writer;
	}

	public void setWriter(HandyWorker writer) {
		this.writer = writer;
	}

	@NotNull
	@Valid
	public Collection<Seccion> getSections() {
		return sections;
	}

	public void setSections(Collection<Seccion> sections) {
		this.sections = sections;
	}

}
