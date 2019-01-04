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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import utilities.AbstractTest;
import domain.LegalText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LegalTextService	legalTextService;


	// Tests

	@Test
	public void testCreate() {
		super.authenticate("administrator1");
		LegalText result;

		result = this.legalTextService.create();

		Assert.notNull(result);

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("administrator1");
		LegalText legalText, saved;
		int numberLegalTextsBeforeSave;
		int numberLegalTextsAfterSave;
		final List<String> laws;
		Collection<LegalText> legalTextsAfterSave;

		laws = new ArrayList<String>();
		laws.add("law 1");
		laws.add("law 2");
		laws.add("law 3");

		legalText = this.legalTextService.create();
		Assert.notNull(legalText);

		legalText.setBody("body1");
		legalText.setTitle("title1");
		legalText.setLaws(laws);

		numberLegalTextsBeforeSave = this.legalTextService.findAll().size();
		Assert.isTrue(numberLegalTextsBeforeSave == 2);

		saved = this.legalTextService.save(legalText, true);
		Assert.notNull(saved);

		legalTextsAfterSave = this.legalTextService.findAll();
		Assert.isTrue(legalTextsAfterSave.contains(saved));

		numberLegalTextsAfterSave = this.legalTextService.findAll().size();
		Assert.isTrue(numberLegalTextsAfterSave == 3);

		super.authenticate(null);

	}

}
