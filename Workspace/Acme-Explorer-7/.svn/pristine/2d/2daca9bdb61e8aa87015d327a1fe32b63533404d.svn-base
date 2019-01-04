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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private RangerService				rangerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindByPrincipal() {
		Collection<MiscellaneousRecord> result;
		super.authenticate("ranger1");
		result = this.miscellaneousRecordService.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 1);
		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordService.findOne(1176);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1176);

	}

	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordService.findAll();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
	}

	@Test
	public void testCreateAndSave() {
		MiscellaneousRecord result;
		MiscellaneousRecord saved;
		final Ranger principal;
		super.authenticate("ranger1");
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.miscellaneousRecordService.create();
		result.setTitle("A");
		saved = this.miscellaneousRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCV().getMiscellaneousRecord().contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		final MiscellaneousRecord toDelete;
		Collection<MiscellaneousRecord> listMiscellaneousRecord;
		super.authenticate("ranger1");
		listMiscellaneousRecord = this.miscellaneousRecordService.findByPrincipal();
		Assert.notNull(listMiscellaneousRecord);
		toDelete = listMiscellaneousRecord.iterator().next();
		this.miscellaneousRecordService.delete(toDelete);
		listMiscellaneousRecord = this.miscellaneousRecordService.findByPrincipal();
		Assert.isTrue(listMiscellaneousRecord.size() == 0);
		super.unauthenticate();

	}
}
