package controllers.customer;

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
import services.CustomerService;
import controllers.AbstractController;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController{

	// Services used

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private CustomerService customerService;

	// Listing complaints for authenticated customer
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = this.customerService.findByPrincipal().getComplaints();

		result = new ModelAndView("complaint/customer/list");
		result.addObject("complaints", complaints);
		result.addObject("requestUri", "/complaint/customer/list.do");

		return result;
	}

	// Display
	
	
	
	// Create
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Complaint complaint;
		
		complaint = this.complaintService.create();
		result = this.createEditModelAndView(complaint);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int complaintId){
		ModelAndView result;
		Complaint complaint;
		
		complaint = this.complaintService.findOne(complaintId);
		Assert.notNull(complaint);
		
		result = this.createEditModelAndView(complaint);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(complaint);
		} else {
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods
	
	protected ModelAndView createEditModelAndView(Complaint complaint){
		ModelAndView result;
		
		result = this.createEditModelAndView(complaint, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Complaint complaint, String message){
		ModelAndView result;
		Collection<FixUpTask> listFixUpTasks;
		
		listFixUpTasks = this.customerService.findByPrincipal().getFixUpTasks();
		
		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("listFixUpTasks", listFixUpTasks);
		
		return result;
	}
}