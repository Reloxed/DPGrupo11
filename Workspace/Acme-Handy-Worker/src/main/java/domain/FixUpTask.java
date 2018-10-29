package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9]{6}")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	public Date getPublishedMoment() {
		return publishedMoment;
	}

	public void setPublishedTime(Date publishedMoment) {
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
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	public Collection<Application> getApplication() {
		return application;
	}

	public void setApplication(Collection<Application> application) {
		this.application = application;
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

}
