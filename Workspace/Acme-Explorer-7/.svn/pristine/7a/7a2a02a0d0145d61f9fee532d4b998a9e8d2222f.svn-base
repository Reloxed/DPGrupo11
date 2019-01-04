
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.FinderService;
import controllers.AbstractController;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public FinderExplorerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Finder> finders;
		int cacheTime;

		cacheTime = this.customisationService.find().getFinderCacheTime();

		this.finderService.deleteOldFinders(cacheTime);

		finders = this.finderService.findByPrincipal();

		result = new ModelAndView("finder/list");
		result.addObject("finders", finders);

		return result;

	}

}
