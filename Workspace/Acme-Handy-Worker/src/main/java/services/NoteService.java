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
	private CustomerService customerService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private ActorService actorService;
	// CRUD Methods --------------------------------

	//check in referee that the report is saves in final mode to create the note.
	public Note create(){
		Actor principal;
		Note result;
		Report report;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Note();
		report = new Report();
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
		result.setReport(report);
		return result;
	}		

	/*customer = this.customerService.findByPrincipal();

		referee = this.refereeService.findByPrincipal();

		handyWorker = this.handyWorkerService.findByPrincipal();

		result = new Note();

		if(principal.equals(customer) || 
				principal.equals(referee) || 
				principal.equals(handyWorker)){

			Assert.isTrue(principal.equals(customer) ||
					principal.equals(referee) || 
					principal.equals(handyWorker));


			report = result.getReport();

			Assert.isTrue(!report.getIsFinal());

			result.setPublishedMoment(new Date(System.currentTimeMillis()-1));


		}
		return result;
	}
	/*if(principal.equals(customer)){
			Assert.notNull(customer);
			Assert.isTrue(principal.equals(customer));

			result = new Note();
			report = result.getReport();

			Assert.isTrue(!report.getIsFinal());

			result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
			result.setCustomerComment(" ");
		}else if(principal.equals(referee)){
			Assert.notNull(referee);
			Assert.isTrue(principal.equals(referee));

			result = new Note();
			report = result.getReport();

			Assert.isTrue(!report.getIsFinal());

			result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
			result.setRefereeComment(" ");

		}else{
			Assert.notNull(handyWorker);
			Assert.isTrue(principal.equals(handyWorker));

			result = new Note();
			report = result.getReport();

			Assert.isTrue(!report.getIsFinal());

			result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
			result.setHandyWorkerComment(" ");
		}

		Assert.notNull(result);
		return result;
	 */


	public Note save(Note note){
		Note result;
		Report report;
		Actor principal;
		Collection<Note>notes;
		
		Assert.notNull(note);
		Assert.isTrue(note.getId()==0);//notes cannot be updated or deleted once they are saved to the database
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		notes = new ArrayList<Note>();

		report  = note.getReport();
		Assert.notNull(report);
		
		notes = report.getNotes();
		//Assert.notNull(notes);
		
		result = this.create();
		
		if(principal instanceof Referee){
			Assert.isTrue(principal instanceof Referee);
			result.setRefereeComment(note.getRefereeComment());

		}else if(principal instanceof Customer){
			Assert.isTrue(principal instanceof Customer);
			result.setCustomerComment(note.getCustomerComment());

		}else{
			Assert.isTrue(principal instanceof HandyWorker);
			result.setHandyWorkerComment(note.getHandyWorkerComment());

		}
		result.setReport(report);
		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		Assert.isTrue(!report.getIsFinal());

		this.noteRepository.save(result);
		
		Assert.isTrue(notes.contains(result));
		report.setNotes(notes);

		return result;


	}
	/*customer = this.customerService.findByPrincipal();

		referee = this.refereeService.findByPrincipal();

		handyWorker = this.handyWorkerService.findByPrincipal();

		report = note.getReport();
		Assert.notNull(report);

		if(principal.equals(customer) ||
				principal.equals(referee)||
				principal.equals(handyWorker)){

			publishedMoment= new Date(System.currentTimeMillis()-1);
			Assert.isTrue(publishedMoment.after(report.getPublishedMoment()));

			customerComment = note.getCustomerComment();
			refereeComment = note.getRefereeComment();
			handyWorkerComment = note.getHandyWorkerComment();

			note.setPublishedMoment(publishedMoment);
			note.setCustomerComment(customerComment);
			note.setRefereeComment(refereeComment);
			note.setHandyWorkerComment(handyWorkerComment);



		}

		result = this.noteRepository.save(note);
		Assert.notNull(result);

		notes = new ArrayList<Note>();
		notes = report.getNotes();
		if(!notes.contains(result)){
			notes.add(result);
			report.setNotes(notes);
		}


		return result;
	}

	/*if(principal.equals(customer)){
			Assert.notNull(customer);

			customerComment = note.getCustomerComment();
			Assert.notNull(customerComment);

			publishedMoment = new Date(System.currentTimeMillis()-1);
			Assert.isTrue(publishedMoment.after(report.getPublishedMoment()));
			note.setPublishedMoment(publishedMoment);
			note.setCustomerComment(customerComment);

			Assert.isTrue(!report.getIsFinal());

			result = this.noteRepository.save(note);
			Assert.notNull(result);

		}else if(principal.equals(referee)){
			Assert.notNull(referee);

			refereeComment = note.getRefereeComment();
			Assert.notNull(refereeComment);

			publishedMoment = new Date(System.currentTimeMillis()-1);
			Assert.isTrue(publishedMoment.after(report.getPublishedMoment()));
			note.setPublishedMoment(publishedMoment);
			note.setRefereeComment(refereeComment);

			Assert.isTrue(!report.getIsFinal());

			result = this.noteRepository.save(note);
			Assert.notNull(result);

		}else{

			Assert.notNull(handyWorker);

			handyWorkerComment = note.getHandyWorkerComment();
			Assert.notNull(handyWorkerComment);

			publishedMoment = new Date(System.currentTimeMillis()-1);
			Assert.isTrue(publishedMoment.after(report.getPublishedMoment()));
			note.setPublishedMoment(publishedMoment);
			note.setHandyWorkerComment(handyWorkerComment);

			Assert.isTrue(!report.getIsFinal());

			result = this.noteRepository.save(note);
			Assert.notNull(result);

		}

		notes = 



				return result;
	 */



	public void delete(Note note){
		Actor principal;
		Report report;
		Collection<Note>notes;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		report = note.getReport();
		Assert.notNull(report);
		
		notes = report.getNotes();
		Assert.notNull(notes);
		
		Assert.isTrue(principal instanceof Customer||
				principal instanceof Referee||
				principal instanceof HandyWorker);
		
		Assert.isTrue(!report.getIsFinal());
		
		this.noteRepository.delete(note);
		Assert.isTrue(!notes.contains(note));
		report.setNotes(notes);
	}

		/*customer = this.customerService.findByPrincipal();

		referee = this.refereeService.findByPrincipal();

		handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.notNull(note);
		Assert.isTrue(note.getId() == 0);

		report = note.getReport();
		if(principal.equals(customer)||
				principal.equals(referee)||
				principal.equals(handyWorker)){

			notes = report.getNotes();

			if(notes.contains(note)){
				notes.remove(note);
				report.setNotes(notes);
			}

		}*/
	

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
