
package domain;

import java.util.Collection;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class EndorserRecord extends DomainEntity {

	private String				fullName;
	private String				email;
	private String				phoneNumber;
	private String				linkedinLink;
	private Collection<String>	comments;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "+[0-9]{6}[0-9]{4,}")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@URL
	@NotBlank
	public String getLinkedinLink() {
		return this.linkedinLink;
	}

	public void setLinkedinLink(final String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

}
