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
	private CustomerService customerService;

	@Autowired
	private RefereeService refereeService;

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

	
	public void testSave(){
		super.authenticate("HandyWorker1");
		Actor principal;
		Note result,saved;
		Collection<Note>notes;
		Report report;

		notes = new ArrayList<Note>();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		
		notes = this.noteService.findAll();
		Assert.notNull(notes);
		
		result = this.noteService.create();
		Assert.notNull(result);
		
		saved = this.noteService.save(result);
		Assert.notNull(saved);
		
		Assert.isTrue(notes.contains(saved));
		

		super.unauthenticate();
	}
	
	
	public void tetsDelete(){
		super.authenticate("handyWorker2");
		Actor principal;
		Note toDelete;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		toDelete = this.noteService.create();
		Assert.notNull(toDelete);
		
		this.noteService.delete(toDelete);
		
	}
	
	@Test
	public void testFindOne(){
		Note result;
		result = this.noteService.findOne(2470);
		Assert.notNull(result);
		
		Assert.isTrue(result.getId() == 2470);
	}
	@Test
	public void testFindAll(){
		Collection<Note>result;
		result =this.noteService.findAll();
		Assert.notNull(result);
		
		Assert.isTrue(result.size()==1);
		
	}
}
