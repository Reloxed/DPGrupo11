
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class EmergencyContact extends DomainEntity {

	private String	name;
	private String	email;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	@Pattern(regexp = "^$|^\\d{4,}|\\+[1-9]\\d{0,2}\\([1-9]\\d{0,2}\\)\\d{4,}|\\+[1-9]\\d{0,2} \\([1-9]\\d{0,2}\\) \\d{4,}|\\+[1-9]\\d{0,2}\\d{4,}|\\+[1-9]\\d{0,2} \\d{4,}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

}
