package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository ------------------------------------

	@Autowired
	private ReportRepository reportRepository;

	// Supporting services -----------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private NoteService noteService;

	// Simple CRUD methods -----------------------------------

	public Report create() {
		return new Report();
	}

	public Collection<Report> findAll() {
		return reportRepository.findAll();
	}

	public Report findOne(int reportId) {
		return reportRepository.findOne(reportId);
	}

	public Report save(Report r) {
		return reportRepository.save(r);
	}

	// TODO
	public void delete(Report r) {
		reportRepository.delete(r);
	}

	// TODO
	// Other business methods -------------------------------
}
