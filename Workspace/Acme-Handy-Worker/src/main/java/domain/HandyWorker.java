package domain;

import java.util.Collection;

public class HandyWorker extends Actor {

	private String make;
	private Collection<Endorsement> endorsements;
	private Collection<Tutorial> tutorial;
	private Application application;
	private Finder finder;
	private Curriculum curriculum;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Collection<Endorsement> getEndorsements() {
		return endorsements;
	}

	public void setEndorsements(Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	public Collection<Tutorial> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Collection<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Finder getFinder() {
		return finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	public Double score(int p, int n) {
		return p - n;
	}

}
