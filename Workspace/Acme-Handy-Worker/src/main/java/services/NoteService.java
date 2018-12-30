package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
	private SystemConfigurationService systemConfigurationService;

	// Constructors ------------------------------------

	public NoteService() {
		super();
	}

	// CRUD Methods --------------------------------

	public Note create() {
		Actor principal;
		Note result;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Note();

		if (principal instanceof Customer) {

			Assert.isTrue(principal instanceof Customer);

			result.setCustomerComment(" ");

		} else if (principal instanceof Referee) {
			Assert.isTrue(principal instanceof Referee);

			result.setRefereeComment(" ");

		} else {
			Assert.isTrue(principal instanceof HandyWorker);

			result.setHandyWorkerComment(" ");

		}

		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		return result;
	}

	public Note save(final Note note) {
		Note result;
		Report report;
		Collection<Note> notes;
		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;
		

		Assert.notNull(note);
		Assert.isTrue(note.getId() == 0);
		
		try {
			
			customer = this.customerService.findByPrincipal();
			Assert.notNull(note.getCustomerComment());
			
		} catch (final IllegalArgumentException e) {	} 
		
		try {
			
			referee = this.refereeService.findByPrincipal();
			Assert.notNull(note.getRefereeComment());
			
		} catch (final IllegalArgumentException e) {	}
		
		try {
			
			handyWorker = this.handyWorkerService.findByPrincipal();
			Assert.notNull(note.getHandyWorkerComment());
			
		} catch (final IllegalArgumentException e) {	}
		
		if (customer == null && handyWorker == null && referee == null) {
			Assert.notNull(customer);
		}

		report = note.getReport();
		Assert.isTrue(report.getIsFinal());		
		

		final String[] spamWords = this.systemConfigurationService
				.findMySystemConfiguration().getSpamWords().split(",");
		final String[] customerComments = note.getCustomerComment().split(
				"(¿¡,.-_/!?) ");
		final String[] refereeComments = note.getRefereeComment().split(
				"(¿¡,.-_/!?) ");
		final String[] handyComments = note.getHandyWorkerComment().split(
				"(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String customerWord : customerComments)
				if (customerWord.toLowerCase().contains(word.toLowerCase())) {
					customer.setIsSuspicious(true);
					break;
				}
			for (final String refereeWord : refereeComments)
				if (refereeWord.toLowerCase().contains(word.toLowerCase())) {
					referee.setIsSuspicious(true);
					break;
				}
			for (final String handyWord : handyComments)
				if (handyWord.toLowerCase().contains(word.toLowerCase())){
					handyWorker.setIsSuspicious(true);
					break;
				}
		}

		note.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		
		result = this.noteRepository.saveAndFlush(note);
		Assert.notNull(result);

		notes = new ArrayList<Note>();
		notes = report.getNotes();
		notes.add(result);
		report.setNotes(notes);

		return result;

	}

	public void delete(final Note note) {
		Actor principal;
		Report report;
		Collection<Note> notes, updated;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		report = note.getReport();
		Assert.notNull(report);

		notes = new ArrayList<Note>();
		notes = this.noteRepository.findAll();
		Assert.notNull(notes);

		Assert.isTrue(principal instanceof Customer
				|| principal instanceof Referee
				|| principal instanceof HandyWorker);

		// Assert.isTrue(!report.getIsFinal());
		this.noteRepository.delete(note);
		updated = new ArrayList<Note>(notes);
		updated.remove(note);
		Assert.isTrue(!updated.contains(note));
		report.setNotes(updated);
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
	// Other business methods -----------------------------
}
