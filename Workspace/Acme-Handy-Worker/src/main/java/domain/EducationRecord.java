
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	private String	diplomaTitle;
	private Date	startDate;
	private Date	endDate;
	private String	institutionName;
	private String	linkAttachment;
	private String	comments;


	@NotBlank
	public String getDiplomaTitle() {
		return this.diplomaTitle;
	}

	public void setDiplomaTitle(final String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(final String institutionName) {
		this.institutionName = institutionName;
	}

	@URL
	public String getLinkAttachment() {
		return this.linkAttachment;
	}

	public void setLinkAttachment(final String linkAttachment) {
		this.linkAttachment = linkAttachment;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
