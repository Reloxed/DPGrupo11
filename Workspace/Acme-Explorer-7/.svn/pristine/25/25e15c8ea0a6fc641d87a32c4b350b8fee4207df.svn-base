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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testDisplayCurriculumOfPrincipal() {
		Curriculum result;
		super.authenticate("ranger1");
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1355);
		super.unauthenticate();

	}

	@Test
	public void testDisplayCurriculumOfPrincipalWithoutCV() {
		Curriculum result;
		super.authenticate("ranger3");
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.isNull(result);
		super.unauthenticate();
	}

	@Test
	public void testDeleteCurriculumOfPrincipal() {
		Curriculum result;
		super.authenticate("ranger1");
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.notNull(result);
		this.curriculumService.delete(result);
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.isNull(result);
		super.unauthenticate();

	}
	@Test
	public void testCreateAndSaveCurriculum() {
		Curriculum created;
		Curriculum saved;

		super.authenticate("ranger3");
		created = this.curriculumService.findCurriculumByPrincipal();
		Assert.isNull(created);

		created = this.curriculumService.create();
		Assert.notNull(created);
		Assert.notNull(created.getTicker());
		Assert.notNull(created.getRanger());

		created.getPersonalRecord().setEmail("ranger3@gmail.com");
		created.getPersonalRecord().setPhone("954857465");
		created.getPersonalRecord().setLinkPhoto("http://www.rangerphoto.com/ranger3");
		created.getPersonalRecord().setLinkedInProfile("http://linkedin.com/ranger3");

		saved = this.curriculumService.save(created);
		Assert.notNull(saved);
		Assert.isTrue(saved == this.curriculumService.findCurriculumByPrincipal());

		super.unauthenticate();
	}

	@Test
	public void testEditCurriculum() {
		Curriculum result;
		Curriculum updated;

		super.authenticate("ranger1");
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.notNull(result);
		result.setTicker("121058-HTBA");
		updated = this.curriculumService.save(result);
		Assert.notNull(updated);
		result = this.curriculumService.findCurriculumByPrincipal();
		Assert.notNull(result);
		Assert.isTrue(updated == result);
		super.unauthenticate();
	}

	@Test
	public void testFindCurriculumByTrip() {
		Curriculum result;
		result = this.curriculumService.findCurriculumByTrip(1199);
		Assert.notNull(result);
		Assert.isTrue(result.getId() == 1355);
	}

}
