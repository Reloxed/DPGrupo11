
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

import services.CategoryService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.TagService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/trip/manager")
public class TripManagerController extends AbstractController {

	// Services

	@Autowired
	private TripService			tripService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private TagService			tagService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private ManagerService		managerService;


	// Constructors

	public TripManagerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Trip> trips;
		final Collection<Trip> publishedTrips;
		final Collection<Trip> pendingTrips;
		Collection<Trip> futureNoCancelledTrips;
		Boolean searching;

		trips = this.tripService.findByManager(); //  trips organised by the manager
		publishedTrips = this.tripService.findAllPublishedTrips(); //  published trips 
		//	publishedTrips.retainAll(trips); // published trips organised by the manager

		pendingTrips = this.tripService.findAllPendingTrips(); // pending trips
		//	pendingTrips.retainAll(trips);              	      // pending trips organised by the manager

		searching = false;

		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("publishedTrips", publishedTrips);
		result.addObject("pendingTrips", pendingTrips);
		result.addObject("searching", searching);
		result.addObject("futureNoCancelledTrips", futureNoCancelledTrips);
		result.addObject("principal", "MANAGER");
		result.addObject("requestURI", "trip/manager/list.do");

		return result;

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("keyword") final String keyword) {
		final ModelAndView result;
		final Collection<Trip> trips;
		final Collection<Trip> results;
		final Collection<Trip> publishedTrips;
		final Collection<Trip> pendingTrips;
		Collection<Trip> futureNoCancelledTrips;
		Boolean searching;

		trips = this.tripService.findByManager();
		results = this.tripService.findBySingleKey(keyword);
		trips.retainAll(results);

		publishedTrips = this.tripService.findAllPublishedTrips();

		pendingTrips = this.tripService.findAllPendingTrips();

		searching = true;

		futureNoCancelledTrips = this.tripService.findAllFutureNoCancelledTrips();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("publishedTrips", publishedTrips);
		result.addObject("pendingTrips", pendingTrips);
		result.addObject("searching", searching);
		result.addObject("futureNoCancelledTrips", futureNoCancelledTrips);
		result.addObject("principal", "MANAGER");
		result.addObject("requestURI", "trip/manager/list.do");

		return result;

	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.create();
		result = this.createEditModelAndView(trip);

		result.addObject("cancel", false);
		result.addObject("permission", true);
		result.addObject("actionLink", "trip/manager/edit.do");
		return result;
	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Collection<Trip> publishedTrips;
		Boolean permission;
		Manager principal;

		publishedTrips = this.tripService.findAllPublishedTrips();

		principal = this.managerService.findByPrincipal();

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		permission = (!publishedTrips.contains(trip)) && principal.getId() == trip.getManager().getId();

		result = this.createEditModelAndView(trip);
		result.addObject("cancel", false);
		result.addObject("permission", permission);
		result.addObject("actionLink", "trip/manager/edit.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(trip);
			result.addObject("cancel", false);
			result.addObject("permission", true);
		} else
			try {
				this.tripService.save(trip);

				result = new ModelAndView("redirect:list.do");
				result.addObject("permission", true);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
				result.addObject("cancel", false);
				result.addObject("permission", true);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;

		try {
			this.tripService.delete(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(trip, "trip.commit.error");
			result.addObject("permission", true);

		}

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int tripId) {
		final ModelAndView result;
		Trip trip;
		Collection<Trip> pendingTrips;
		Boolean permission;
		Manager principal;

		principal = this.managerService.findByPrincipal();

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		pendingTrips = this.tripService.findAllPendingTrips();

		permission = pendingTrips.contains(trip) && trip.getManager().getId() == principal.getId();

		result = this.createEditModelAndView(trip);
		result.addObject("cancel", true);
		result.addObject("permission", permission);
		result.addObject("actionLink", "trip/manager/cancel.do");
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView reject(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(trip);
			result.addObject("cancel", true);
		} else
			try {
				this.tripService.cancel(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
				result.addObject("cancel", true);
				result.addObject("permission", true);
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String message) {
		final ModelAndView result;
		Collection<LegalText> legalTexts;
		Collection<Category> categories;
		Collection<Tag> tags;
		Collection<Ranger> rangers;

		legalTexts = this.legalTextService.findAllFinalMode();
		categories = this.categoryService.findAll();
		tags = this.tagService.findAll();
		rangers = this.rangerService.findAll();

		result = new ModelAndView("trip/edit");
		result.addObject("trip", trip);
		result.addObject("legalTexts", legalTexts);
		result.addObject("categories", categories);
		result.addObject("tags", tags);
		result.addObject("rangers", rangers);
		result.addObject("message", message);

		return result;
	}

}
