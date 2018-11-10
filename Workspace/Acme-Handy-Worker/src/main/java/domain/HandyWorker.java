package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	private double score;
	private String make;
	private Collection<Endorsement> endorsements;
	private Collection<Tutorial> tutorial;
	private Collection<Application> applications;
	private Finder finder;
	private Curriculum curriculum;

	@NotBlank
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	// ¿?
	public Collection<Endorsement> getEndorsements() {
		return endorsements;
	}

	public void setEndorsements(Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	@Valid
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	// ¿?
	public Collection<Tutorial> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Collection<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}

	@Valid
	@ElementCollection
	@OneToMany(mappedBy = "handyWorker")
	// ¿?
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	public Finder getFinder() {
		return finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}

	@NotNull
	@Valid
	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
