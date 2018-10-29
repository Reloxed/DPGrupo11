package domain;

<<<<<<< HEAD

import javax.persistence.Entity;

=======
>>>>>>> 26df00516fec1a2872a2a638a5e57f733efd9b0e
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
