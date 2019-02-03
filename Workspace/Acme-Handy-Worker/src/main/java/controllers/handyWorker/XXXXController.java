package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.HandyWorkerService;
import services.XXXXService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Report;
import domain.XXXX;

@Controller
@RequestMapping("/xxxx/handy-worker")
public class XXXXController extends AbstractController {

	// Services ###############################################################

	@Autowired
	private XXXXService xxxxService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Listing the xxxxs of a fix up task #####################################

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Collection<XXXX> xxxxs;

		xxxxs = this.xxxxService.findByFixUpTaskId(fixuptaskID);

		res = new ModelAndView("xxxx/list");
		res.addObject("xxxxs", xxxxs);

		return res;
	}

	// Displaying a xxxx ######################################################

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int xxxxID) {
		ModelAndView res;
		XXXX xxxx;

		xxxx = this.xxxxService.findOne(xxxxID);

		res = new ModelAndView("xxxx/display");
		res.addObject("xxxx", xxxx);

		return res;
	}

	// Creating a xxxx for a fix up task ######################################
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fixuptaskID) {
		ModelAndView res;
		FixUpTask fixUpTask;
		XXXX xxxx;

		fixUpTask = this.fixUpTaskService.findOne(fixuptaskID);
		Assert.notNull(fixUpTask, "fut.not.found");

		xxxx = this.xxxxService.create();
		xxxx.setFixUpTask(fixUpTask);
		fixUpTask.getXXXXs().add(xxxx);

		res = this.createEditModelAndView(xxxx);
		return res;
	}

	// Editing a xxxx #########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int xxxxID) {
		ModelAndView result;
		XXXX xxxx;

		xxxx = this.xxxxService.findOne(xxxxID);
		Assert.notNull(xxxx);
		Assert.isTrue(!xxxx.getIsFinal(), "xxxx.final");

		result = this.createEditModelAndView(xxxx);
		return result;
	}

	// Other methods ##########################################################

	private ModelAndView createEditModelAndView(XXXX xxxx) {
		ModelAndView result;

		result = this.createEditModelAndView(xxxx, null);

		return result;
	}

	private ModelAndView createEditModelAndView(XXXX xxxx, String message) {
		ModelAndView res;

		res = new ModelAndView("report/edit");
		res.addObject("xxxx", xxxx);
		res.addObject("message", message);

		return res;
	}
}
