
package controllers.handyWorker;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Category;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUptaskHandyWorkerController extends AbstractController {

	//Services

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;


	//Constructor

	public FixUptaskHandyWorkerController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int taskId, final Locale locale) {
		final ModelAndView result;
		FixUpTask fixUpTask;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		int customerId;
		
		

		fixUpTask = this.fixUpTaskService.findOne(taskId);
		customerId = this.fixUpTaskService.creatorFixUpTask(fixUpTask.getId());
		language = locale.getLanguage();
		result = new ModelAndView("fixUpTask/display");
		result.addObject("customerId", customerId);
		result.addObject("fixUpTask", fixUpTask);
		
		if(fixUpTask.getCategory()==null){
			fixUpTask.setCategory(fixUpTask.getCategory().getParentCategory());
		}
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		result.addObject("requestUri", "fixUpTask/handyWorker/display.do");

		return result;

	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		final int customerId;
		//Customer principal;
		//principal=this.customerService.findByPrincipal();
		fixUpTasks = this.fixUpTaskService.findAll();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		//result.addObject("principal",principal);
		result.addObject("requestUri", "fixUpTask/handyWorker/list.do");
		return result;

	}

}
