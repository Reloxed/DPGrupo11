package domain;

import java.util.Collection;

import javax.validation.Valid;

public class Administrator extends Actor {

	private Collection<Category> categories;
	private Collection<Warranty> warranties;

	@Valid
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	@Valid
	public Collection<Warranty> getWarranties() {
		return this.warranties;
	}

	public void setWarranties(final Collection<Warranty> warranties) {
		this.warranties = warranties;
	}

}
