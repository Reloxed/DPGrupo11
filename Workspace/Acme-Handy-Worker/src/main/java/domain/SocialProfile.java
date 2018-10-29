package domain;

import javax.persistence.Entity;

@Entity
public class SocialProfile extends DomainEntity {

	private String nick;
	private String socialNetwork;
	private String link;

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	public void setSocialNetwork(String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
