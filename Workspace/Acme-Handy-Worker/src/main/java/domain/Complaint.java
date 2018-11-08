package domain;

import java.sql.Date;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Complaint extends DomainEntity {

	private String ticker;
	private Date moment;
	private String description;
	private String attachements;
	private FixUpTask fixUpTask;
	private Report report;

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

	public String getAttachements() {
		return attachements;
	}

	public void setAttachements(String attachements) {
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
	
	@Valid
	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
