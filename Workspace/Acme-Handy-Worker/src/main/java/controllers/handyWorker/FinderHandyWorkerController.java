package controllers.handyWorker;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.SystemConfigurationService;

import controllers.AbstractController;
import domain.Finder;

@Controller
@RequestMapping("/finder/handyWorker")
public class FinderHandyWorkerController extends AbstractController {

	// Services

	@Autowired
	private FinderService			finderService;
	
	@Autowired
	private SystemConfigurationService systemConfigurationService;
	
	// Constructors

		public FinderHandyWorkerController() {
			super();
		}

	
		// Listing

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			final ModelAndView result;
			final Finder finder;
			int cacheTime;

			cacheTime = this.systemConfigurationService.findAll().iterator().next().getTimeResultsCached();

			this.finderService.deleteExpireFinders(cacheTime);

			finder = this.finderService.findByPrincipal();

			result = new ModelAndView("finder/list");
			result.addObject("finder", finder);

			return result;

		}
		
		
}
