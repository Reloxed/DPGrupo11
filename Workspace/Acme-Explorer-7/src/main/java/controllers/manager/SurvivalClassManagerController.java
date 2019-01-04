
package controllers.manager;

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

import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	// Services

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ManagerService			managerService;


	// Constructors

	public SurvivalClassManagerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		Collection<Trip> futureNoCancelledTrips;

		survivalClasses = this.survivalClassService.findByPrincipal();
		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("futureNoCancelledTrips", futureNoCancelledTrips);

		return result;
	}

	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Trip trip;

		survivalClass = this.survivalClassService.create();

		trip = this.tripService.findOne(tripId);
		survivalClass.setTrip(trip);
		result = this.createEditModelAndView(survivalClass);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);

		result = this.createEditModelAndView(survivalClass);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		try {
			this.survivalClassService.delete(survivalClass);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClass, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String message) {
		final ModelAndView result;
		Manager principal;
		boolean permission = false;

		Collection<Trip> futureNoCancelledTrips;

		principal = this.managerService.findByPrincipal();
		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();

		permission = survivalClass.getTrip().getManager().getId() == principal.getId();

		if (survivalClass.getId() == 0)
			permission = permission && futureNoCancelledTrips.contains(survivalClass.getTrip());

		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("message", message);
		result.addObject("permission", permission);
		return result;
	}

}
