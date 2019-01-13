
package controllers.handyWorker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.SystemConfigurationService;
import controllers.AbstractController;

import domain.Category;

import domain.Application;

import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUptaskHandyWorkerController extends AbstractController {


	//Services

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	// Constructor

	public FixUptaskHandyWorkerController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int taskId, final Locale locale) {

		final ModelAndView result;
		FixUpTask fixUpTask;
		String language;
		String espa�ol;
		String english;
		espa�ol = "es";
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
		result.addObject("espa�ol", espa�ol);
		result.addObject("english", english);
		result.addObject("requestUri", "fixUpTask/handyWorker/display.do");

		return result;

	}


	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findAll();

		List<FixUpTask> collFixUpTasks = new ArrayList<>();
		for (FixUpTask fix : this.fixUpTaskService.findAll()) {
			if (!fix.getApplications().isEmpty()) {
				for (Application app : fix.getApplications()) {
					if (app.getStatus().equals("ACCEPTED")) {
						collFixUpTasks.add(fix);
					}
				}
			}
		}

//		System.out.println(collFixUpTasks);

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("collFixUpTasks", collFixUpTasks);
		// result.addObject("principal",principal);
		result.addObject("requestUri", "fixUpTask/handyWorker/list.do");
		result.addObject("vat", this.systemConfigurationService.findVAT());

		return result;

	}

}
