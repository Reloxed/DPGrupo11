package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String keyWord;
	private Money priceLow;
	private Money priceHigh;
	private Date startMoment;
	private Date endMoment;
	private Date searchMoment;
	private Category category;
	private Warranty warranty;
	private Collection<FixUpTask> fixuptask;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@AttributeOverride(name = "quantity", column = @Column(name = "priceLow"))
	public Money getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(Money priceLow) {
		this.priceLow = priceLow;
	}

	@AttributeOverride(name = "quantity", column = @Column(name = "priceHigh"))
	public Money getPriceHigh() {
		return priceHigh;
	}

	public void setPriceHigh(Money priceHigh) {
		this.priceHigh = priceHigh;
	}

	@Temporal(TemporalType.TIMESTAMP)
	
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	
	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@Valid
	@OneToOne(optional = true)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Valid
	@OneToOne(optional = true)
	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}

	@Valid
	@OneToMany
	public Collection<FixUpTask> getFixuptask() {
		return fixuptask;
	}

	public void setFixuptask(Collection<FixUpTask> fixuptask) {
		this.fixuptask = fixuptask;
	}

	@Past
	@Temporal(TemporalType.TIME)
	@NotNull
	public Date getSearchMoment() {
		return searchMoment;
	}

	public void setSearchMoment(Date searchMoment) {
		this.searchMoment = searchMoment;
	}

}
