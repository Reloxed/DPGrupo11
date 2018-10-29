
package domain;

import java.util.Collection;

import javax.persistence.Entity;

@Entity
public class Administrator extends Actor {

	private Collection<Category>	categories;
	private Collection<Warranty>	warranties;


	public Collection<Category> getCategories(){
		return this.categories;
	}
	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	public Collection<Warranty> getWarranties(){
		return this.warranties;
	}
	public void setWarranties(final Collection<Warranty> warranties) {
		this.warranties = warranties;
	}

}
