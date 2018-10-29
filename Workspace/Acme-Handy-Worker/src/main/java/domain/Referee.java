
package domain;

import java.util.Collection;

public class Referee extends Actor {

	private Collection<Complaint>	complaints;
	private Collection<Note>		notes;


	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}
}
