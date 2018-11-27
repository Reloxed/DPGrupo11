package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.HandyWorker;
import domain.Section;

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
	private CustomerService customerService;

	// Tests ------------------------------------------------------------------

	// HandyWorker
	@Test
	public void testFindOne0() {
		Section res;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.findOne(2337);
		Assert.notNull(res);
		Assert.isTrue(res.getId() == 2337);
	}

	@Test
	public void testFindAll0() {
		Collection<Section> res;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.findAll();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
	}

	// Customer
	@Test(expected = IllegalArgumentException.class)
	public void testFindOne1() {
		Section res;
		Customer principal;

		super.authenticate("customer2");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.findOne(2337);
		Assert.notNull(res);
		Assert.isTrue(res.getId() == 2337);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAll1() {
		Collection<Section> res;
		Customer principal;

		super.authenticate("customer2");
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionService.findAll();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
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
		res.setTitle("Titulo");
		res.setText("Text");

		saved = this.sectionService.save(res);
		Assert.notNull(saved);
	}

	@Test(expected = ConstraintViolationException.class)
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

		super.authenticate("handyWorker2");

		sections = this.sectionService.findSectionsByPrincipal();
		Assert.notNull(sections);

		toDelete = sections.iterator().next();
		this.sectionService.delete(toDelete);

		super.unauthenticate();
	}
}
