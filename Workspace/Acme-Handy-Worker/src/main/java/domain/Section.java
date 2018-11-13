package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	// Attributes

	private String title;
	private String text;
	private String pictures;
	private int number;

	// Methods

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
}
