
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	private String	nick;
	private String	socialNetwork;
	private String	linkProfile;
	private String	linkPhoto;


	@NotBlank
	public String getNick() {
		return this.nick;
	}
	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}
	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	@NotBlank
	@URL
	public String getLinkProfile() {
		return this.linkProfile;
	}
	public void setLinkProfile(final String linkProfile) {
		this.linkProfile = linkProfile;
	}

	@URL
	public String getLinkPhoto() {
		return this.linkPhoto;
	}
	public void setLinkPhoto(final String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}

}
