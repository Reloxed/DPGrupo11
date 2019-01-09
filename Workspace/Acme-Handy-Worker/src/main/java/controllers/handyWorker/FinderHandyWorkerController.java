
package controllers.handyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/finder/handyWorker")
public class FinderHandyWorkerController extends AbstractController {

	// Services

	@Autowired
	private FinderService		finderService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//@Autowired
	//private SystemConfigurationService systemConfigurationService;

	// Constructors

	public FinderHandyWorkerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int finderId) {
		final ModelAndView result;
		final Finder finder;
		Collection<FixUpTask> results;

		//this.finderService.deleteExpireFinders();
		finder = this.finderService.findByPrincipal();
		results = finder.getFixuptask();

		result = new ModelAndView("finder/list");
		result.addObject("finder", finder);
		result.addObject("results", results);

		return result;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;
		HandyWorker hw;

		hw = this.handyWorkerService.findByPrincipal();
		finder = hw.getFinder();
		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
		result.addObject("requestUri", "finder/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.update.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;
		Collection<FixUpTask> results;

		results = finder.getFixuptask();

		result = new ModelAndView("finder/handyWorker/edit");
		result.addObject("messageCode", messageCode);
		result.addObject("finder", finder);
		result.addObject("results", results);

		return result;
	}

}
