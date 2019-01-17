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
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SectionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SectionService sectionService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private TutorialService tutorialService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOne() {
		Section res;
		Collection<Section> collSec;

		collSec = this.sectionService.findAll();

		res = this.sectionService.findOne(collSec.iterator().next().getId());
		Assert.notNull(res);

	}

	@Test
	public void testFindAll() {
		Collection<Section> res;

		res = this.sectionService.findAll();
		Assert.notNull(res);
	}

	@Test
	public void testCreateAndSave0() {
		Section res;
		Section saved;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.create();
		res.setTitle("Titulo sexo");
		res.setText("Text");

		saved = this.sectionService.save(res);
		Assert.notNull(saved);

		res = this.sectionService.findOne(saved.getId());
		Assert.notNull(res);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateAndSave1() {
		Section res;
		Section saved;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.create();

		saved = this.sectionService.save(res);
		Assert.notNull(saved);
	}

	@Test
	public void testDelete() {
		Section toDelete = new Section();
		Collection<Section> sections;
		Tutorial tutorial;

		super.authenticate("handyWorker2");

		sections = this.sectionService.findSectionsByPrincipal();
		Assert.notEmpty(sections);

		toDelete = sections.iterator().next();
		tutorial = this.tutorialService.findTutorialBySectionId(toDelete
				.getId());

		this.sectionService.delete(toDelete);

		sections = tutorial.getSections();

		super.unauthenticate();
	}
}
