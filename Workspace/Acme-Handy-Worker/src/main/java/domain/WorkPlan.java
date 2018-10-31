package domain;

import java.util.Collection;

import org.hibernate.validator.constraints.NotEmpty;

public class WorkPlan extends DomainEntity{
	
	private Collection<Phase> phases;

	@NotEmpty
	public Collection<Phase> getPhases() {
		return phases;
	}

	public void setPhases(Collection<Phase> phases) {
		this.phases = phases;
	}

}
