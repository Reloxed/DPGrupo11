package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
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
		Referee principal;
		Collection<Report> res;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		res = this.reportRepository.findAll();

		return res;
	}

	public Report findOne(int reportId) {
		Report res = null;
		Collection<Report> reports;

		reports = this.findByPrincipal();
		Assert.notEmpty(reports);

		for (Report report : reports) {
			if (report.getId() == reportId) {
				res = this.reportRepository.findOne(reportId);
			}
		}

		return res;
	}

	public Report save(Report report) {
		Referee principal;
		Report res;
		Collection<Report> reports;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(report.getPublishedMoment().before(
				LocalDate.now().toDate()));
		reports = this.findByPrincipal();
		Assert.notNull(reports);

		res = this.reportRepository.save(report);
		Assert.notNull(res);

		return res;
	}

	public void delete(Report report) {
		Referee principal;

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(!report.getIsFinal());

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		this.reportRepository.delete(report.getId());

	}

	// Other business methods -------------------------------

	public Collection<Report> findByPrincipal() {
		Referee principal;
		Collection<Report> res;
		Collection<Report> allReports;
		Collection<Complaint> complaints;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		complaints = principal.getComplaints();
		Assert.notNull(complaints);

		allReports = this.findAll();
		Assert.notNull(allReports);

		res = new ArrayList<Report>();
		for (Complaint complaint : complaints) {
			for (Report report : allReports) {
				if (complaint.equals(report.getComplaint())) {
					res.add(report);
					break;
				}
			}
		}

		return res;
	}
}
