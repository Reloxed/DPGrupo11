
package controllers.customer;

import java.util.Collection;
import java.util.Locale;

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
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;

import domain.Application;
import domain.Complaint;

import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {


	//Services

	@Autowired
	private FixUpTaskService fixUpTaskService;


	@Autowired
	private CustomerService		customerService;



	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private ApplicationService applicationService;

	//Constructor

	public FixUpTaskCustomerController() {
		super();
	}

	//Display



	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask task;


		task=this.fixUpTaskService.create();
		result=this.createEditModelAndView(task);
		return result;



	}

	//Display-----------------------------------------------------------

	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int taskId){

		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(taskId);

		result = new ModelAndView("fixUpTask/display");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("requestURI", "fixUpTask/customer/display.do");

		return result;
	}

		
	

	//edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int fixUpTaskId) {
		final ModelAndView result;
		FixUpTask task;

		task = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(task);

		result = this.createEditModelAndView(task);

		return result;

	}

	//list


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		Customer principal;


		principal=this.customerService.findByPrincipal();
		fixUpTasks=this.fixUpTaskService.FixUpTaskByCustomer(principal.getId());

		result=new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks",fixUpTasks);
		result.addObject("principal",principal);
		result.addObject("requestUri","fixUpTask/customer/list.do");

		principal = this.customerService.findByPrincipal();
		fixUpTasks = this.fixUpTaskService.FixUpTaskByCustomer(principal.getId());

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("principal", principal);
		result.addObject("requestUri", "fixUpTask/customer/list.do");

		return result;

	}

	

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final FixUpTask task) {
		ModelAndView result;

		try {
			this.fixUpTaskService.delete(task);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(task, "fixUpTask.commit.error");
		}

		return result;

	}



	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask task, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(task);
		else
			try {
				this.fixUpTaskService.save(task);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(binding.getAllErrors());
				result = this.createEditModelAndView(task, "fixUpTask.commit.error");
			}

		return result;

	}



	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FixUpTask task) {
		ModelAndView result;

		result = this.createEditModelAndView(task, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask task, final String message) {
		ModelAndView result;
		Collection<Complaint> complaints;
		Collection<Application> applications;
		String ticker;

		complaints = this.complaintService.findComplaintsByCustomer();
		applications = this.applicationService.findAllApplicationsByCustomer();
		ticker = task.getTicker();


		result = new ModelAndView("fixUpTask/edit");
		result.addObject("fixUpTask", task);
		result.addObject("message", message);
		result.addObject("complaints", complaints);
		result.addObject("applications", applications);
		result.addObject("ticker", ticker);
		return result;
	}




}
