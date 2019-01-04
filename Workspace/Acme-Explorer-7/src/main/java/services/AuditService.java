
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import repositories.AuditRepository;
import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditService {

	// Managed Repository
	@Autowired
	private AuditRepository			auditRepository;

	// Supporting services
	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors
	public AuditService() {
		super();
	}

	// Simple CRUD methods
	public Audit create() {
		Audit result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Audit();
		result.setLinkAttachment(new ArrayList<String>());
		result.setAuditor(principal);
		return result;
	}

	public Audit findOne(final int auditId) {

		Audit result;

		result = this.auditRepository.findOne(auditId);

		Assert.notNull(result);

		return result;
	}

	public Audit save(final Audit audit, final Boolean isDraft) {
		Audit result;
		Auditor principal;
		Date moment;
		Collection<String> linkAttachments;
		Trip trip;
		Boolean areAllLinksValid;
		Collection<String> spamwords;
		Collection<Audit> audits, updated;

		Assert.notNull(audit);

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(audit);

		trip = audit.getTrip();

		if (audit.getId() == 0) {
			moment = new Date(System.currentTimeMillis() - 1);
			Assert.isTrue(moment.after(trip.getPublicationDate()));
			audit.setMoment(moment);
		} else {
			Boolean checkDraft;
			checkDraft = this.auditRepository.findOne(audit.getId()).getIsDraft();
			Assert.isTrue(checkDraft);
			moment = this.auditRepository.findOne(audit.getId()).getMoment();
			audit.setMoment(moment);
		}

		linkAttachments = audit.getLinkAttachment();

		areAllLinksValid = true;
		for (final String link : linkAttachments)
			if (!(ResourceUtils.isUrl(link) || link.equals(""))) {
				areAllLinksValid = false;
				break;
			}

		Assert.isTrue(areAllLinksValid);

		audit.setIsDraft(isDraft);

		result = this.auditRepository.save(audit);

		spamwords = this.customisationService.find().getSpamWords();
		for (final String spamword : spamwords)
			if (result.getDescription().toLowerCase().contains(spamword.toLowerCase()) || result.getTitle().toLowerCase().contains(spamword.toLowerCase())) {
				principal.setIsSuspicious(true);
				break;
			}

		audits = principal.getAudits();
		updated = new ArrayList<Audit>(audits);
		updated.add(result);
		principal.setAudits(updated);

		audits = trip.getAudits();
		updated = new ArrayList<Audit>(audits);
		updated.add(result);
		trip.setAudits(updated);

		return result;
	}

	public void delete(final Audit audit) {
		Auditor principal;
		Collection<Audit> audits, updated;
		Trip trip;
		Boolean checkDraft;

		Assert.isTrue(audit.getId() != 0);

		checkDraft = this.auditRepository.findOne(audit.getId()).getIsDraft();

		Assert.isTrue(checkDraft);

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(audit.getAuditor() == principal);

		trip = audit.getTrip();

		audits = trip.getAudits();
		updated = new ArrayList<Audit>(audits);
		updated.remove(audit);
		trip.setAudits(updated);

		audits = principal.getAudits();
		updated = new ArrayList<Audit>(audits);
		updated.remove(audit);
		principal.setAudits(updated);

		this.auditRepository.delete(audit);

	}

	// Other business methods
	public List<Audit> findAuditsByTrip(final int tripId) {
		List<Audit> result;
		result = this.auditRepository.findAuditsByTrip(tripId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Audit> findByPrincipal() {
		final Collection<Audit> result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.auditRepository.findByAuditor(principal.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.auditRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Double averageAuditsPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.auditRepository.averageAuditsPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer maxAuditsPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.auditRepository.maxAuditsPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer minAuditsPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.auditRepository.minAuditsPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationAuditsPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.auditRepository.stdDeviationAuditsPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

}
