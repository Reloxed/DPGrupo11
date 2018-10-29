
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class MessageBox extends DomainEntity {

	private String				name;
	private boolean				isPredefined;

	private Collection<Message>	messages;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getIsPredefined() {
		return this.isPredefined;
	}

	public void setIsPredefined(final boolean isPredefined) {
		this.isPredefined = isPredefined;
	}

	@NotNull
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}
}
