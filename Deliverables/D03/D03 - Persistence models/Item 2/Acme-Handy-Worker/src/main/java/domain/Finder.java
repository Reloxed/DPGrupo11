
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Money					priceLow;
	private Money					priceHigh;
	private Date					startMoment;
	private Date					endMoment;
	private Date					searchMoment;
	private Category				category;
	private Warranty				warranty;
	private Collection<FixUpTask>	fixuptask;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@AttributeOverride(name = "quantity", column = @Column(name = "priceLow"))
	public Money getPriceLow() {
		return this.priceLow;
	}

	public void setPriceLow(final Money priceLow) {
		this.priceLow = priceLow;
	}

	@AttributeOverride(name = "quantity", column = @Column(name = "priceHigh"))
	public Money getPriceHigh() {
		return this.priceHigh;
	}

	public void setPriceHigh(final Money priceHigh) {
		this.priceHigh = priceHigh;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@Valid
	@OneToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
	@OneToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@Valid
	@OneToMany
	public Collection<FixUpTask> getFixuptask() {
		return this.fixuptask;
	}

	public void setFixuptask(final Collection<FixUpTask> fixuptask) {
		this.fixuptask = fixuptask;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getSearchMoment() {
		return this.searchMoment;
	}

	public void setSearchMoment(final Date searchMoment) {
		this.searchMoment = searchMoment;
	}

}