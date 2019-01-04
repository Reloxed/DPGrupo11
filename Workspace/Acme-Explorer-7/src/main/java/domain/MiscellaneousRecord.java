
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	private String			title;
	private String			linkAttachment;
	private List<String>	comments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@URL
	public String getLinkAttachment() {
		return this.linkAttachment;
	}
	public void setLinkAttachment(final String linkAttachment) {
		this.linkAttachment = linkAttachment;
	}

	@NotNull
	@ElementCollection
	public List<String> getComments() {
		return this.comments;
	}
	public void setComments(final List<String> comments) {
		this.comments = comments;
	}
}
