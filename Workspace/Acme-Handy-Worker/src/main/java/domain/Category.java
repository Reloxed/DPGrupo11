package domain;

import java.util.Collection;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

public class Category extends DomainEntity {

	private String spanishName;
	private String englishName;
	private Category parentCategory;
	private Collection<Category> childCategories;

	@NotBlank
	public String getSpanishName() {
		return spanishName;
	}

	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}

	@NotBlank
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Valid
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Valid
	public Collection<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}

}
