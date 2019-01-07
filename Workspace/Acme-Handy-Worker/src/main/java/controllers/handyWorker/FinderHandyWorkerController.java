package controllers.handyWorker;



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

import services.ActorService;
import services.FinderService;
import services.HandyWorkerService;
import services.SystemConfigurationService;

import controllers.AbstractController;
import domain.Actor;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/finder/handyWorker")
public class FinderHandyWorkerController extends AbstractController {

	// Services

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ActorService		actorService;
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
		//int cacheTime;

		//cacheTime = this.systemConfigurationService.findAll().iterator().next().getTimeResultsCached();

		//this.finderService.deleteExpireFinders(cacheTime);

		finder = this.finderService.findByPrincipal();
		results = finder.getFixuptask();
		result = new ModelAndView("finder/list");
		result.addObject("finder", finder);
		result.addObject("results", results);
		result.addObject("requestUri", "finder/handyWorker/list.do");


		return result;

	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.create();
		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
		result.addObject("requestUri", "finder/handyWorker/search.do");


		return result;
	}


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		final Actor user = this.actorService.findByPrincipal();
		final HandyWorker hw = this.handyWorkerService.findOne(user.getId());

		finder = hw.getFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);

		return result;


	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:/");
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
