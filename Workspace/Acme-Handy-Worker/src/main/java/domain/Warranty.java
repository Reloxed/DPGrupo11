
package domain;

import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;

public class Warranty extends DomainEntity {

	private String			title;
	private String			terms;
	private boolean			isFinal;
	private Collection<Law>	laws;


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

	public boolean isFinal() {
		return this.isFinal;
	}
	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Collection<Law> getLaws() {
		return this.laws;
	}
	public void setLaws(final Collection<Law> laws) {
		this.laws = laws;
	}

}
