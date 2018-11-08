package domain;

import java.util.Collection;



import org.hibernate.validator.constraints.NotBlank;

public class MessageBox extends DomainEntity {

	private String name;
	private boolean isPredefined;
	private Collection<Message> messages;


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

	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	
}
