
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import services.TripService;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services

	@Autowired
	private TripService			tripService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors

	public TripController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Trip> trips;
		final Collection<Trip> pendingTrips;
		final Collection<Trip> auditableTrips;
		Boolean searching;

		auditableTrips = this.tripService.findAllAuditableTrips();
		pendingTrips = this.tripService.findAllPendingTrips();
		trips = this.tripService.findAllPublishedTrips();
		searching = false;

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("pendingTrips", pendingTrips);
		result.addObject("auditableTrips", auditableTrips);
		result.addObject("searching", searching);
		result.addObject("principal", "OTHER");
		result.addObject("requestURI", "trip/list.do");

		return result;
	}

	// Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView result;
		final Trip trip;
		Sponsorship sponsorship;
		Boolean permission;
		Date currentDate;

		trip = this.tripService.findOne(tripId);
		sponsorship = this.sponsorshipService.getRandomSponsorship(trip);

		currentDate = new Date(System.currentTimeMillis() - 1);
		permission = trip.getPublicationDate().before(currentDate);

		result = new ModelAndView("trip/display");

		result.addObject("trip", trip);
		result.addObject("randomSponsorship", sponsorship);
		result.addObject("permission", permission);

		return result;

	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("keyword") final String keyword) {
		ModelAndView result;
		final Collection<Trip> trips;
		final Collection<Trip> results;
		final Collection<Trip> pendingTrips;
		final Collection<Trip> auditableTrips;
		Boolean searching;

		results = this.tripService.findBySingleKey(keyword);
		trips = this.tripService.findAllPublishedTrips();
		trips.retainAll(results);

		auditableTrips = this.tripService.findAllAuditableTrips();
		pendingTrips = this.tripService.findAllPendingTrips();

		searching = true;

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("pendingTrips", pendingTrips);
		result.addObject("auditableTrips", auditableTrips);
		result.addObject("searching", searching);
		result.addObject("principal", "OTHER");
		result.addObject("requestURI", "trip/list.do");

		return result;

	}

}
