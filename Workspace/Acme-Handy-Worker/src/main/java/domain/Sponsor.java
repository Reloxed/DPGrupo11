
package domain;

import java.util.Collection;

public class Sponsor extends Actor {

	// Attributes

	private Collection<Sponsorship>	sponsorships;


	// Metodos

	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
