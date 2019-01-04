
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	@Autowired
	private NoteService		noteService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ManagerService	managerService;


	//Tests

	@Test
	public void testListingNotesOfManager() {
		Collection<Note> result;
		super.authenticate("manager1");
		result = this.noteService.findAllNotesOfPrincipalManager();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
		super.unauthenticate();
	}

	@Test
	public void testSave1() {
		super.authenticate("auditor1");
		Note note, saved = null;
		Trip trip;
		Collection<Note> notes;

		trip = this.tripService.findOne(1204); // Trip 6 hasn't been publicated yet. Therefore, the note cannot be created

		note = this.noteService.create();

		note.setRemark("sex");

		note.setTrip(trip);

		try {
			saved = this.noteService.save(note);
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");

		notes = this.noteService.findAll();
		Assert.isTrue(!notes.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSave2() {
		super.authenticate("auditor1");
		Note note, saved = null;
		Trip trip;
		Collection<Note> notes;

		trip = this.tripService.findOne(1202); // Trip 4  

		note = this.noteService.create();

		note.setRemark("sex"); // Contains a spam word
		note.setTrip(trip);

		Assert.isTrue(!this.auditorService.findByPrincipal().getIsSuspicious()); // Auditor 1 is not suspicious

		Assert.isTrue(this.tripService.findOne(1202).getNotes().size() == 4); // Trip 4 has 4 notes
		Assert.isTrue(this.auditorService.findByPrincipal().getNotes().size() == 3); // Auditor 1 has written 3 notes.

		try {
			saved = this.noteService.save(note);
		} catch (final RuntimeException e) {
		}

		super.authenticate(null);
		super.authenticate("administrator1");

		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(saved)); // The note has been created successfully

		super.authenticate(null);
		super.authenticate("auditor1");

		Assert.isTrue(this.tripService.findOne(1202).getNotes().size() == 5); // Trip 4 now has 5 notes
		Assert.isTrue(this.auditorService.findByPrincipal().getNotes().size() == 4); // Auditor 1 now has 4 notes.

		Assert.isTrue(this.auditorService.findByPrincipal().getIsSuspicious()); // Now auditor1 is suspicious
		super.authenticate(null);

	}

	@Test
	public void testPostAManagerReply() {
		Note noteToModify;
		super.authenticate("manager2");
		noteToModify = this.noteService.findAllNotesOfPrincipalManager().get(1);  // note 4
		Assert.notNull(noteToModify);
		Assert.isNull(noteToModify.getManagerReply());
		Assert.isNull(noteToModify.getMomentReply());
		noteToModify.setManagerReply("sex");

		Assert.isTrue(!this.managerService.findOne(1140).getIsSuspicious()); //Manager 2 is not suspicious

		this.noteService.postManagerReply(noteToModify);
		noteToModify = this.noteService.findAllNotesOfPrincipalManager().get(0);
		Assert.notNull(noteToModify);
		Assert.notNull(noteToModify.getManagerReply());
		Assert.notNull(noteToModify.getMomentReply());
		Assert.isTrue(this.managerService.findOne(1140).getIsSuspicious()); //Manager 2 is now suspicious
		super.unauthenticate();

	}

	@Test
	public void testFindByAuditor() {
		Collection<Note> result;
		super.authenticate("auditor1");
		result = this.noteService.findByAuditor();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 3);
		super.unauthenticate();
	}

}
