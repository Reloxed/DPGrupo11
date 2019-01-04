
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					name;
	private Collection<Category>	parentCategories;
	private Collection<Category>	childCategories;
	private Collection<Trip>		trips;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@ManyToMany
	public Collection<Category> getParentCategories() {
		return this.parentCategories;
	}
	public void setParentCategories(final Collection<Category> parentCategories) {
		this.parentCategories = parentCategories;
	}

	@NotNull
	@ManyToMany(mappedBy = "parentCategories")
	public Collection<Category> getChildCategories() {
		return this.childCategories;
	}
	public void setChildCategories(final Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}

	@NotNull
	@OneToMany(mappedBy = "category")
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

}
