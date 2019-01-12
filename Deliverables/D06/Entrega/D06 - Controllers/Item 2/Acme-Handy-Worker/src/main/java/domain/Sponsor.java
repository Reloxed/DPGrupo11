package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor implements Cloneable {

	// Attributes

	private Collection<Sponsorship> sponsorships;
	private Collection<CreditCard> creditCards;

	// Metodos
	@OneToMany(mappedBy="sponsor")
	@Valid
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	
	@Valid
	@OneToMany
	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@Override
	public Sponsor clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Sponsor) super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
