/*
 * RegistrationTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private AuditorService	auditorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindAuditsByTrip() {
		List<Audit> result;
		result = this.auditService.findAuditsByTrip(1199);
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
	}

	@Test
	public void testFindByAuditor() {
		Collection<Audit> result;
		super.authenticate("auditor1");
		result = this.auditService.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 4);
		super.unauthenticate();

	}

	@Test
	public void testSave1() {
		super.authenticate("auditor1");
		Audit audit, saved = null;
		Trip trip;
		Collection<Audit> audits;
		List<String> linkAttachments;

		linkAttachments = new ArrayList<String>();
		linkAttachments.add("https://linkAttachments.com/link1");
		linkAttachments.add("Not valid");

		audit = this.auditService.create();
		audit.setTitle("Title");
		audit.setDescription("Description");
		audit.setLinkAttachment(linkAttachments);

		trip = this.tripService.findOne(1202); // Trip 4 
		audit.setTrip(trip);

		try {
			saved = this.auditService.save(audit, true); // It won't be saved because it contains a link that is not valid
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");

		audits = this.auditService.findAll();
		Assert.isTrue(!audits.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSave2() {
		super.authenticate("auditor1");
		Audit audit, saved = null;
		Trip trip;
		Collection<Audit> audits;
		List<String> linkAttachments;

		linkAttachments = new ArrayList<String>();
		linkAttachments.add("https://linkAttachments.com/link1");

		audit = this.auditService.create();
		audit.setTitle("Title");
		audit.setDescription("Description");
		audit.setLinkAttachment(linkAttachments);

		trip = this.tripService.findOne(1204); // Trip 6 is not published yet. Therefore the audit won't be created
		audit.setTrip(trip);

		try {
			saved = this.auditService.save(audit, true); // 
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");

		audits = this.auditService.findAll();
		Assert.isTrue(!audits.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSave3() {
		super.authenticate("auditor1");
		Audit audit, saved = null;
		Trip trip;
		Collection<Audit> audits;
		List<String> linkAttachments;

		linkAttachments = new ArrayList<String>();
		linkAttachments.add("https://linkAttachments.com/link1");

		audit = this.auditService.create();
		audit.setTitle("Title");
		audit.setDescription("Description");
		audit.setLinkAttachment(linkAttachments);

		trip = this.tripService.findOne(1202); // Trip 4
		audit.setTrip(trip);

		Assert.isTrue(this.auditorService.findByPrincipal().getAudits().size() == 4); // Auditor1 has 4 audits
		Assert.isTrue(this.tripService.findOne(1202).getAudits().size() == 0); // Trip 4 doesn't hace any audit records

		try {
			saved = this.auditService.save(audit, true); // 
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");

		audits = this.auditService.findAll();
		Assert.isTrue(audits.contains(saved));

		super.authenticate(null);
		super.authenticate("auditor1");

		Assert.isTrue(this.auditorService.findByPrincipal().getAudits().size() == 5); // Auditor1 now has 5 audits
		Assert.isTrue(this.tripService.findOne(1202).getAudits().size() == 1); // Trip4 now has an audit record

		super.authenticate(null);

	}

	@Test
	public void testEditAuditNotInDrafMode() {
		List<Audit> result;
		Audit toModify;
		super.authenticate("auditor1");
		result = new ArrayList<Audit>(this.auditService.findByPrincipal());
		Assert.notNull(result);
		toModify = result.get(0);
		Assert.isTrue(!toModify.getIsDraft());
		toModify.setDescription("CHANGED");
		try {
			toModify = this.auditService.save(toModify, false);
		} catch (final Exception e) {

		}
		super.unauthenticate();

	}

	@Test
	public void testEditAuditInDraftMode() {
		List<Audit> result;
		Audit saved;
		Audit toModify;
		Auditor principal;
		super.authenticate("auditor1");
		result = new ArrayList<Audit>(this.auditService.findByPrincipal());
		Assert.notNull(result);
		toModify = result.get(1);
		Assert.isTrue(toModify.getIsDraft());
		toModify.setDescription("CHANGED");
		saved = this.auditService.save(toModify, true);
		Assert.notNull(saved);
		Assert.isTrue(saved.getDescription() == "CHANGED");
		principal = saved.getAuditor();
		Assert.isTrue(principal.getAudits().contains(saved));
		super.unauthenticate();

	}

	@Test
	public void testDeleteAuditNotInDraftMode() {
		List<Audit> result;
		Audit toDelete;
		super.authenticate("auditor1");
		result = new ArrayList<Audit>(this.auditService.findByPrincipal());
		Assert.notNull(result);
		toDelete = result.get(0);
		Assert.isTrue(!toDelete.getIsDraft());
		try {
			this.auditService.delete(toDelete);
		} catch (final Exception e) {

		}
		Assert.isTrue(toDelete.getAuditor().getAudits().contains(toDelete));
		Assert.isTrue(toDelete.getTrip().getAudits().contains(toDelete));
		super.unauthenticate();
	}

	@Test
	public void testDeleteAuditInDraftMode() {
		List<Audit> result;
		Audit toDelete;
		super.authenticate("auditor1");
		result = new ArrayList<Audit>(this.auditService.findByPrincipal());
		Assert.notNull(result);
		toDelete = result.get(1);
		Assert.isTrue(toDelete.getIsDraft());

		Assert.isTrue(this.auditorService.findByPrincipal().getAudits().size() == 4);
		Assert.isTrue(this.tripService.findOne(1201).getAudits().size() == 2);

		this.auditService.delete(toDelete);

		Assert.isTrue(!toDelete.getAuditor().getAudits().contains(toDelete));
		Assert.isTrue(!toDelete.getTrip().getAudits().contains(toDelete));

		Assert.isTrue(this.auditorService.findByPrincipal().getAudits().size() == 3);
		Assert.isTrue(this.tripService.findOne(1201).getAudits().size() == 1);

		super.unauthenticate();

	}

}
