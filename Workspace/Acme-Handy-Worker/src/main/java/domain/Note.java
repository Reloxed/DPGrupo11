package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Note extends DomainEntity {

	private Date publishedMoment;
	private String creatorComment;
	private Collection<String> otherActorComments;

	/* Atributtes */

	@Past
	@NotNull
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getCreatorComment() {
		return this.creatorComment;
	}

	public void setCreatorComment(final String creatorComment) {
		this.creatorComment = creatorComment;
	}

	public Collection<String> getOtherActorComments() {
		return this.otherActorComments;
	}

	public void setOtherActorComments(
			final Collection<String> otherActorComments) {
		this.otherActorComments = otherActorComments;
	}
}
