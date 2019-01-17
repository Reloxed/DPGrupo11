package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class NoteService {

	// Managed repository ----------------------------

	@Autowired
	private NoteRepository noteRepository;

	// Supporting services -------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private RefereeService refereeService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private UtilityService utilityService;

	// Constructors ------------------------------------

	public NoteService() {
		super();
	}

	// CRUD Methods --------------------------------

	public Note create() {
		Actor principal;
		Note result;
		boolean validCommenter = false;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);		

		if (principal instanceof Customer || principal instanceof Referee || principal instanceof HandyWorker) {
			validCommenter = true;
		} 
		
		Assert.isTrue(validCommenter);		
		result = new Note();
		return result;
	}

	public Note save(final Note note) {
		Note result;
		Report report;
		Collection<Note> notes;
		Actor principal;
		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		Assert.notNull(note);
		Assert.isTrue(note.getId() == 0);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		report = note.getReport();
		Assert.isTrue(report.getIsFinal());		
		
		if(note.getId() != 0) {
			Assert.isTrue(note.getPublishedMoment().equals(this.findOne(note.getId()).getPublishedMoment()));
			Assert.isTrue(note.getReport().equals(this.findOne(note.getId()).getReport()));
		}
		
		if (principal instanceof Customer) {
			customer = this.customerService.findByPrincipal();
			Assert.notNull(note.getCustomerComment());
			if (note.getId() != 0) {
				if (note.getHandyWorkerComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getHandyWorkerComment().equals(note.getHandyWorkerComment()));
				}
				if (note.getRefereeComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getRefereeComment().equals(note.getRefereeComment()));
				}
			}
			Assert.isTrue(customer.getComplaints().contains(report.getComplaint()));
			
		} else if (principal instanceof Referee) {
			referee = this.refereeService.findByPrincipal();
			Assert.notNull(note.getRefereeComment());
			if (note.getId() != 0) {
				if (note.getHandyWorkerComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getHandyWorkerComment().equals(note.getHandyWorkerComment()));
				}
				if (note.getCustomerComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getCustomerComment().equals(note.getCustomerComment()));
				}
			}
			Assert.isTrue(referee.getComplaints().contains(report.getComplaint()));

		} else if (principal instanceof HandyWorker) {
			handyWorker = this.handyWorkerService.findByPrincipal();
			Assert.notNull(note.getHandyWorkerComment());
			if (note.getId() != 0) {
				if (note.getCustomerComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getCustomerComment().equals(note.getCustomerComment()));
				}
				if (note.getRefereeComment() != null) {
					Assert.isTrue(this.findOne(note.getId()).getRefereeComment().equals(note.getRefereeComment()));
				}
			}
			Assert.isTrue(this.complaintService.findComplaintsByHandyWorkerId(handyWorker.getId()).contains(report.getComplaint()));
		}
			
		if (customer == null && handyWorker == null && referee == null) {
			Assert.notNull(customer);
		}
		
		note.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		
		List<String> atributosAComprobar = new ArrayList<>();
		if (note.getCustomerComment() != null)
			atributosAComprobar.add(note.getCustomerComment());
		if (note.getRefereeComment() != null)
			atributosAComprobar.add(note.getRefereeComment());
		if (note.getHandyWorkerComment() != null)
			atributosAComprobar.add(note.getHandyWorkerComment());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}

		result = this.noteRepository.saveAndFlush(note);
		Assert.notNull(result);

		notes = new ArrayList<Note>();
		notes = report.getNotes();
		notes.add(result);
		report.setNotes(notes);

		return result;
	}

	public Note findOne(final int id) {
		Note result;

		result = this.noteRepository.findOne(id);
		Assert.notNull(result);

		return result;

	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;

	}
}
