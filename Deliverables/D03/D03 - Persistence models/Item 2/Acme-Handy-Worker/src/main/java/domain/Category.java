
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		spanishName;
	private String		englishName;
	private Category	parentCategory;


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

	@Override
	public String toString() {
		return "spanishName: " + this.spanishName + ", englishName: " + this.englishName + ", parentCategory: " + this.parentCategory + ",[" + super.toString() + "]";
	}

}
