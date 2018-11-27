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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOne() {
		ProfessionalRecord res;
		res = this.professionalRecordService.findOne(2329);
		Assert.notNull(res);
		Assert.isTrue(res.getId() == 2329);
	}

	@Test
	public void testFindAll() {
		Collection<ProfessionalRecord> res;
		res = this.professionalRecordService.findAll();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
	}

	// Correct startDate
	@Test
	public void testCreateAndSave0() {
		ProfessionalRecord res;
		ProfessionalRecord saved;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.professionalRecordService.create();
		res.setCompanyName("Company");
		res.setStartDate(new Date(System.currentTimeMillis() - 1));
		res.setRole("Role");

		saved = this.professionalRecordService.save(res);

		Assert.notNull(saved);
		Assert.isTrue(principal.getCurriculum().getProfessionalRecords()
				.contains(saved));
	}

	// With endDate
	@Test
	public void testCreateAndSave1() {
		ProfessionalRecord res;
		ProfessionalRecord saved;

		HandyWorker principal;
		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.professionalRecordService.create();
		res.setCompanyName("Company");
		res.setStartDate(new Date(System.currentTimeMillis() - 1));
		res.setEndDate(new Date(System.currentTimeMillis() + 1));
		res.setRole("Role");

		saved = this.professionalRecordService.save(res);

		Assert.notNull(saved);
		Assert.isTrue(principal.getCurriculum().getProfessionalRecords()
				.contains(saved));
	}

	// Wrong startDate
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		ProfessionalRecord res;
		ProfessionalRecord saved;

		HandyWorker principal;
		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.professionalRecordService.create();
		res.setCompanyName("Company");
		res.setStartDate(new Date(System.currentTimeMillis() + 116551651000L));
		res.setRole("Role");

		saved = this.professionalRecordService.save(res);

		Assert.notNull(saved);
		Assert.isTrue(principal.getCurriculum().getProfessionalRecords()
				.contains(saved));
	}

	@Test
	public void testDelete() {
		ProfessionalRecord toDelete = new ProfessionalRecord();
		Collection<ProfessionalRecord> professionalRecords;

		HandyWorker principal;
		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();

		professionalRecords = principal.getCurriculum()
				.getProfessionalRecords();
		Assert.notNull(professionalRecords);

		toDelete = professionalRecords.iterator().next();
		this.professionalRecordService.delete(toDelete);

		professionalRecords = principal.getCurriculum()
				.getProfessionalRecords();
		Assert.isTrue(professionalRecords.size() == 1);

		super.unauthenticate();
	}
}
