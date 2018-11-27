
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	// Supported services ---------------------------------------------
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		final Application a;

		a = this.applicationService.create();

		Assert.notNull(a);

		super.unauthenticate();

	}

	@Test
	public void testSave() {
		super.authenticate("handyWorker1");

		Application a;

		a = this.applicationService.create();
		a.setFixUpTask(this.fixUpTaskService.findOne(2429));
		this.applicationService.save(a);

		super.unauthenticate();
	}
}
