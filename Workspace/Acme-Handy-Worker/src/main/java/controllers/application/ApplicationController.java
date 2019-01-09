package controllers.application;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Constructors

	public ApplicationController() {
		super();
	}

	// List

	@RequestMapping(value = "/customer/list-customer", params = "fixuptaskID")
	public ModelAndView list(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Collection<Application> applications;
		Customer fixUpTaskOwner;
		boolean hasAccepted = false;

		applications = this.applicationService
				.findAllApplicationsByFixUpTask(fixuptaskID);
		fixUpTaskOwner = this.customerService
				.findCustomerByApplicationId(applications.iterator().next()
						.getId());
		
		for (Application application : applications) {
			if (application.getStatus().equals("ACCEPTED")) {
				hasAccepted = true;
				break;
			}
		}
		
		res = new ModelAndView("application/listCustomer");
		res.addObject("requestURI", "application/customer/list-customer.do");
		res.addObject("applications", applications);
		res.addObject("owner", fixUpTaskOwner);
		res.addObject("hasAccepted", hasAccepted);
		return res;
	}

	@RequestMapping(value = "/handy-worker/list-handy-worker")
	public ModelAndView list() {
		ModelAndView res;
		Collection<Application> applications;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();

		applications = this.applicationService
				.findAllApplicationsByHandyWorker(principal.getId());

		res = new ModelAndView("application/listHandyWorker");
		res.addObject("requestURI", "application/customer/list-.do");
		res.addObject("applications", applications);
		res.addObject("owner", principal);
		return res;
	}
	
	// Accept application
	
	@RequestMapping(value = "/customer/accept")
	public ModelAndView accept(@RequestParam int applicationID){
		ModelAndView res;
		Application toAccept;
		
		return res;
	}
}
