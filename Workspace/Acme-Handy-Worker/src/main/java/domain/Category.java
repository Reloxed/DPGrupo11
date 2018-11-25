
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					spanishName;
	private String					englishName;
	private Category				parentCategory;
	private Collection<Category>	childCategories;


	@NotBlank
	public String getSpanishName() {
		return this.spanishName;
	}

	public void setSpanishName(final String spanishName) {
		this.spanishName = spanishName;
	}

	@NotBlank
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@NotNull
	@OneToMany(mappedBy = "parentCategory")
	public Collection<Category> getChildCategories() {
		return this.childCategories;
	}

	public void setChildCategories(final Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}

	@Override
	public String toString() {
		return "spanishName: " + this.spanishName + ", englishName: " + this.englishName + ", parentCategory: " + this.parentCategory + ",[" + super.toString() + "]";
	}

}
