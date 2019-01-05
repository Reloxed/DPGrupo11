
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HandyWorker;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOne() {
		Collection<Phase> collP;
		Phase phase;
		
		collP = this.phaseService.findAll();
		phase = this.phaseService.findOne(collP.iterator().next().getId());
		Assert.notNull(phase);
	}
	
	@Test
	public void testFindAll() {
		Collection<Phase> res;
		res = this.phaseService.findAll();
		Assert.notNull(res);
	}

	@Test
	public void testCreateAndSave() {
		Phase res;
		Phase saved;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.phaseService.create();
		res.setStartMoment(new Date(9994865400000L));
		res.setEndMoment(new Date(9994865498468L));
		res.setTitle("Title Nigeria");
		res.setDescription("Description");
		res.setFixUpTask(this.fixUpTaskService.findAll().iterator().next());

		saved = this.phaseService.save(res);
		Assert.notNull(saved);

		res = this.phaseService.findOne(saved.getId());
		Assert.notNull(res);
	}

	@Test
	public void testFindCreator() {
		HandyWorker res;
		Phase phase;

		phase = this.phaseService.findOne(this.phaseService.findAll().iterator().next().getId());
		res = this.phaseService.creator(phase.getId());
		Assert.notNull(res);
	}

	public void testDelete() {
		Phase res, saved, toDelete;
		HandyWorker principal;
		Collection<Phase> phases;

		// Create phase

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.phaseService.create();
		res.setStartMoment(new Date(System.currentTimeMillis() - 1));
		res.setEndMoment(new Date(1564865498468L));
		res.setTitle("Title Nigeria");
		res.setDescription("Description");
		res.setFixUpTask(this.fixUpTaskService.findAll().iterator().next());

		saved = this.phaseService.save(res);
		Assert.notNull(saved);

		res = this.phaseService.findOne(saved.getId());
		Assert.notNull(res);

		// Delete phase

		phases = this.phaseService.findAll();
		Assert.notEmpty(phases);
		toDelete = this.phaseService.findAll().iterator().next();
		this.phaseService.delete(toDelete);
	}
}
