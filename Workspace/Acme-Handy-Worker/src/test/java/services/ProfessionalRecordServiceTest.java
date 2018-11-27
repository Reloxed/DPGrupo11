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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindByPrincipal() {
		Collection<ProfessionalRecord> res;
		super.authenticate("handyWorker2");
		res = this.professionalRecordService.findByPrincipal();
		Assert.notNull(res);
		Assert.isTrue(res.size() == 2);
		super.unauthenticate();
	}
}
