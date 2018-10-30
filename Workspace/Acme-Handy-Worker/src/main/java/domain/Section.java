package domain;

import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;

public class Section extends DomainEntity {

	// Attributes

	private String title;
	private String text;
	private Collection<String> picture;

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

	public Collection<String> getPicture() {
		return this.picture;
	}

	public void setPicture(final Collection<String> picture) {
		this.picture = picture;
	}

}
