package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class HandyWorker extends Actor {

	private String make;
	private Collection<Endorsement> endorsements;
	private Collection<Tutorial> tutorial;
	private Application application;
	private Finder finder;
	private Curriculum curriculum;

	@NotBlank
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@NotNull
	@Valid
	public Collection<Endorsement> getEndorsements() {
		return endorsements;
	}

	public void setEndorsements(Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	@NotNull
	@Valid
	public Collection<Tutorial> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Collection<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}

	@NotNull
	@Valid
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
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

	public double score(int p, int n) {
		return p - n;
	}

}
