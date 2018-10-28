package domain;

import javax.persistence.Embeddable;

@Embeddable
public class Priority extends DomainEntity{
	
	public static final String	HIGH		= "HIGH";
	public static final String	NEUTRAL		= "NEUTRAL";
	public static final String	LOW		= "LOW";

}