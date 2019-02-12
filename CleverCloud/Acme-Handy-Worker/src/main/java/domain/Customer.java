package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "Customer")
public class Customer extends Endorser {

	private double score;

	private Collection<FixUpTask> fixUpTasks;
	private Collection<Complaint> complaints;
	private Collection<CreditCard> creditCards;

	@Valid
	@OneToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return fixUpTasks;
	}

	public void setFixUpTasks(Collection<FixUpTask> fixUpTasks) {
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
	@OneToMany
	public Collection<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@Valid
	@OneToMany
	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

}
