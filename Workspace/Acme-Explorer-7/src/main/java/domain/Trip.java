
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	private String						ticker;
	private String						title;
	private String						description;
	private double						price;
	private List<String>				requirements;
	private Date						publicationDate;
	private String						cancellationReason;
	private Date						startDate;
	private Date						endDate;
	private List<Stage>					stage;
	private Collection<Audit>			audits;
	private Category					category;
	private LegalText					legaltext;
	private Collection<Tag>				tags;
	private Collection<Sponsorship>		sponsorships;
	private Ranger						ranger;
	private Collection<Story>			stories;
	private Collection<Application>		applications;
	private Manager						manager;
	private Collection<SurvivalClass>	survivalClasses;
	private Collection<Note>			notes;


	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(0)
	@Digits(integer = 5, fraction = 2)
	public double getPrice() {
		return this.price;
	}
	public void setPrice(final double price) {
		this.price = price;
	}

	@NotNull
	@ElementCollection
	public List<String> getRequirements() {
		return this.requirements;
	}
	public void setRequirements(final List<String> requirements) {
		this.requirements = requirements;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationDate() {
		return this.publicationDate;
	}
	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getCancellationReason() {
		return this.cancellationReason;
	}
	public void setCancellationReason(final String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	public List<Stage> getStage() {
		return this.stage;
	}
	public void setStage(final List<Stage> stage) {
		this.stage = stage;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Audit> getAudits() {
		return this.audits;
	}
	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
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
	@ManyToOne(optional = false, cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	})
	public LegalText getLegaltext() {
		return this.legaltext;
	}
	public void setLegaltext(final LegalText legaltext) {
		this.legaltext = legaltext;
	}

	@NotNull
	@ManyToMany
	public Collection<Tag> getTags() {
		return this.tags;
	}
	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}
	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}
	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Story> getStories() {
		return this.stories;
	}
	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Application> getApplications() {
		return this.applications;
	}
	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}
	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}
	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}
	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

}
