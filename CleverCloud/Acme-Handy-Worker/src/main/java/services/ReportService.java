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
import domain.Actor;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
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
	private UtilityService utilityService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Report create() {
		Referee principal;
		Report result;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		result = new Report();

		Collection<Note> notes = new ArrayList<Note>();
		result.setNotes(notes);

		return result;
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
		Assert.notNull(report);
		Assert.isTrue(principal.getComplaints().contains(report.getComplaint()));

		if (report.getId() == 0) {
			report.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		} else {

			Assert.isTrue(report.getComplaint().equals(
					this.findOne(report.getId()).getComplaint()));

			if (this.findOne(report.getId()).getIsFinal()) {
				Assert.isTrue(report.getIsFinal());
				Assert.isTrue(report.getAttachments().equals(
						this.findOne(report.getId()).getAttachments()));
				Assert.isTrue(report.getDescription().equals(
						this.findOne(report.getId()).getDescription()));
			}
		}

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(report.getDescription());

		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam) {
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
		Assert.isTrue(principal.getComplaints().contains(report.getComplaint()));

		reports = this.findReportByPrincipal();
		Assert.isTrue(reports.contains(report));

		this.reportRepository.delete(report.getId());

	}

	// Other business methods -------------------------------

	public Collection<Report> findReportByPrincipal() {
		Actor principal;
		Customer customer = null;
		HandyWorker handyWorker = null;
		Referee referee = null;
		Collection<Report> res;
		Collection<Report> reports;
		Collection<Complaint> complaints = new ArrayList<>();
		
		principal = this.actorService.findByPrincipal();
		
		if(principal instanceof Referee){
			referee = this.refereeService.findByPrincipal();
			complaints = referee.getComplaints();
			Assert.notNull(complaints);
		} else if(principal instanceof Customer){
			customer = this.customerService.findByPrincipal();
			complaints = customer.getComplaints();
			Assert.notNull(complaints);
		} else if(principal instanceof HandyWorker){
			handyWorker = this.handyWorkerService.findByPrincipal();
			complaints = this.complaintService.findComplaintsByHandyWorkerId(handyWorker.getId());
			Assert.notNull(complaints);
		}

		reports = this.findAll();
		Assert.notNull(reports);

		res = new ArrayList<Report>();
		for (final Report report : reports)
			for (final Complaint complaint : complaints)
				if (report.getComplaint() == complaint)
					res.add(report);

		return res;
	}

	public Double[] findNotesNumberOperations() {
		Double[] res = this.reportRepository.findNotesNumberOperations();
		return res;
	}
	
	public Report findReportByComplaint(int complaintId){
		Collection<Report> reports;
		Report result = new Report();
		
		reports = this.findAll();
		
		for (Report r: reports){
			if(r.getComplaint().getId() == complaintId){
				result = r;
				break;
			}
		}
		return result;
	}
}
