package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
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
	@ManyToOne(optional=true)
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Valid
	@OneToMany(mappedBy="parentCategory")
	@ElementCollection
	public Collection<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}

	public String toString() {
		return "spanishName: " + spanishName + ", englishName: " + englishName
				+ ", parentCategory: " + parentCategory + ", childCategories: "
				+ childCategories + ",[" + super.toString() + "]";
	}

}
