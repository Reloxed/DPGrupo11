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

	// Tests ------------------------------------------------------------------

	@Test
	public void testFindReportByPrincipal() {
		Collection<Report> res;

		super.authenticate("referee1");

		res = this.reportService.findReportByPrincipal();
		Assert.notEmpty(res);

	}
	
	@Test
	public void testFindAll(){
		Collection<Report> collRep;

		collRep = this.reportService.findAll();
		Assert.notNull(collRep);
		Assert.notEmpty(collRep);

	}
	
	@Test
	public void testFindOne(){
		Collection<Report> collRep;
		Report report;

		collRep = this.reportService.findAll();
		report = this.reportService.findOne(collRep.iterator().next().getId());
		Assert.notNull(report);

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
		res.setComplaint(principal.getComplaints().iterator().next());
		res.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		res.setDescription("Description nigeria-sexo");
		res.setIsFinal(false);

		saved = this.reportService.save(res);
		Assert.notNull(saved);

		res = this.reportService.findOne(saved.getId());
		Assert.notNull(res);
	}

	@Test
	public void testDelete() {
		Report toDelete = new Report();
		Collection<Report> reports;

		super.authenticate("referee1");

		reports = this.reportService.findReportByPrincipal();
		Assert.notEmpty(reports);

		toDelete = reports.iterator().next();
		toDelete.setIsFinal(false);
		this.reportService.delete(toDelete);

	}
}
