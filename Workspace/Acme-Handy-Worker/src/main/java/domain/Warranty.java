package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Warranty {
	
	private String title;
	private String terms;
	private boolean isFinal;
	private Collection<Law> laws;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	@ElementCollection
	public Collection<Law> getLaws() {
		return laws;
	}
	public void setLaws(Collection<Law> laws) {
		this.laws = laws;
	}

}
