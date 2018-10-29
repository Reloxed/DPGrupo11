package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
public class Finder extends DomainEntity {

	private String keyWord;
	private Money priceLow;
	private Money priceHigh;
	private Date startMoment;
	private Date endMoment;
	private Category category;
	private Warranty warranty;
	private Collection<FixUpTask> fixuptask;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Money getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(Money priceLow) {
		this.priceLow = priceLow;
	}

	public Money getPriceHigh() {
		return priceHigh;
	}

	public void setPriceHigh(Money priceHigh) {
		this.priceHigh = priceHigh;
	}

	@Past
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@NotNull
	@Valid
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@NotNull
	@Valid
	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}

	@NotNull
	@Valid
	public Collection<FixUpTask> getFixuptask() {
		return fixuptask;
	}

	public void setFixuptask(Collection<FixUpTask> fixuptask) {
		this.fixuptask = fixuptask;
	}

}
