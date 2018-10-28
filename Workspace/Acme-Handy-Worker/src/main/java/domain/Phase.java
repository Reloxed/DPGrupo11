package domain;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Phase extends DomainEntity{

	private String title;
	private String description;
	private Date startDate;
	private Date endDate;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Past
	public Date getStartDate() {
		return startDate;
	}

	@NotNull
	@Past
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	public Date getEndDate() {
		return endDate;
	}

	@NotNull
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
