
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SurvivalClassService;
import services.TripService;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass")
public class SurvivalClassController extends AbstractController {

	// Services

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;


	// Constructors

	public SurvivalClassController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		final ModelAndView result;

		final SurvivalClass survivalClass;
		Collection<Trip> futureNoCancelledTrips;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();

		result = new ModelAndView("survivalClass/display");

		result.addObject("futureNoCancelledTrips", futureNoCancelledTrips);
		result.addObject("survivalClass", survivalClass);

		return result;

	}
}
