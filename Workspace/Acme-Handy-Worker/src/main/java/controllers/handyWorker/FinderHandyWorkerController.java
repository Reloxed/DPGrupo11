
package controllers.handyWorker;

import java.util.ArrayList;
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
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.SystemConfigurationService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Application;
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
	
	@Autowired
	private SystemConfigurationService	systemConfigurationService;
	
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors

	public FinderHandyWorkerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;
		Collection<FixUpTask> fixUpTasks;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		finder = principal.getFinder();
		fixUpTasks = finder.getFixuptask();
		
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
		
		result = new ModelAndView("finder/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("vat", this.systemConfigurationService.findVAT());
		result.addObject("collFixUpTasks", collFixUpTasks);
		result.addObject("requestUri", "finder/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		finder = principal.getFinder();
		result = this.createEditModelAndView(finder);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("warranties", this.warrantyService.findAll());

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder res;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(finder);

		} else
			try {
				res = this.finderService.resultadosFinder(finder);
				// this.finderService.save(res);
				result = new ModelAndView("redirect:/finder/handyWorker/list.do");
			} catch (final Throwable oops) {
				System.out.println(finder.getFixuptask());
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
