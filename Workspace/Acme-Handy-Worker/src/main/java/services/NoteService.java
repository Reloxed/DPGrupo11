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


	// CRUD Methods --------------------------------

	//check in referee that the report is saves in final mode to create the note.
	public Note create(){
		Actor principal;
		Note result;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Note();

		if(principal instanceof Customer){
			Assert.isTrue(principal instanceof Customer);

			result.setCustomerComment(" ");


		}else if(principal instanceof Referee){
			Assert.isTrue(principal instanceof Referee);

			result.setRefereeComment(" ");

		}else{
			Assert.isTrue(principal instanceof HandyWorker);

			result.setHandyWorkerComment(" ");

		}

		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		return result;
	}		



	public Note save(Note note){
		Note result;
		Report report;
		Actor principal;
		Collection<Note>notes,updated;

		Assert.notNull(note);
		Assert.isTrue(note.getId()==0);//notes cannot be updated or deleted once they are saved to the database

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		report = note.getReport();
		Assert.notNull(report);

		if(principal instanceof Referee){
			Assert.isTrue(principal instanceof Referee);
			note.setRefereeComment(note.getRefereeComment());

		}else if(principal instanceof Customer){
			Assert.isTrue(principal instanceof Customer);
			note.setCustomerComment(note.getCustomerComment());

		}else{
			Assert.isTrue(principal instanceof HandyWorker);
			note.setHandyWorkerComment(note.getHandyWorkerComment());

		}
		note.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		note.setReport(report);
		Assert.isTrue(report.getIsFinal());
		result = this.noteRepository.saveAndFlush(note);
		Assert.notNull(result);

		notes = new ArrayList<Note>();
		notes = report.getNotes();
		updated = new ArrayList<Note>(notes);
		updated.add(result);
		report.setNotes(updated);

		return result;

	}

	public void delete(Note note){
		Actor principal;
		Report report;
		Collection<Note>notes,updated;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		report = note.getReport();
		Assert.notNull(report);

		notes = new ArrayList<Note>();
		notes = this.noteRepository.findAll();
		Assert.notNull(notes);

		Assert.isTrue(principal instanceof Customer||
				principal instanceof Referee||
				principal instanceof HandyWorker);

		Assert.isTrue(report.getIsFinal());

		this.noteRepository.delete(note);
		updated = new ArrayList<Note>(notes);
		updated.remove(note);
		Assert.isTrue(!updated.contains(note));
		report.setNotes(updated);
	}

	public Note findOne(final int id){
		Note result;

		result = this.noteRepository.findOne(id);
		Assert.notNull(result);

		return result;

	}

	public Collection<Note> findAll(){
		Collection<Note>result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;


	}
	// Other business methods -----------------------------
}
