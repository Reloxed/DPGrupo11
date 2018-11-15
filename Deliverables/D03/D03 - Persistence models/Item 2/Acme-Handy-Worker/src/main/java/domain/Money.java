package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money {

	private double quantity;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final double quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return "Quantity: " + quantity;
	}

}
