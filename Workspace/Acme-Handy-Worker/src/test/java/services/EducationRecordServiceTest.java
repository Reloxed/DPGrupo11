package services;

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
	public void testSaveEducationRecords() {
		EducationRecord educationRecord, saved;
		Collection<EducationRecord> educationRecords;
		String username = "handyWorker1";
		super.authenticate(username);
		
		educationRecord = educationRecordService.create();
		educationRecord.setDiplomaTitle("Grado en Ingeniería Informática - Ingeniería del Software");
		Calendar startDate = null;
		startDate.set(2015, 8, 22);
		educationRecord.setStartDate(startDate.getTime());
		educationRecord.setInstitutionName("Universidad de Sevilla");
		saved = educationRecordService.save(educationRecord);
		educationRecords = educationRecordService.findAll();
		Assert.isTrue(educationRecords.contains(saved));
		
		super.unauthenticate();		
	}


}


