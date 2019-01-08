package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Complaint;

import services.ComplaintService;
import services.CustomerService;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController {

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

	// 
	
}