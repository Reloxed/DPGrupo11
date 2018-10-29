package domain;

import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class MiscellaneousRecord extends DomainEntity {

	private String title;
	private String linkAttachement;
	private Collection<String> comments;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@URL
	public String getLinkAttachement() {
		return linkAttachement;
	}

	public void setLinkAttachement(String linkAttachement) {
		this.linkAttachement = linkAttachement;
	}

	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

}
