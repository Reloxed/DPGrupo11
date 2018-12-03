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
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CurriculumServiceTest extends AbstractTest{

	// System under test ------------------------------------------------------

	@Autowired
	private CurriculumService curriculumService;	
	
	// Supporting Services ----------------------------------------------------
	
	@Autowired
	private PersonalRecordService personalRecordService;	
	
	@Autowired
	private HandyWorkerService handyWorkerService;	
	
	// Tests ------------------------------------------------------------------
	
	@Test
	public void testCreateCurriculum() {
		Curriculum curriculum;

		String username = "handyWorker1";
		super.authenticate(username);
		curriculum = curriculumService.create();
		Assert.notNull(curriculum);
		super.unauthenticate();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNotCreateCurriculum() {
		Curriculum curriculum;

		String username = "sponsor1";
		super.authenticate(username);
		curriculum = curriculumService.create();
		Assert.notNull(curriculum);
		super.unauthenticate();
	}
	
	@Test
	public void testFindOneCurriculum() {
		Curriculum curriculum;
		Collection<Curriculum> collC;
		collC = this.curriculumService.findAll();
		curriculum = this.curriculumService.findOne(collC.iterator().next().getId());
		Assert.notNull(curriculum);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNotFindOneCurriculum() {
		Curriculum curriculum;
		curriculum = this.curriculumService.findOne(-2);
		Assert.notNull(curriculum);
	}
	
	@Test
	public void testFindAllCurriculum() {
		Collection<Curriculum> curriculums;
		curriculums = curriculumService.findAll();
		Assert.notNull(curriculums);
		Assert.notEmpty(curriculums);
	}
	
	@Test
	public void testCreateSaveAndFindOneCurriculum() {
		Curriculum curriculum, savedC, foundC;
		PersonalRecord personalRecord, savedPR, foundPR;
		String username = "handyWorker1";
		super.authenticate(username);
		
		curriculum = this.curriculumService.create(); 		
		Assert.notNull(curriculum);
		personalRecord = curriculum.getPersonalRecord();
		personalRecord.setFullName("Me llamo Ralph");
		personalRecord.setEmail("ralphnoestonto@yahoo.es");
		personalRecord.setLinkedinLink("https://www.linkedin.com/in/ralph");
		personalRecord.setPhoneNumber("697561248");
		savedPR = this.personalRecordService.save(personalRecord);
		Assert.notNull(savedPR);
		curriculum.setPersonalRecord(savedPR);
		savedC = this.curriculumService.save(curriculum);
		Assert.notNull(savedC);
		super.unauthenticate();
		
		foundC = this.curriculumService.findOne(savedC.getId());
		Assert.notNull(foundC);
		
		foundPR = this.personalRecordService.findOne(savedPR.getId());
		Assert.notNull(foundPR);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndNotSaveCurriculum() {
		Curriculum curriculum, savedC, savedC2;
		PersonalRecord personalRecord, savedPR;
		String username = "handyWorker1";
		super.authenticate(username);
		
		curriculum = this.curriculumService.create(); 		
		Assert.notNull(curriculum);
		personalRecord = curriculum.getPersonalRecord();
		personalRecord.setFullName("Me llamo Ralph");
		personalRecord.setEmail("ralphnoestonto@yahoo.es");
		personalRecord.setLinkedinLink("https://es.linkedin.com/in/ralph");
		personalRecord.setPhoneNumber("697561248");
		savedPR = this.personalRecordService.save(personalRecord);
		Assert.notNull(savedPR);
		curriculum.setPersonalRecord(savedPR);
		savedC = this.curriculumService.save(curriculum);
		Assert.notNull(savedC);
		savedC.setTicker("181129-d5P67q");
		savedC2 = this.curriculumService.save(savedC);
		Assert.notNull(savedC2);
		super.unauthenticate();
		
	}
	
	@Test
	public void testDeleteCurriculum() {
		HandyWorker user;
		Curriculum curriculum, deletedC;
		String username = "handyWorker1";
		super.authenticate(username);
		
		user = this.handyWorkerService.findByPrincipal();
		curriculum = user.getCurriculum();
		this.curriculumService.delete(curriculum);
		
		deletedC = this.curriculumService.findOne(curriculum.getId());
		Assert.isNull(deletedC);

		super.unauthenticate();
	}
	
	@Test
	public void testDeleteCurriculum2() {
		HandyWorker user;
		Curriculum curriculum;
		PersonalRecord deletedPR;
		String username = "handyWorker1";
		super.authenticate(username);
		
		user = this.handyWorkerService.findByPrincipal();
		curriculum = user.getCurriculum();
		this.curriculumService.delete(curriculum);
		
		deletedPR = this.personalRecordService.findOne(curriculum.getPersonalRecord().getId());
		Assert.isNull(deletedPR);

		super.unauthenticate();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNotDeleteCurriculum() {
		Curriculum curriculum, savedC;
		PersonalRecord personalRecord, savedPR;
		String username1 = "handyWorker1";
		String username2 = "handyWorker2";
		super.authenticate(username1);
		
		curriculum = this.curriculumService.create(); 		
		Assert.notNull(curriculum);
		personalRecord = curriculum.getPersonalRecord();
		personalRecord.setFullName("Me llamo Ralph");
		personalRecord.setEmail("ralphnoestonto@yahoo.es");
		personalRecord.setLinkedinLink("https://es.linkedin.com/in/ralph");
		personalRecord.setPhoneNumber("697561248");
		savedPR = this.personalRecordService.save(personalRecord);
		Assert.notNull(savedPR);
		curriculum.setPersonalRecord(savedPR);
		savedC = this.curriculumService.save(curriculum);
		Assert.notNull(savedC);
		super.unauthenticate();
		
		super.authenticate(username2);
		this.curriculumService.delete(savedC);
		super.unauthenticate();

	}
	
}
