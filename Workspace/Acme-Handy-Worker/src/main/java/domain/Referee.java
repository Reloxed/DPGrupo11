package domain;

import java.util.Collection;

import javax.persistence.Entity;

@Entity
public class Referee extends Actor {

	private Collection<Complaint> complaints;

	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
}
