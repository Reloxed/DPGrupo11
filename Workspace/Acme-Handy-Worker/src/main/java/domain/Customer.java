package domain;

import java.util.Collection;

import javax.validation.Valid;


import org.hibernate.validator.constraints.Range;

public class Customer extends Actor {

	private double score;
	private Collection<Endorsement> endorsements;
	private Collection<FixUpTask> fixUpTasks;
	private Collection<Complaint> complaints;

	
	@Valid
	public Collection<Endorsement> getEndorsements() {
		return endorsements;
	}
			
	public void setEndorsements(Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	
	@Valid
	public Collection<FixUpTask> getFixuptasks() {
		return fixUpTasks;
	}

	public void setFixuptasks(Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Valid
	public Collection<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
	

}
