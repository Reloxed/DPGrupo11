
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
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Managed services ------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		Complaint c;

		c = this.complaintService.create();
		Assert.notNull(c);

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("customer1");
		Complaint c, saved;
		Collection<Complaint> complaints;

		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.size() == 3);
		c = this.complaintService.create();
		Assert.notNull(c);

		c.setDescription("Description");
		c.setFixUpTask(this.fixUpTaskService.findOne(2429));
		saved = this.complaintService.save(c);

		Assert.isTrue(this.complaintService.findAll().contains(saved));
		Assert.isTrue(this.complaintService.findAll().size() == 4);

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		super.authenticate(null);

		Assert.notNull(this.complaintService.findAll());

		super.unauthenticate();
	}
}
