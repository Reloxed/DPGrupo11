package controllers.handyWorker;

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

import services.CustomerService;
import services.FixUpTaskService;
import services.XXXXService;
import controllers.AbstractController;
import domain.Customer;
import domain.FixUpTask;
import domain.XXXX;

@Controller
@RequestMapping("/xxxx")
public class XXXXAdministrationController extends AbstractController {

	// Services ###############################################################

	@Autowired
	private XXXXService xxxxService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CustomerService customerService;

	// Listing the xxxxs of a fix up task #####################################

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int fixuptaskID, Locale locale) {
		ModelAndView res;
		Collection<XXXX> allxxxxs;
		Collection<XXXX> publishedxxxxs;
		int ownerID;
		Customer owner;
		String language;
		String espanyol;
		espanyol = "es";

		allxxxxs = this.xxxxService.findByFixUpTaskId(fixuptaskID);
		ownerID = this.fixUpTaskService.creatorFixUpTask(fixuptaskID);
		owner = this.customerService.findOne(ownerID);

		publishedxxxxs = new ArrayList<>();
		for (XXXX xxxx : allxxxxs) {
			if (xxxx.getIsFinal()) {
				publishedxxxxs.add(xxxx);
			}
		}
		language = locale.getLanguage();

		res = new ModelAndView("xxxx/list");
		res.addObject("xxxxs", allxxxxs);
		res.addObject("publishedxxxxs", publishedxxxxs);
		res.addObject("language", language);
		res.addObject("espanyol", espanyol);
		res.addObject("owner", owner);

		return res;
	}

	// Displaying a xxxx ######################################################

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int xxxxID, Locale locale) {
		ModelAndView res;
		XXXX xxxx;
		int authorID;
		Customer author;
		String language;
		String espanyol;
		espanyol = "es";

		xxxx = this.xxxxService.findOne(xxxxID);
		authorID = this.fixUpTaskService.creatorFixUpTask(xxxx.getFixUpTask()
				.getId());
		author = this.customerService.findOne(authorID);
		language = locale.getLanguage();

		res = new ModelAndView("xxxx/display");
		res.addObject("xxxx", xxxx);
		res.addObject("author", author);
		res.addObject("language", language);
		res.addObject("espanyol", espanyol);

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

	// Saving as final ########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@Valid XXXX xxxx, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(xxxx);
		else
			try {
				xxxx.setIsFinal(true);
				this.xxxxService.save(xxxx);
				res = new ModelAndView("redirect:list.do?fixuptaskID="
						+ xxxx.getFixUpTask().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(xxxx, "xxxx.commit.error");
			}
		return res;
	}

	// Saving as draft ########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@Valid XXXX xxxx, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(xxxx);
		else
			try {
				this.xxxxService.save(xxxx);
				res = new ModelAndView("redirect:list.do?fixuptaskID="
						+ xxxx.getFixUpTask().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(xxxx, "xxxx.commit.error");
			}
		return res;
	}

	// Deleting a draft #######################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid XXXX xxxx) {
		ModelAndView result;
		try {
			this.xxxxService.delete(xxxx);
			result = new ModelAndView("redirect:list.do?fixuptaskID="
					+ xxxx.getFixUpTask().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(xxxx, "xxxx.commit.error");
		}
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

		res = new ModelAndView("xxxx/edit");
		res.addObject("xxxx", xxxx);
		res.addObject("message", message);

		return res;
	}
}
