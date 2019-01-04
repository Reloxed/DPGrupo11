
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	moment;
	private String	remark;
	private String	managerReply;
	private Date	momentReply;
	private Auditor	auditor;
	private Trip	trip;


	public String getManagerReply() {
		return this.managerReply;
	}
	public void setManagerReply(final String managerReply) {
		this.managerReply = managerReply;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMomentReply() {
		return this.momentReply;
	}
	public void setMomentReply(final Date momentReply) {
		this.momentReply = momentReply;
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
	@NotBlank
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(final String remark) {
		this.remark = remark;
	}
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}
	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}
	public void setTrip(final Trip trip) {
		this.trip = trip;
	}
}
