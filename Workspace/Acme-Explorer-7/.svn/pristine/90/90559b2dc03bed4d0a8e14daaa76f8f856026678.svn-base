
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	private String			name;
	private String			surname;
	private String			email;
	private String			phone;
	private String			linkedInProfile;
	private List<String>	comments;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
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
	@Pattern(regexp = "^$|^\\d{4,}|\\+[1-9]\\d{0,2}\\([1-9]\\d{0,2}\\)\\d{4,}|\\+[1-9]\\d{0,2} \\([1-9]\\d{0,2}\\) \\d{4,}|\\+[1-9]\\d{0,2}\\d{4,}|\\+[1-9]\\d{0,2} \\d{4,}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@URL
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}
	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
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
