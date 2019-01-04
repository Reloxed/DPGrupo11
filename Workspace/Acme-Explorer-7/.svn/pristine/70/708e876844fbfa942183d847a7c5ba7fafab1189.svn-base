
package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;


import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	private String				name;
	

	@NotEmpty
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
