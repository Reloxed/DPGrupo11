
package controllers.referee;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class ReportRefereeController extends AbstractController {

	// Services

	@Autowired
	private ReportService		reportService;

	@Autowired
	private ComplaintService	complaintService;


	// Constructors

	public ReportRefereeController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Report> reports;

		reports = this.reportService.findReportByPrincipal();
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/referee/list.do");
		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		final ModelAndView result;

		final Report report;

		report = this.reportService.findOne(reportId);

		result = new ModelAndView("report/display");
		result.addObject("report", report);
		result.addObject("requestURI", "report/referee/display.do");

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "complaintId")
	public ModelAndView create(@RequestParam final int complaintId) {
		ModelAndView result;
		Report report;

		report = this.reportService.create();
		final Complaint complaint = this.complaintService.findOne(complaintId);
		report.setComplaint(complaint);

		result = this.createEditModelAndView(report);

		return result;
	}

	// Edition--------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;

		report = this.reportService.findOne(reportId);
		Assert.notNull(report);

		result = this.createEditModelAndView(report);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				report.setIsFinal(true);
				this.reportService.save(report);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(report, "report.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(report, "report.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Report report) {
		ModelAndView result;
		try {
			this.reportService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(report, "report.commit.error");
		}
		return result;
	}

	// Ancillary
	// methods--------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		final ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}
}
