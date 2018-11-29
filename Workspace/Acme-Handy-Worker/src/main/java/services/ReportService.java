package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository ------------------------------------

	@Autowired
	private ReportRepository reportRepository;

	// Supporting services -----------------------------------

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private NoteService noteService;

	// Simple CRUD methods -----------------------------------

	public Report create() {
		Referee principal;
		Report res;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		res = new Report();

		return res;
	}

	public Collection<Report> findAll() {
		Collection<Report> res;
		res = this.reportRepository.findAll();
		return res;
	}

	public Report findOne(int reportId) {
		Report res;
		res = this.reportRepository.findOne(reportId);
		return res;
	}

	public Report save(Report report) {
		Referee principal;
		Report res;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(report.getPublishedMoment().before(
				new Date(System.currentTimeMillis())));

		res = this.reportRepository.save(report);
		Assert.notNull(res);

		return res;
	}

	public void delete(Report report) {
		Referee principal;
		Collection<Report> reports;

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(!report.getIsFinal());

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		reports = this.findReportByPrincipal();
		Assert.notEmpty(reports);
		Assert.isTrue(reports.contains(report));

		if (report.getNotes().size() != 0) {
			for (Note note : report.getNotes()) {
				this.noteService.delete(note);
			}
		}

		this.reportRepository.delete(report.getId());

	}

	// Other business methods -------------------------------

	public Collection<Report> findReportByPrincipal() {
		Referee principal;
		Collection<Report> res;
		Collection<Report> reports;
		Collection<Complaint> complaints;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		complaints = principal.getComplaints();
		Assert.notNull(complaints);

		reports = this.findAll();
		Assert.notNull(reports);

		res = new ArrayList<Report>();
		for (Report report : reports) {
			for (Complaint complaint : complaints) {
				if (report.getComplaint() == complaint) {
					res.add(report);
				}
			}
		}

		return res;
	}
}
