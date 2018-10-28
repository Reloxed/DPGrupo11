package domain;

import java.util.Collection;

public class Customer extends Actor {

	private Collection<Endorsement> endorsements;
	private Collection<Application> applications;
	private Collection<FixUpTask> fixuptasks;

	public Collection<Endorsement> getEndorsements() {
		return endorsements;
	}

	public void setEndorsements(Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	public Collection<FixUpTask> getFixuptasks() {
		return fixuptasks;
	}

	public void setFixuptasks(Collection<FixUpTask> fixuptasks) {
		this.fixuptasks = fixuptasks;
	}

	public Double score(int p, int n) {
		return p - n;
	}
}
