
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	private String	name;
	private double	latitude;
	private double	longitude;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@Range(min = -90, max = 90)
	public double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}
	@Range(min = -180, max = 180)
	public double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

}
