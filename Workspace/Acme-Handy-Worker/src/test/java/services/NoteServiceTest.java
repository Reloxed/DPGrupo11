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
	private CustomerService customerService;
	
	@Autowired
	private RefereeService refereeService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ReportService reportService;
	
	//Tests ------------------------------------------------------------
	
	@Test
	public void testSave1(){
		super.authenticate("referee2");
		Note note = null;
		Note saved = null;
		Report report;
		Collection<Note>notes;
		
		report = this.reportService.findOne(2436);
		
		note = this.noteService.create();
		
		note.setReport(report);
		
		saved = this.noteService.save(note);
		
		super.authenticate(null);
		super.authenticate("administrator2");
		
		notes = this.noteService.findAll();
		Assert.isTrue(!notes.contains(saved));
		
		super.authenticate(null);
		
		
	}
}
