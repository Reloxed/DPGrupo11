
package domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String					name;
	private String					surname;
	private String					email;
	private String					phone;
	private String					address;
	private boolean					isSuspicious;
	private boolean					isBanned;
	private List<SocialIdentity>	socialIdentities;
	private List<Folder>			folders;
	private UserAccount				userAccount;
	private Collection<Message>		sentMessages;
	private Collection<Message>		receivedMessages;


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

	@Pattern(regexp = "^$|^\\d{4,}|\\+[1-9]\\d{0,2}\\([1-9]\\d{0,2}\\)\\d{4,}|\\+[1-9]\\d{0,2} \\([1-9]\\d{0,2}\\) \\d{4,}|\\+[1-9]\\d{0,2}\\d{4,}|\\+[1-9]\\d{0,2} \\d{4,}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	@NotNull
	@OneToMany
	public List<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public void setSocialIdentities(final List<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@NotEmpty
	@OneToMany
	public List<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final List<Folder> folders) {
		this.folders = folders;
	}

	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSentMessages() {
		return this.sentMessages;
	}

	public void setSentMessages(final Collection<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<Message> getReceivedMessages() {
		return this.receivedMessages;
	}

	public void setReceivedMessages(final Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

}
