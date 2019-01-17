
package controllers.handyWorker;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.HandyWorkerService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.HandyWorker;
import domain.Report;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private HandyWorkerService	handyWorkerService;
	
	@Autowired
	private ReportService 		reportService;

	//Constructor

	public ComplaintHandyWorkerController() {
		super();
	}

	//listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Complaint> complaints;
		HandyWorker hw;
		Collection<Report> reports = new ArrayList<>();

		hw = this.handyWorkerService.findByPrincipal();
		complaints = this.complaintService.findComplaintsByHandyWorkerId(hw.getId());
		for(Complaint c: complaints){
			Report r = this.reportService.findReportByComplaint(c.getId());
			reports.add(r);
		}

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("reports", reports);
		result.addObject("requestUri", "complaint/handyWorker/list.do");
		return result;

	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.findOne(complaintId);

		result = new ModelAndView("complaint/display");
		result.addObject("complaint", complaint);
		result.addObject("requestUri", "complaint/handyWorker/display.do");

		return result;

	}
}
