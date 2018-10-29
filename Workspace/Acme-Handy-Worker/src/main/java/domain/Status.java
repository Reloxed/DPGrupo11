package domain;

import javax.persistence.Embeddable;

@Embeddable
public class Status {

	public static final String PENDING = "PENDING";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String REJECTED = "REJECTED";

}
