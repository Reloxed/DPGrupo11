package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String title;
	private String terms;
	private boolean isFinal;
	private String laws;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	public String getLaws() {
		return this.laws;
	}

	public void setLaws(final String laws) {
		this.laws = laws;
	}

	public String toString() {
		return "title: " + title + ", terms: " + terms + ", isFinal: "
				+ isFinal + ", laws: " + laws + ",[" + super.toString() + "]";
	}
}
