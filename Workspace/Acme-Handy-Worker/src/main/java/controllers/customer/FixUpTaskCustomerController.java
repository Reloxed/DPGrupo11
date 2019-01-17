package controllers.customer;

import java.util.ArrayList;
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
import services.CategoryService;
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	// Services

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private WarrantyService warrantyService;

	// Constructor

	public FixUpTaskCustomerController() {
		super();
	}

	// Display

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final Locale locale) {
		ModelAndView result;
		FixUpTask task;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		language = locale.getLanguage();
		task = this.fixUpTaskService.create();
		result = this.createEditModelAndView(task);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		return result;

	}

	// Display-----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int fixUpTaskId,final Locale locale) {

		ModelAndView result;
		FixUpTask fixUpTask;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		
		language = locale.getLanguage();
		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		result = new ModelAndView("fixUpTask/display");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("customerOwner",
				this.fixUpTaskService.creatorFixUpTask(fixUpTaskId));
		result.addObject("requestURI", "fixUpTask/customer/display.do");
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);

		return result;
	}

	// edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int fixUpTaskId,final Locale locale) {
		final ModelAndView result;
		FixUpTask task;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		
		language = locale.getLanguage();
		task = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(task);

		result = this.createEditModelAndView(task);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);

		return result;

	}

	// list

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		fixUpTasks = this.fixUpTaskService.FixUpTaskByCustomer(principal
				.getId());

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("principal", principal);
		result.addObject("requestUri", "fixUpTask/customer/list.do");

		principal = this.customerService.findByPrincipal();
		fixUpTasks = this.fixUpTaskService.FixUpTaskByCustomer(principal
				.getId());

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
			result = this
					.createEditModelAndView(task, "fixUpTask.commit.error");
		}

		return result;

	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask task,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(task);
		else
			try {
				Customer principal;
				FixUpTask saved;
				principal = this.customerService.findByPrincipal();
				saved = this.fixUpTaskService.save(task);
<<<<<<< HEAD
				if(!principal.getFixUpTasks().contains(task)){
					principal.getFixUpTasks().add(this.fixUpTaskService.findOne(saved.getId()));
=======
				if (!principal.getFixUpTasks().contains(task)) {
					principal.getFixUpTasks().add(
							this.fixUpTaskService.findOne(saved.getId()));
>>>>>>> Figueroa
					this.customerService.save(principal);
				}
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(binding.getAllErrors());
				result = this.createEditModelAndView(task, oops.getMessage());
			}

		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final FixUpTask task) {
		ModelAndView result;

		result = this.createEditModelAndView(task, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask task,
			final String message) {
		ModelAndView result;
		Collection<Complaint> complaints;
		Collection<Application> applications;
		String ticker;
		Collection<Category> categories = new ArrayList<>();
		Collection<Warranty> warranties;
		
		
		categories = this.categoryService.findAll();
		warranties = this.warrantyService.findFinalWarranties();

		complaints = this.complaintService.findComplaintsByCustomer();
		applications = this.applicationService.findAllApplicationsByCustomer();

		ticker = task.getTicker();
		result = new ModelAndView("fixUpTask/edit");
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);
		result.addObject("fixUpTask", task);
		result.addObject("message", message);
		result.addObject("complaints", complaints);
		result.addObject("applications", applications);
		result.addObject("ticker", ticker);
		return result;
	}

}
