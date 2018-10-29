package domain;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Complaint extends DomainEntity {

	private String ticker;
	private Date moment;
	private String description;
	private Collection<String> attachements;
	private FixUpTask fixUpTask;
	private Collection<Report> reports;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\d{6}-[A-Z0-9]{6}")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Past
	@NotNull
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@URL
	public Collection<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(Collection<String> attachements) {
		this.attachements = attachements;
	}

	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Valid
	@NotNull
	public Collection<Report> getReports() {
		return reports;
	}

	public void setReports(Collection<Report> reports) {
		this.reports = reports;
	}

}
