
package controllers.handyWorker;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FinderService;
import services.HandyWorkerService;
import services.WarrantyService;
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

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;


	// Constructors

	public FinderHandyWorkerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;
		Collection<FixUpTask> results;
		HandyWorker h;

		h = this.handyWorkerService.findByPrincipal();
		finder = h.getFinder();
		results = finder.getFixuptask();

		result = new ModelAndView("finder/list");
		result.addObject("results", results);
		result.addObject("requestUri", "finder/handyWorker/list.do");

		return result;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;
		HandyWorker hw;

		hw = this.handyWorkerService.findByPrincipal();
		finder = hw.getFinder();
		result = this.createEditModelAndView(finder);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("warranties", this.warrantyService.findAll());

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(finder);

		} else
			try {
				this.finderService.resultadosFinder(finder);
				result = new ModelAndView("redirect:/finder/handyWorker/list.do");
			} catch (final Throwable oops) {
				System.out.println(finder);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(finder);
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
		final Collection<FixUpTask> results;

		results = this.finderService.resultadosFinder(finder).getFixuptask();

		result = new ModelAndView("finder/search");
		result.addObject("message", messageCode);
		result.addObject("finder", finder);
		result.addObject("results", results);

		return result;
	}

}
