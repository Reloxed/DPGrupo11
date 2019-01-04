/*
 * RegistrationTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EducationRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private RangerService			rangerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindByPrincipal() {
		Collection<EducationRecord> result;
		super.authenticate("ranger1");
		result = this.educationRecordService.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		EducationRecord result;
		result = this.educationRecordService.findOne(1171);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1171);

	}

	@Test
	public void testFindAll() {
		Collection<EducationRecord> result;
		result = this.educationRecordService.findAll();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 3);
	}

	@Test
	public void testCreateAndSave() {
		EducationRecord result;
		EducationRecord saved;
		final Ranger principal;
		super.authenticate("ranger1");
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.educationRecordService.create();
		result.setDiplomaTitle("a");
		result.setEndDate(new Date(1457568000000L));
		result.setInstitutionName("a");
		result.setStartDate(new Date(1425945600000L));
		saved = this.educationRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCV().getEducationRecord().contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		final EducationRecord toDelete;
		Collection<EducationRecord> listEducationRecord;
		super.authenticate("ranger1");
		listEducationRecord = this.educationRecordService.findByPrincipal();
		Assert.notNull(listEducationRecord);
		toDelete = listEducationRecord.iterator().next();
		this.educationRecordService.delete(toDelete);
		listEducationRecord = this.educationRecordService.findByPrincipal();
		Assert.isTrue(listEducationRecord.size() == 1);
		super.unauthenticate();

	}
}
