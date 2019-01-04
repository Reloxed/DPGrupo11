package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController{
	
	//Services
	
	@Autowired
	private ComplaintService complaintService;
	
	//Constructor
	
	public ComplaintRefereeController() {
		super();
		
	}
	
	//Listing complaints assgined to referee logged.
	@RequestMapping(value="/list1", method= RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Complaint>complaints;
		
		complaints = this.complaintService.findComplaintsByReferee();
		
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/referee/list1.do");
		result.addObject("complaints", complaints);
		
		
		return result;
		
	}
	
	//Listing complaints not assigned to referee logged.
	@RequestMapping(value="/list2", method= RequestMethod.GET)
	public ModelAndView list1(){
		ModelAndView result;
		Collection<Complaint>notAssigned,refereeComplaints;
		
		notAssigned = this.complaintService.findAll();
		refereeComplaints = this.complaintService.findComplaintsByReferee();
		
		notAssigned.removeAll(refereeComplaints);
		
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/referee/list2.do");
		result.addObject("complaints", notAssigned);
		
		
		return result;
		
	}
}
