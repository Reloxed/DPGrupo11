package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
public class CountryCode extends DomainEntity {
	
	private int code;
	
	@Range(min = 1, max = 999)
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
