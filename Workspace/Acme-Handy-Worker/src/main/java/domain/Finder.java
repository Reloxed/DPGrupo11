package domain;

import java.util.Date;

public class Finder {

	private String keyWord;
	private Money priceLow;
	private Money priceHigh;
	private Date startDate;
	private Date endDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}

	public Collection<FixUpTask> getFixuptask() {
		return fixuptask;
	}

	public void setFixuptask(Collection<FixUpTask> fixuptask) {
		this.fixuptask = fixuptask;
	}

}
