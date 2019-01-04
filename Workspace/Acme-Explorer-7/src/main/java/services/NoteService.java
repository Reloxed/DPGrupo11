
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Administrator;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class NoteService {

	// Managed Repository
	@Autowired
	private NoteRepository			noteRepository;

	// Supporting services

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors
	public NoteService() {
		super();
	}
	// Simple CRUD methods

	public Note create() {
		Auditor principal;
		Note result;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);
		result = new Note();
		result.setAuditor(principal);
		return result;
	}

	public Note save(final Note note) {
		Note result;
		Auditor principal;
		Date moment;
		Trip trip;
		Collection<String> spamwords;
		Collection<Note> notes, updated;

		Assert.notNull(note);
		Assert.isTrue(note.getId() == 0);

		principal = this.auditorService.findByPrincipal();

		Assert.notNull(principal);

		trip = note.getTrip();
		Assert.notNull(trip);

		moment = new Date(System.currentTimeMillis() - 1);
		Assert.isTrue(moment.after(trip.getPublicationDate()));
		note.setMoment(moment);
		note.setMomentReply(null);
		note.setManagerReply(null);
		note.setAuditor(principal);

		result = this.noteRepository.save(note);

		Assert.notNull(result);

		spamwords = this.customisationService.find().getSpamWords();
		for (final String spamword : spamwords)
			if (result.getRemark().toLowerCase().contains(spamword.toLowerCase())) {
				principal.setIsSuspicious(true);
				break;
			}

		notes = principal.getNotes();
		updated = new ArrayList<Note>(notes);
		updated.add(result);
		principal.setNotes(updated);

		notes = trip.getNotes();
		updated = new ArrayList<Note>(notes);
		updated.add(result);
		trip.setNotes(updated);

		return result;

	}

	public Note findOne(final int id) {
		Note result;
		result = this.noteRepository.findOne(id);
		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	// Other business methods
	//32. An actor who is authenticated as a manager must be able to:
	//	1. List the notes that an auditor has written on his or her trips and write a reply.
	public List<Note> findAllNotesOfPrincipalManager() {
		List<Note> result;
		Manager principal;
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.noteRepository.findByManager(principal.getId());
		return result;

	}

	public Note postManagerReply(final Note note) {
		List<Note> allNotes;
		Collection<String> spamwords;
		Manager principal;
		Note result;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		allNotes = this.findAllNotesOfPrincipalManager();
		Assert.isTrue(allNotes.contains(note));

		Assert.isTrue(this.noteRepository.findOne(note.getId()).getManagerReply() == null);
		Assert.isTrue(note.getId() != 0);

		spamwords = this.customisationService.find().getSpamWords();
		for (final String spamword : spamwords)
			if (note.getManagerReply().toLowerCase().contains(spamword.toLowerCase())) {
				principal.setIsSuspicious(true);
				break;
			}

		note.setMomentReply(new Date(System.currentTimeMillis() - 1));

		result = this.noteRepository.save(note);

		return result;

	}

	//33. An actor who is authenticated as an auditor must be able to:
	//1. Manage his notes on trips, which includes listing and writing them.
	//Once a note is written, it cannot be modified at all or deleted.

	public Collection<Note> findByAuditor() {
		final Collection<Note> result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.noteRepository.findByAuditor(principal.getId());
		Assert.notNull(result);
		return result;

	}

	public Double averageNotesPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.noteRepository.averageNotesPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer maxNotesPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.noteRepository.maxNotesPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer minNotesPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.noteRepository.minNotesPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationNotesPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.noteRepository.stdDeviationNotesPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

}
