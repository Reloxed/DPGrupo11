package domain;

import java.util.Collection;

public class Sponsor extends Actor{
	
	
	
	//Atributos

	private Collection<SponsorShip> sponsorChips;

	
	//Metodos
	
	
	public Collection<SponsorShip> getSponsorChips() {
		return sponsorChips;
	}

	public void setSponsorChips(Collection<SponsorShip> sponsorChips) {
		this.sponsorChips = sponsorChips;
	}
	
	
	
}
