
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

	private String					ticker;
	private Date					publishedMoment;
	private String					description;
	private String					address;
	private Money					maxPrice;
	private Date					startMoment;
	private Date					endMoment;
	private Collection<Application>	application;
	private Category				category;
	private Warranty				warranty;
	private Collection<Complaint>	complaints;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9]{6}")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Valid
	@NotNull
	public Money getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Money maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@OneToMany(mappedBy = "fixUpTask")
	@Valid
	public Collection<Application> getApplication() {
		return this.application;
	}

	public void setApplication(final Collection<Application> application) {
		this.application = application;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}
	
	@Valid
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

}
