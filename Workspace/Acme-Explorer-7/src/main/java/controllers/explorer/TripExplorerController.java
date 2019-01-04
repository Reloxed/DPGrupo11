
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.FinderService;
import services.TripService;
import controllers.AbstractController;
import domain.Finder;
import domain.Trip;

@Controller
@RequestMapping("/trip/explorer")
public class TripExplorerController extends AbstractController {

	// Services

	@Autowired
	private TripService				tripService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public TripExplorerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Trip> trips;
		Finder finder;
		Boolean searching;
		int cacheTime;

		trips = this.tripService.findAllPublishedTrips();
		searching = false;

		cacheTime = this.customisationService.find().getFinderCacheTime();

		this.finderService.deleteOldFinders(cacheTime);

		finder = this.finderService.create();

		result = this.createListModelAndView(finder);

		result.addObject("searching", searching);
		result.addObject("trips", trips);
		return result;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(final int finderId) {
		ModelAndView result;
		Collection<Trip> results;
		Boolean searching;
		Finder finder;

		finder = this.finderService.findOne(finderId);

		results = finder.getResults();
		searching = true;
		finder = this.finderService.create();
		result = this.createListModelAndView(finder);
		result.addObject("trips", results);
		result.addObject("searching", searching);

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Collection<Trip> results;
		Collection<Trip> trips;
		Boolean searching;
		Finder saved;
		int maxResults;

		if (binding.hasErrors()) {
			trips = this.tripService.findAllPublishedTrips();
			searching = false;
			result = this.createListModelAndView(finder);
			result.addObject("trips", trips);
			result.addObject("searching", searching);
		} else
			try {

				maxResults = this.customisationService.find().getFinderMaxResults();

				saved = this.finderService.save(finder);
				results = this.finderService.search(saved, maxResults);
				searching = true;
				result = this.createListModelAndView(finder);
				result.addObject("trips", results);
				result.addObject("searching", searching);

			} catch (final Throwable oops) {
				trips = this.tripService.findAllPublishedTrips();
				searching = false;
				result = this.createListModelAndView(finder, "trip.commit.error");
				result.addObject("trips", trips);
				result.addObject("searching", searching);

			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createListModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createListModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createListModelAndView(final Finder finder, final String message) {
		final ModelAndView result;

		final Collection<Trip> pendingTrips;
		final Collection<Trip> paidPassedTrips;

		pendingTrips = this.tripService.findAllPendingTrips();
		paidPassedTrips = this.tripService.findAllPassedTripsAcceptedApplicationPrincipal();

		result = new ModelAndView("trip/list");

		result.addObject("pendingTrips", pendingTrips);
		result.addObject("paidPassedTrips", paidPassedTrips);

		result.addObject("finder", finder);
		result.addObject("requestURI", "trip/explorer/list.do");
		result.addObject("principal", "EXPLORER");
		result.addObject("message", message);

		return result;
	}

}
