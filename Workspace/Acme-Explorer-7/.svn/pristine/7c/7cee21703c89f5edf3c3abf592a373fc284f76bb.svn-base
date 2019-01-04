
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	// Services

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripService				tripService;


	// Constructors

	public SurvivalClassExplorerController() {
		super();
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		final ModelAndView result;
		Boolean canExplorerEnrolSurvivalClass;
		Boolean canExplorerUnregisterSurvivalClass;
		Explorer explorer;

		final SurvivalClass survivalClass;
		Collection<Trip> futureNoCancelledTrips;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();

		result = new ModelAndView("survivalClass/display");

		explorer = null;

		canExplorerEnrolSurvivalClass = this.explorerService.canExplorerEnrolSurvivalClass(survivalClass);
		canExplorerUnregisterSurvivalClass = this.explorerService.canExplorerUnregisterSurvivalClass(survivalClass);

		if (canExplorerEnrolSurvivalClass)
			explorer = this.explorerService.findByPrincipal();
		result.addObject("futureNoCancelledTrips", futureNoCancelledTrips);
		result.addObject("canExplorerEnrolSurvivalClass", canExplorerEnrolSurvivalClass);
		result.addObject("canExplorerUnregisterSurvivalClass", canExplorerUnregisterSurvivalClass);
		result.addObject("explorer", explorer);
		result.addObject("survivalClass", survivalClass);

		return result;

	}

	@RequestMapping(value = "/enrol", method = RequestMethod.GET)
	public ModelAndView enrol(@RequestParam final int survivalClassId) {
		final ModelAndView result;
		Explorer explorer;
		final SurvivalClass survivalClass;
		Boolean canExplorerEnrolSurvivalClass;
		Boolean canExplorerUnregisterSurvivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		explorer = this.explorerService.findByPrincipal();

		canExplorerUnregisterSurvivalClass = this.explorerService.canExplorerUnregisterSurvivalClass(survivalClass);

		canExplorerEnrolSurvivalClass = this.explorerService.canExplorerEnrolSurvivalClass(survivalClass);

		if (canExplorerEnrolSurvivalClass) {
			this.survivalClassService.enrol(survivalClass);
			canExplorerEnrolSurvivalClass = false;
			canExplorerUnregisterSurvivalClass = true;
		}

		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);
		result.addObject("canExplorerEnrolSurvivalClass", canExplorerEnrolSurvivalClass);
		result.addObject("canExplorerUnregisterSurvivalClass", canExplorerUnregisterSurvivalClass);
		result.addObject("explorer", explorer);
		return result;

	}

	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam final int survivalClassId) {
		final ModelAndView result;
		Explorer explorer;
		final SurvivalClass survivalClass;
		Boolean canExplorerEnrolSurvivalClass;
		Boolean canExplorerUnregisterSurvivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);

		canExplorerEnrolSurvivalClass = this.explorerService.canExplorerEnrolSurvivalClass(survivalClass);
		canExplorerUnregisterSurvivalClass = this.explorerService.canExplorerUnregisterSurvivalClass(survivalClass);

		if (canExplorerUnregisterSurvivalClass) {
			this.survivalClassService.unregister(survivalClass);
			canExplorerUnregisterSurvivalClass = false;
			canExplorerEnrolSurvivalClass = true;
		}
		explorer = this.explorerService.findByPrincipal();

		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);
		result.addObject("canExplorerEnrolSurvivalClass", canExplorerEnrolSurvivalClass);
		result.addObject("canExplorerUnregisterSurvivalClass", canExplorerUnregisterSurvivalClass);
		result.addObject("explorer", explorer);
		return result;

	}
}
