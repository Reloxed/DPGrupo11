package services;

import java.util.Calendar;
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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class EducationRecordServiceTest extends AbstractTest{

	// System under test ------------------------------------------------------

	@Autowired
	private EducationRecordService educationRecordService;	
	
	// Tests ------------------------------------------------------------------
	
	@Test
	public void testCreateEducationRecord() {
		EducationRecord educationRecord;
		String username = "handyWorker1";
		super.authenticate(username);
		educationRecord = educationRecordService.create();
		Assert.notNull(educationRecord);
		super.unauthenticate();	
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNotCreateEducationRecord() {
		EducationRecord educationRecord;
		// Wrong actor
		String username = "sponsor1";
		super.authenticate(username);
		educationRecord = educationRecordService.create();
		Assert.notNull(educationRecord);
		super.unauthenticate();	
	}
	
	@Test
	public void testFindAllEducationRecords() {
		Collection<EducationRecord> educationRecords;
		String username = "handyWorker1";
		super.authenticate(username);
		educationRecords = educationRecordService.findAll();
		Assert.notNull(educationRecords);
		Assert.notEmpty(educationRecords);
		super.unauthenticate();	
	}
	
	@Test
	public void testFindOneEducationRecord() {
		EducationRecord educationRecord, saved;
		Calendar startDate;
		String username = "handyWorker1";
		super.authenticate(username);
		
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		saved = educationRecordService.save(educationRecord);
		educationRecord = educationRecordService.findOne(saved.getId());
		Assert.notNull(educationRecord);
		
		super.unauthenticate();		
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNotFindOneEducationRecords() {
		EducationRecord educationRecord;
		String username = "handyWorker1";
		super.authenticate(username);
		
		// Wrong educationRecord.id
		educationRecord = educationRecordService.findOne(32);
		Assert.notNull(educationRecord);
		super.unauthenticate();	
	}
	
	@Test
	public void testSaveEducationRecord_1() {
		EducationRecord educationRecord, saved;
		Collection<EducationRecord> educationRecords;
		Calendar startDate;
		String username = "handyWorker1";
		super.authenticate(username);
		
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		saved = educationRecordService.save(educationRecord);
		educationRecords = educationRecordService.findAll();
		Assert.isTrue(educationRecords.contains(saved));
		
		super.unauthenticate();		
	}
	
	@Test
	public void testSaveEducationRecord_2() {
		EducationRecord educationRecord, saved;
		Collection<EducationRecord> educationRecords;
		Calendar startDate;
		String username = "handyWorker1";
		super.authenticate(username);
		
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		educationRecord.setLinkAttachment("http://www.urlvalida.com");
		educationRecord.setComments("Que bien funciona todo.//Los tests siempre salen en verde.//");
		saved = educationRecordService.save(educationRecord);
		educationRecords = educationRecordService.findAll();
		Assert.isTrue(educationRecords.contains(saved));
		
		super.unauthenticate();		
	}
	
	@Test(expected=ConstraintViolationException.class) 
	public void testNotSaveEducationRecord_1() {
		EducationRecord educationRecord, saved;
		Calendar startDate;
		String username = "handyWorker1";
		
		super.authenticate(username);
		
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		// Wrong date
		startDate.set(2020, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		saved = educationRecordService.save(educationRecord);
		Assert.notNull(saved);
		
		super.unauthenticate();		
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNotSaveEducationRecord_2() {
		EducationRecord educationRecord, saved;
		Calendar startDate;
		Calendar endDate;
		String username = "handyWorker1";
		
		super.authenticate(username);
		
		// End Date before Start Date
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		endDate = Calendar.getInstance();
		endDate.set(2013, 8, 22);
		educationRecord.setEndDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		saved = educationRecordService.save(educationRecord);
		Assert.notNull(saved);
		
		super.unauthenticate();		
	}
	
	@Test(expected=ConstraintViolationException.class) 
	public void testNotSaveEducationRecord_3() {
		EducationRecord educationRecord, saved;
		Calendar startDate;
		String username = "handyWorker1";
		
		super.authenticate(username);
		
		// Not a link format in linkAttachment
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		startDate = Calendar.getInstance();
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		educationRecord.setLinkAttachment("Esto debe fallar al no ser un link");
		saved = educationRecordService.save(educationRecord);
		Assert.notNull(saved);
		
		super.unauthenticate();		
	}


}


