
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Ranger extends Actor {

	private Collection<Trip>	trips;
	private Curriculum			CV;


	@NotNull
	@OneToMany(mappedBy = "ranger")
	public Collection<Trip> getTrips() {
		return this.trips;
	}
	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

	@Valid
	@OneToOne(optional = true, mappedBy = "ranger")
	public Curriculum getCV() {
		return this.CV;
	}
	public void setCV(final Curriculum cV) {
		this.CV = cV;
	}
	

	
	
	
	
}
