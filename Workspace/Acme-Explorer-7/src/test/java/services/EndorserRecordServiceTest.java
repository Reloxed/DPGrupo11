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
import domain.EndorserRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private RangerService			rangerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindByPrincipal() {
		Collection<EndorserRecord> result;
		super.authenticate("ranger2");
		result = this.endorserRecordService.findByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
		super.unauthenticate();
	}

	@Test
	public void testFindOne() {
		EndorserRecord result;
		result = this.endorserRecordService.findOne(1174);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1174);

	}

	@Test
	public void testFindAll() {
		Collection<EndorserRecord> result;
		result = this.endorserRecordService.findAll();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 2);
	}

	@Test
	public void testCreateAndSave() {
		EndorserRecord result;
		EndorserRecord saved;
		final Ranger principal;
		super.authenticate("ranger1");
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.endorserRecordService.create();
		result.setName("a");
		result.setPhone("696257894");
		result.setSurname("a");
		saved = this.endorserRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCV().getEndorserRecord().contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		final EndorserRecord toDelete;
		Collection<EndorserRecord> listEndorserRecord;
		super.authenticate("ranger2");
		listEndorserRecord = this.endorserRecordService.findByPrincipal();
		Assert.notNull(listEndorserRecord);
		toDelete = listEndorserRecord.iterator().next();
		this.endorserRecordService.delete(toDelete);
		listEndorserRecord = this.endorserRecordService.findByPrincipal();
		Assert.isTrue(listEndorserRecord.size() == 1);
		super.unauthenticate();

	}
}
