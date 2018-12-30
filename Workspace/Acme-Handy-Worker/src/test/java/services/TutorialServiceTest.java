package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class TutorialServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private TutorialService tutorialService;

	// Supporting services --------------------------------------------

	@Autowired
	private SectionService sectionService;

	// Tests ----------------------------------------------------------

	// Create correcto
	@Test
	public void testCreate1() {
		Tutorial res;
		super.authenticate("handyWorker1");
		res = this.tutorialService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// Create con actor no HandyWorker
	@Test(expected = IllegalArgumentException.class)
	public void testCreate2() {
		Tutorial res;
		super.authenticate("admin1");
		res = this.tutorialService.create();
		Assert.notNull(res);
		super.unauthenticate();
	}

	// FindAll
	@Test
	public void testFindAll() {
		Collection<Tutorial> res;
		res = this.tutorialService.findAll();
		Assert.notEmpty(res);
	}

	// Save con FindOne correctos
	@Test
	public void testSaveAndFindOne() {
		Tutorial res;
		Section section;
		super.authenticate("handyWorker1");
		section = this.sectionService.create();
		section.setTitle("Un pais multicolor");
		section.setText("Nacio una abeja bajo el sol");
		Collection<Section> sections = new ArrayList<>();
		section = this.sectionService.save(section);
		sections.add(section);
		res = this.tutorialService.create();
		res.setSections(sections);
		res.setTitle("Y fue Steve Jobs el que");
		Calendar date = Calendar.getInstance();
		date.set(2018, 07, 18);
		res.setLastUpdate(date.getTime());
		res.setSummary("invento Apple con el corrector de Windows");
		res = this.tutorialService.save(res);
		Assert.notNull(res);
		Tutorial found;
		found = this.tutorialService.findOne(res.getId());
		Assert.notNull(found);
		super.unauthenticate();
	}
	
	// Save con actor incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testSave2() {
		Tutorial res;
		Section section;
		super.authenticate("customer1");
		section = this.sectionService.create();
		section.setTitle("Un pais multicolor");
		section.setText("Nacio una abeja bajo el sol");
		Collection<Section> sections = new ArrayList<>();
		section = this.sectionService.save(section);
		sections.add(section);
		res = this.tutorialService.create();
		res.setSections(sections);
		res.setTitle("Y fue Steve Jobs el que");
		Calendar date = Calendar.getInstance();
		date.set(2018, 07, 18);
		res.setLastUpdate(date.getTime());
		res.setSummary("invento Apple con el corrector de Windows");
		res = this.tutorialService.save(res);
		Assert.notNull(res);
		Tutorial found;
		found = this.tutorialService.findOne(res.getId());
		Assert.notNull(found);
		super.unauthenticate();
	}
	
	// Delete funcionando
	@Test(expected = IllegalArgumentException.class)
	public void testDelete1(){
		super.authenticate("handyWorker2");
		Tutorial res = this.tutorialService.findOne(this.tutorialService.findAll().iterator().next().getId());
		this.tutorialService.delete(res);
		Assert.isTrue(!this.tutorialService.findAll().contains(res));
		super.unauthenticate();
	}

}
