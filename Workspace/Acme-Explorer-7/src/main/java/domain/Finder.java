
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String				keyword;
	private Double				priceMin;
	private Double				priceMax;
	private Date				dateMin;
	private Date				dateMax;
	private Explorer			explorer;
	private Date				moment;
	private Collection<Trip>	results;


	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Min(0)
	@Digits(integer = 5, fraction = 2)
	public Double getPriceMin() {
		return this.priceMin;
	}
	public void setPriceMin(final Double priceMin) {
		this.priceMin = priceMin;
	}
	@Min(0)
	@Digits(integer = 5, fraction = 2)
	public Double getPriceMax() {
		return this.priceMax;
	}
	public void setPriceMax(final Double priceMax) {
		this.priceMax = priceMax;
	}
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDateMin() {
		return this.dateMin;
	}
	public void setDateMin(final Date dateMin) {
		this.dateMin = dateMin;
	}
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDateMax() {
		return this.dateMax;
	}
	public void setDateMax(final Date dateMax) {
		this.dateMax = dateMax;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Explorer getExplorer() {
		return this.explorer;
	}
	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@ManyToMany
	public Collection<Trip> getResults() {
		return this.results;
	}

	public void setResults(final Collection<Trip> results) {
		this.results = results;
	}

}
