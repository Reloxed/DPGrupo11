
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	private ReportRepository			reportRepository;

	// Supporting services -----------------------------------

	@Autowired
	private RefereeService				refereeService;

	@Autowired
	private UtilityService	utilityService;


	// Constructors ------------------------------------

	public ReportService() {
		super();
	}

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
		Collection<Report> result;
		result = this.reportRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Report findOne(final int reportId) {
		Report result;
		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);
		return result;
	}

	public Report save(final Report report) {
		Referee principal;
		Report res;
		

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		if (report.getId() != 0) {
			Assert.isTrue(report.getComplaint().equals(this.findOne(report.getId()).getComplaint()));
		}
		Assert.isTrue(report.getPublishedMoment().before(new Date(System.currentTimeMillis())));

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(report.getDescription());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}
		
		res = this.reportRepository.save(report);
		Assert.notNull(res);

		return res;
	}

	public void delete(final Report report) {
		Referee principal;
		Collection<Report> reports;

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(!report.getIsFinal());

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		reports = this.findReportByPrincipal();
		Assert.isTrue(reports.contains(report));
		
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
		for (final Report report : reports)
			for (final Complaint complaint : complaints)
				if (report.getComplaint() == complaint)
					res.add(report);

		return res;
	}
}
