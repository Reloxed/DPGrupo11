package services;

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
	
	@Test (expected = IllegalArgumentException.class)
	public void testNotCreate(){
		// Wrong user
		super.authenticate("admin1");
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
		Collection<Report> collR;
		Report report;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		collR = this.reportService.findAll();
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		report.setIsFinal(true);
		
		result = this.noteService.create();		
		result.setHandyWorkerComment("hola");
		result.setReport(report);
		saved = this.noteService.save(result);
		
		this.noteService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(saved.getReport().getNotes().contains(saved));
		super.unauthenticate();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNotSave(){
		// The complaint to which the report is attached is not related to the present HandyWorker 
		super.authenticate("handyWorker2");
		Actor principal;
		Note result,saved;
		Collection<Report> collR;
		Report report;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		collR = this.reportService.findAll();
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		report.setIsFinal(true);
		
		result = this.noteService.create();		
		result.setHandyWorkerComment("hola");
		result.setReport(report);
		saved = this.noteService.save(result);
		
		this.noteService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(saved.getReport().getNotes().contains(saved));
		super.unauthenticate();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNotSave2(){
		// A handyworker is changing a comment of a customer
		super.authenticate("handyWorker2");
		Actor principal;
		Note result,saved;
		Collection<Report> collR;
		Report report;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		collR = this.reportService.findAll();
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		report.setIsFinal(true);
		
		result = this.noteService.create();		
		result.setHandyWorkerComment("hola");
		result.setCustomerComment("Error 404");
		result.setReport(report);
		saved = this.noteService.save(result);
		
		this.noteService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(saved.getReport().getNotes().contains(saved));
		super.unauthenticate();
	}
	
	@Test
	public void testSaveSpam(){
		super.authenticate("handyWorker1");
		Actor principal;
		Note result,saved;
		Collection<Report> collR;
		Report report;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = this.noteService.create();
		Assert.notNull(result);
		
		collR = this.reportService.findAll();
		report = this.reportService.findOne(collR.iterator().next().getId());
		Assert.notNull(report);
		report.setIsFinal(true);
		
		result.setHandyWorkerComment("sex");
		result.setReport(report);
		saved = this.noteService.save(result);
		
		this.noteService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(principal.getIsSuspicious());
		Assert.isTrue(saved.getReport().getNotes().contains(saved));
		super.unauthenticate();
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
