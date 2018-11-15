package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String name;
	private String middleName;
	private String surname;
	private String photo;
	private String email;
	private String phoneNumber;
	private String address;
	private boolean isSuspicious;
	private Collection<SocialProfile> socialProfiles;
	private Collection<MessageBox> messageBoxes;
	private UserAccount userAccount;
	

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@URL
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getIsSuspicious() {
		return isSuspicious;
	}

	public void setIsSuspicious(boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	@Valid
	@OneToMany
	public Collection<SocialProfile> getSocialProfiles() {
		return socialProfiles;
	}

	public void setSocialProfiles(Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@Valid
	@OneToMany
	@NotEmpty
	public Collection<MessageBox> getMessageBoxes() {
		return messageBoxes;
	}

	public void setMessageBoxes(Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
