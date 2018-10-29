package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Law extends DomainEntity {

	private String title;
	private String text;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
