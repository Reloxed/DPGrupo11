
package controllers.referee;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	//Services

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private ReportService 		reportService;
	
	//Constructor

	public ComplaintRefereeController() {
		super();

	}

	//Listing complaints assgined to referee logged.
	@RequestMapping(value = "/listAssigned", method = RequestMethod.GET)
	public ModelAndView listAssigned() {
		ModelAndView result;
		Collection<Complaint> complaints;
		Collection<Report> reports = new ArrayList<>();

		complaints = this.complaintService.findComplaintsByReferee();
		for(Complaint c: complaints){
			Report r = this.reportService.findReportByComplaint(c.getId());
			if(r != null) reports.add(r);
		}

		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/referee/listAssigned.do");
		result.addObject("complaints", complaints);
		result.addObject("reports", reports);

		return result;

	}

	//Listing complaints not assigned to referee logged.
	@RequestMapping(value = "/listNotAssigned", method = RequestMethod.GET)
	public ModelAndView listNotAssigned() {
		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = this.complaintService.findNotAssignedComplaints();

		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/referee/listNotAssigned.do");
		result.addObject("complaints", complaints);

		return result;

	}
	@RequestMapping(value = "/assignReferee", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int complaintId) {
		final ModelAndView result;
		final Complaint complaint;
		Collection<Complaint> complaints;

		complaint = this.complaintService.findOne(complaintId);
		this.complaintService.assignReferee(complaint);
		complaints = this.complaintService.findComplaintsByReferee();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/referee/listAssigned.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.findOne(complaintId);

		result = new ModelAndView("complaint/display");
		result.addObject("complaint", complaint);
		result.addObject("requestUri", "complaint/customer/display.do");

		return result;

	}

}
//Ancillary methods

