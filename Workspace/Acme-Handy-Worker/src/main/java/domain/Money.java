
package domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

public class Money {

	private double	quantity;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final double quantity) {
		this.quantity = quantity;
	}
}
