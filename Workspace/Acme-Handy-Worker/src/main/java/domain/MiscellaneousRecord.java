package domain;

import java.util.Collection;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class MiscellaneousRecord extends DomainEntity {

	private String title;
	private String linkAttachement;
	private Collection<String> comment;

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

	public Collection<String> getComment() {
		return comment;
	}

	public void setComment(Collection<String> comment) {
		this.comment = comment;
	}

}
