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
import domain.ProfessionalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private RangerService				rangerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindByPrincipal() {
		Collection<ProfessionalRecord> result;
		super.authenticate("ranger1");
		result = this.professionalRecordService.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		ProfessionalRecord result;
		result = this.professionalRecordService.findOne(1168);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1168);

	}

	@Test
	public void testFindAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordService.findAll();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 3);
	}

	@Test
	public void testCreateAndSave() {
		ProfessionalRecord result;
		ProfessionalRecord saved;
		final Ranger principal;
		super.authenticate("ranger1");
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.professionalRecordService.create();
		result.setCompanyName("a");
		result.setEndDate(new Date(1457568000000L));
		result.setRole("a");
		result.setStartDate(new Date(1425945600000L));
		saved = this.professionalRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCV().getProfessionalRecords().contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		final ProfessionalRecord toDelete;
		Collection<ProfessionalRecord> listProfessionalRecord;
		super.authenticate("ranger1");
		listProfessionalRecord = this.professionalRecordService.findByPrincipal();
		Assert.notNull(listProfessionalRecord);
		toDelete = listProfessionalRecord.iterator().next();
		this.professionalRecordService.delete(toDelete);
		listProfessionalRecord = this.professionalRecordService.findByPrincipal();
		Assert.isTrue(listProfessionalRecord.size() == 1);
		super.unauthenticate();

	}
}
