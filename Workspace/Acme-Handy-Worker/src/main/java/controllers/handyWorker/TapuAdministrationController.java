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
import services.TapuService;
import controllers.AbstractController;
import domain.Customer;
import domain.FixUpTask;
import domain.Tapu;

@Controller
@RequestMapping("/tapu")
public class TapuAdministrationController extends AbstractController {

	// Services ###############################################################

	@Autowired
	private TapuService tapuService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CustomerService customerService;

	// Listing the tapus of a fix up task #####################################

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int fixuptaskID, Locale locale) {
		ModelAndView res;
		Collection<Tapu> alltapus;
		Collection<Tapu> publishedtapus;
		int ownerID;
		Customer owner;
		String language;
		String espanyol;
		espanyol = "es";

		alltapus = this.tapuService.findByFixUpTaskId(fixuptaskID);
		ownerID = this.fixUpTaskService.creatorFixUpTask(fixuptaskID);
		owner = this.customerService.findOne(ownerID);

		publishedtapus = new ArrayList<>();
		for (Tapu tapu : alltapus) {
			if (tapu.getIsFinal()) {
				publishedtapus.add(tapu);
			}
		}
		language = locale.getLanguage();

		res = new ModelAndView("tapu/list");
		res.addObject("tapus", alltapus);
		res.addObject("publishedtapus", publishedtapus);
		res.addObject("language", language);
		res.addObject("espanyol", espanyol);
		res.addObject("owner", owner);

		return res;
	}

	// Displaying a tapu ######################################################

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int tapuID, Locale locale) {
		ModelAndView res;
		Tapu tapu;
		int authorID;
		Customer author;
		String language;
		String espanyol;
		espanyol = "es";

		tapu = this.tapuService.findOne(tapuID);
		authorID = this.fixUpTaskService.creatorFixUpTask(tapu.getFixUpTask()
				.getId());
		author = this.customerService.findOne(authorID);
		language = locale.getLanguage();

		res = new ModelAndView("tapu/display");
		res.addObject("tapu", tapu);
		res.addObject("author", author);
		res.addObject("language", language);
		res.addObject("espanyol", espanyol);

		return res;
	}

	// Creating a tapu for a fix up task ######################################
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int fixuptaskID) {
		ModelAndView res;
		FixUpTask fixUpTask;
		Tapu tapu;

		fixUpTask = this.fixUpTaskService.findOne(fixuptaskID);
		Assert.notNull(fixUpTask, "fut.not.found");

		tapu = this.tapuService.create();
		tapu.setFixUpTask(fixUpTask);
		fixUpTask.getTapus().add(tapu);

		res = this.createEditModelAndView(tapu);
		return res;
	}

	// Editing a tapu #########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tapuID) {
		ModelAndView result;
		Tapu tapu;

		tapu = this.tapuService.findOne(tapuID);
		Assert.notNull(tapu);
		Assert.isTrue(!tapu.getIsFinal(), "tapu.final");

		result = this.createEditModelAndView(tapu);
		return result;
	}

	// Saving as final ########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@Valid Tapu tapu, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(tapu);
		else
			try {
				tapu.setIsFinal(true);
				this.tapuService.save(tapu);
				res = new ModelAndView("redirect:list.do?fixuptaskID="
						+ tapu.getFixUpTask().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(tapu, "tapu.commit.error");
			}
		return res;
	}

	// Saving as draft ########################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@Valid Tapu tapu, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(tapu);
		else
			try {
				this.tapuService.save(tapu);
				res = new ModelAndView("redirect:list.do?fixuptaskID="
						+ tapu.getFixUpTask().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(tapu, "tapu.commit.error");
			}
		return res;
	}

	// Deleting a draft #######################################################

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Tapu tapu) {
		ModelAndView result;
		try {
			this.tapuService.delete(tapu);
			result = new ModelAndView("redirect:list.do?fixuptaskID="
					+ tapu.getFixUpTask().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tapu, "tapu.commit.error");
		}
		return result;
	}

	// Other methods ##########################################################

	private ModelAndView createEditModelAndView(Tapu tapu) {
		ModelAndView result;

		result = this.createEditModelAndView(tapu, null);

		return result;
	}

	private ModelAndView createEditModelAndView(Tapu tapu, String message) {
		ModelAndView res;

		res = new ModelAndView("tapu/edit");
		res.addObject("tapu", tapu);
		res.addObject("message", message);

		return res;
	}
}
