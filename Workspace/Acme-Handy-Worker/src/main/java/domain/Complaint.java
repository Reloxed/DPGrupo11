package domain;

import java.sql.Date;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Complaint extends DomainEntity {

	private String ticker;
	private Date moment;
	private String description;
	private Collection<String> attachements;
	private FixUpTask fixUpTask;
	private Collection<Report> reports;

	@NotBlank
	@Pattern(regexp = "\\d{6}-[a-z,A-Z,0-9] {6}")
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

	public Collection<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(Collection<String> attachements) {
		this.attachements = attachements;
	}

	@Valid
	@NotNull
	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	public Collection<Report> getReports() {
		return reports;
	}

	public void setReports(Collection<Report> reports) {
		this.reports = reports;
	}

}
