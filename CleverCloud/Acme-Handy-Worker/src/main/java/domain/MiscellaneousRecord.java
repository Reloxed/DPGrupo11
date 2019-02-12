package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "MiscellaneousRecord")
public class MiscellaneousRecord extends DomainEntity {

	private String title;
	private String linkAttachment;
	private String comments;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@URL
	public String getLinkAttachment() {
		return linkAttachment;
	}

	public void setLinkAttachment(String linkAttachment) {
		this.linkAttachment = linkAttachment;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
