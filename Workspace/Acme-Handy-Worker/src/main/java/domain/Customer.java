package domain;

import java.util.Set;

public class Customer extends DomainEntity {

	public Set<Endorsement> endorsements;
	public Set<Application> applications;
	public Set<FixUpTask> fixuptasks;
	
}
