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

	// Metodos
	@OneToMany(mappedBy="sponsor")
	@Valid
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
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
