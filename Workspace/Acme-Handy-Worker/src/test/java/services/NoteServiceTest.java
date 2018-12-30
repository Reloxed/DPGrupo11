package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@Transactional

public class NoteServiceTest extends AbstractTest{

	//System under test -----------------------------------------------

	@Autowired
	private NoteService noteService;

	

	@Autowired
	private ActorService actorService;

	@Autowired
	private ReportService reportService;

	//Tests ------------------------------------------------------------

	@Test
	public void testCreate(){
		super.authenticate("HandyWorker1");
		Actor principal;


		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.noteService.create();

		super.unauthenticate();

	}
	
	@Test
	public void testSave(){
		super.authenticate("handyWorker1");
		Actor principal;
		Note result,saved;
		Collection<Note>notes;
		Collection<Report> collR;
		Report report;

		notes = new ArrayList<Note>();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = this.noteService.create();
		Assert.notNull(result);
		
		collR = this.reportService.findAll();
		Report repToFinal = collR.iterator().next();
		repToFinal.setIsFinal(true);
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		
		notes = report.getNotes();
		Assert.notNull(notes);
		
		result.setHandyWorkerComment("sex");
		result.setCustomerComment(" ");
		result.setRefereeComment(" ");
		result.setReport(report);
		saved = this.noteService.save(result);
		
		this.noteService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(principal.getIsSuspicious());
		Assert.isTrue(saved.getReport().getNotes().contains(saved));
		super.unauthenticate();
	}
	
	@Test
	public void tetsDelete(){
		super.authenticate("Customer1");
		Actor principal;
		Note toDelete,note;
		Report report;
		Collection<Note>notes,removed;
		Collection<Report> collR;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		note = this.noteService.create();
		Assert.notNull(note);
		
		note.setCustomerComment(" ");
		note.setHandyWorkerComment(" ");
		note.setRefereeComment(" ");
		collR = this.reportService.findAll();
		
		Report repToFinal = collR.iterator().next();
		repToFinal.setIsFinal(true);
		
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		
		note.setReport(report);
		
		toDelete = this.noteService.save(note);
		this.noteService.findOne(toDelete.getId());
		Assert.notNull(toDelete);
		
		notes = new ArrayList<Note>();
		notes = report.getNotes();
		Assert.notNull(notes);
		
		this.noteService.delete(toDelete);
		
		removed = report.getNotes();
		Assert.notNull(removed);
		Assert.isTrue(notes.contains(toDelete));
		Assert.isTrue(!removed.contains(toDelete));

	}
	
	@Test
	public void testFindOne(){
		Note result;
		Collection<Note> collN;
		collN = this.noteService.findAll();
		int noteId = collN.iterator().next().getId();
		result = this.noteService.findOne(noteId);
		Assert.notNull(result);
		
		Assert.isTrue(result.getId() == noteId);
	}
	@Test
	public void testFindAll(){
		Collection<Note>result;
		result = this.noteService.findAll();
		Assert.notNull(result);
		
		
	}
}
