package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Referee;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ReportServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ReportService reportService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private ComplaintService complaintService;

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindReportByPrincipal() {
		Collection<Report> res;

		super.authenticate("referee1");

		res = this.reportService.findReportByPrincipal();
		Assert.notEmpty(res);

		System.out.println(res);
	}

	@Test
	public void testCreateAndSave() {
		Report res;
		Report saved;
		Referee principal;

		super.authenticate("referee2");
		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		res = this.reportService.create();
		res.setComplaint(this.complaintService.findOne(2433));
		res.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		res.setDescription("Description");
		res.setIsFinal(false);

		saved = this.reportService.save(res);
		Assert.notNull(saved);
	}

	@Test
	public void testDelete() {
		Report toDelete = new Report();
		Collection<Report> reports;

		super.authenticate("referee1");

		reports = this.reportService.findReportByPrincipal();
		Assert.notEmpty(reports);

		// toDelete = reports.iterator().next();
		toDelete = this.reportService.findOne(2584);
		toDelete.setIsFinal(false);
		this.reportService.delete(toDelete);

		reports = this.reportService.findReportByPrincipal();
		Assert.isTrue(reports.size() == 2);
	}
}
