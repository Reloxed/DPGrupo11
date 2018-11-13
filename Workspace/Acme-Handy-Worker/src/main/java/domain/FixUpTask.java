package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

	private String ticker;
	private Date publishedMoment;
	private String description;
	private String address;
	private Money maxPrice;
	private Date startMoment;
	private Date endMoment;
	private Collection<Application> application;
	private Category category;
	private Warranty warranty;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9]{6}")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPublishedMoment() {
		return publishedMoment;
	}

	public void setPublishedMoment(Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Valid
	@NotNull
	public Money getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Money maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@OneToMany(mappedBy="fixUpTask")
	@Valid
	public Collection<Application> getApplication() {
		return application;
	}

	public void setApplication(Collection<Application> application) {
		this.application = application;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@NotNull
	@Valid
	@OneToOne(optional=false)
	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}

}
