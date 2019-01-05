package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;


import controllers.AbstractController;
import domain.Complaint;
import domain.HandyWorker;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController{
	
	
	
	@Autowired
	private ComplaintService complaintService;	
	
	
	//Constructor
	
	public ComplaintHandyWorkerController() {
		super();
	}
	

	//listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int hw){
		final ModelAndView result;	
		final Collection<Complaint> complaints;
		complaints=this.complaintService.findComplaintsByHandyWorkerId(hw);
		
		result=new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestUri", "complaint/handyWorker/list.do");
		return result;


	}


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId){
		final ModelAndView result;	
		Complaint complaint;
		
		complaint=this.complaintService.findOne(complaintId);
		
		result=new ModelAndView("complaint/display");
		result.addObject("complaint", complaint);
		result.addObject("requestUri", "complaint/handyWorker/display.do");
		
		return result;
	
		
	}
}