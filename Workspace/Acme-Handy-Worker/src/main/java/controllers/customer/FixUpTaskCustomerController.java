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

import services.CustomerService;
import services.FixUpTaskService;

import controllers.AbstractController;

import domain.Customer;

import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController{

	
	//Services
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private CustomerService customerService;

	
	//Constructor
	
	public FixUpTaskCustomerController() {
		super();
	}
	
	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask task;
		
		task=this.fixUpTaskService.create();
		result=this.createEditModelAndView(task);
		return result;
		
		
		
	}
	
	//edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		FixUpTask task;

		task=this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(task);

		result=this.createEditModelAndView(task);

		return result;
		
	}
	
	//list
		@RequestMapping(value="/list",method=RequestMethod.GET)
		public ModelAndView list(){
			ModelAndView result;
			Collection<FixUpTask> fixUpTasks;
			Customer principal;
			
			principal=this.customerService.findByPrincipal();
			fixUpTasks=this.fixUpTaskService.FixUpTaskByCustomer(principal.getId());
			
			result=new ModelAndView("fixUpTask/list");
			result.addObject("fixUpTasks",fixUpTasks);
			result.addObject("principal",principal);
			result.addObject("requestUri","fixUpTask/customer/list.do");
			return result;
			
		}

	//Delete
	
	
	
	
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
				result = this.createEditModelAndView(task, "endorsement.commit.error");
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

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("message", message);
		return result;
	}
	
	
	
}
