
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	private String			title;
	private String			body;
	private Date			registerDate;
	private List<String>	laws;
	private Boolean			isDraft;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	// Derived attribute => doesn't need any annotations
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getRegisterDate() {
		return this.registerDate;
	}
	public void setRegisterDate(final Date registerDate) {
		this.registerDate = registerDate;
	}

	@ElementCollection
	@NotEmpty
	public List<String> getLaws() {
		return this.laws;
	}
	public void setLaws(final List<String> laws) {
		this.laws = laws;
	}

	// Derived attribute => doesn't need any annotations
	public Boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

}
