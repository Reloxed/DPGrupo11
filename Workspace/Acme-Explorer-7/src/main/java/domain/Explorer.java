
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	private Collection<Application>			applications;
	private Collection<EmergencyContact>	emergencyContacts;
	private Collection<Story>				stories;
	private Collection<Finder>				finders;
	private Collection<SurvivalClass>		survivalClasses;


	@NotNull
	@OneToMany(mappedBy = "applicant")
	public Collection<Application> getApplications() {
		return this.applications;
	}
	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}
	@NotNull
	@ManyToMany
	public Collection<EmergencyContact> getEmergencyContacts() {
		return this.emergencyContacts;
	}
	public void setEmergencyContacts(final Collection<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}
	@NotNull
	@OneToMany(mappedBy = "explorer")
	public Collection<Story> getStories() {
		return this.stories;
	}
	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@OneToMany(mappedBy = "explorer")
	public Collection<Finder> getFinders() {
		return this.finders;
	}
	public void setFinders(final Collection<Finder> finders) {
		this.finders = finders;
	}

	@NotNull
	@ManyToMany
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

}
