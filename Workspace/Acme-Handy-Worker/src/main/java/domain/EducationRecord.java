package domain;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	private String diplomaTitle;
	private Date startDate;
	private Date endDate;
	private String institutionName;
	private String linkAttachement;
	private String comments;

	@NotBlank
	public String getDiplomaTitle() {
		return diplomaTitle;
	}

	public void setDiplomaTitle(String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	@URL
	public String getLinkAttachement() {
		return linkAttachement;
	}

	public void setLinkAttachement(String linkAttachement) {
		this.linkAttachement = linkAttachement;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
