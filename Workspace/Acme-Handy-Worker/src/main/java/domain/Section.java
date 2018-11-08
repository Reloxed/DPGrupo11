package domain;



import org.hibernate.validator.constraints.NotBlank;

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

	public String getPicture() {
		return this.pictures;
	}

	public void setPicture(final String pictures) {
		this.pictures = pictures;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
}
