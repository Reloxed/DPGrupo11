package domain;

import java.util.Collection;
import java.util.Date;

import com.lowagie.text.Section;

public class Tutorial {

	private String title;
	private Date lastUpdated;
	private String summary;
	private String picture;
	private HandyWorker writer;
	private Collection<Seccion> sections;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

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

	public HandyWorker getWriter() {
		return writer;
	}

	public void setWriter(HandyWorker writer) {
		this.writer = writer;
	}

	public Collection<Seccion> getSections() {
		return sections;
	}

	public void setSections(Collection<Seccion> sections) {
		this.sections = sections;
	}

}
