package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CurriculumServiceTest extends AbstractTest{

	// System under test ------------------------------------------------------

	@Autowired
	private CurriculumService curriculumService;	
	
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
	
	@Test
	public void testSaveCurriculum() {
		Curriculum curriculum;

		String username = "handyWorker1";
		super.authenticate(username);
		curriculum = curriculumService.create();
		Assert.notNull(curriculum);
		super.unauthenticate();
	}
}
