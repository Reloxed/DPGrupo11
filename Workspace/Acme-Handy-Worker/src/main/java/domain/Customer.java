package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class Customer extends Actor {

	private double score;
	private Collection<Endorsement> endorsements;
	private Collection<Application> applications;
	private Collection<FixUpTask> fixuptasks;

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
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	public Collection<FixUpTask> getFixuptasks() {
		return fixuptasks;
	}

	public void setFixuptasks(Collection<FixUpTask> fixuptasks) {
		this.fixuptasks = fixuptasks;
	}

	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
