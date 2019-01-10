package controllers.administator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;

@Controller
@RequestMapping(value="statistics/administrator")
public class StatisticsAdministratorController extends AbstractController{
	
	//Services
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	
	//Constructor
	
	public StatisticsAdministratorController(){
		super();
		
	}
	
	//Display
	
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(){
		final ModelAndView result;
		Double[] allApplicationsPerFup;
		
		allApplicationsPerFup = this.fixUpTaskService.findApplicationsNumberOperations();
		allApplicationsPerFup.toString();
		
		result = new ModelAndView("administrator/dashboard");
		
		result.addObject("allApplicationsPerFup", allApplicationsPerFup);
		result.addObject("requestURI", "statistics/administrator/display.do");
		
		return result;
	}
	
}
