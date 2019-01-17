package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;

import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("report/handyWorker")
public class ReportHandyWorkerController extends AbstractController {

	// Services
	
		@Autowired
		private ReportService reportService;
		
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
}
