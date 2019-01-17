package controllers.application;

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

import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
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
	
	@Autowired
	private FixUpTaskService fixUpTaskService;

	// Constructors

	public ApplicationController() {
		super();
	}

	// List

	@RequestMapping(value = "/customer/list-customer")
	public ModelAndView list(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Collection<Application> applications;
		Customer fixUpTaskOwner;

		applications = this.applicationService
				.findAllApplicationsByFixUpTask(fixuptaskID);
		fixUpTaskOwner = this.customerService
				.findCustomerByApplicationId(applications.iterator().next()
						.getId());

		res = new ModelAndView("application/listCustomer");
		res.addObject("requestURI", "application/customer/list-customer.do");
		res.addObject("applications", applications);
		res.addObject("owner", fixUpTaskOwner);
		res.addObject("fixuptaskID", fixuptaskID);
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

		res = new ModelAndView("application/handy-worker/list-handy-worker");
		res.addObject("requestURI", "application/handy-worker/list-handy-worker.do");
		res.addObject("applications", applications);
		res.addObject("owner", principal);
		return res;
	}
	
	@RequestMapping(value = "/handy-worker/create", method = RequestMethod.GET, params = "fixUpTaskId")
	public ModelAndView create(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		Application application;
		
		application = this.applicationService.create();
		FixUpTask fix = this.fixUpTaskService.findOne(fixUpTaskId);
		application.setFixUpTask(fix);
		
		result = this.createEditModelAndView(application);
		
		return result;
	}
	
	@RequestMapping(value = "/handy-worker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int applicationId) {
		ModelAndView result;
		Application application;
		
		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		result = createEditModelAndView(application);
		
		return result;		
	}
	
	@RequestMapping(value="/handy-worker/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Application application, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			result = createEditModelAndView(application);
		} else {
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list-handy-worker.do");
			} catch (Throwable oops) {
				result = createEditModelAndView (application, "application.commit.error");
				}
			}
		return result;
	}
	
	
	// Create edit ModelAndView para customers
	// -----------------------------------------------
	
	protected ModelAndView createEditModelAndView(Application application) {
		ModelAndView result;
		
		result = createEditModelAndView(application, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Application application, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("application/handy-worker/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);
		
		return result;
	}
}