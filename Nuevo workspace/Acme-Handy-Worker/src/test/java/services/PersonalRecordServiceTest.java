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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOne() {
		Collection<PersonalRecord> collPR;
		PersonalRecord personalR;
		collPR = this.personalRecordService.findAll();
		personalR = this.personalRecordService.findOne(collPR.iterator().next().getId());
		Assert.notNull(personalR);
	}
	
	@Test
	public void testFindAll() {
		Collection<PersonalRecord> res;
		res = this.personalRecordService.findAll();
		Assert.notEmpty(res);
		Assert.isTrue(res.size() == 2);
	}

	@Test
	public void testCreateAndSave() {
		PersonalRecord res;
		PersonalRecord saved;
		HandyWorker principal;

		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.personalRecordService.create();
		res.setFullName("fullName");
		res.setEmail("example@example.com");
		res.setPhoneNumber("+034695784231");
		res.setLinkedinLink("https://www.linkedin.com/78sdio87");

		saved = this.personalRecordService.save(res);
		Assert.notNull(saved);

		res = this.personalRecordService.findOne(saved.getId());
		Assert.notNull(res);
	}
	
	@Test
	public void testDelete() {
		PersonalRecord toDelete = new PersonalRecord();

		HandyWorker principal;
		super.authenticate("handyWorker2");
		principal = this.handyWorkerService.findByPrincipal();

		toDelete = principal.getCurriculum().getPersonalRecord();
		Assert.notNull(toDelete);

		this.personalRecordService.delete(toDelete);

		Assert.isNull(principal.getCurriculum().getPersonalRecord());
		super.unauthenticate();
	}
}
