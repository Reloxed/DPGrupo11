
package controllers.explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ExplorerService;
import services.TripService;
import controllers.AbstractController;
import domain.Application;
import domain.Explorer;
import domain.Trip;

@Controller
@RequestMapping("/application/explorer")
public class ApplicationExplorerController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private ExplorerService		explorerService;


	// Constructors

	public ApplicationExplorerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;
		final Collection<Application> cancellableApplications;
		final Collection<Trip> startingSoonTrips;

		applications = this.applicationService.findByPrincipal();
		cancellableApplications = this.applicationService.findCancellableApplicationsByPrincipal();
		startingSoonTrips = this.tripService.findAllStartingSoonTrips();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("cancellableApplications", cancellableApplications);
		result.addObject("startingSoonTrips", startingSoonTrips);
		result.addObject("requestURI", "application/explorer/list.do");

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"applicationStatus"
	})
	public ModelAndView listByStatus(@RequestParam final int applicationStatus) {
		final ModelAndView result;
		final Collection<Application> cancellableApplications;
		Map<String, List<Application>> groupedApplications;
		final Collection<Application> applications;
		final Collection<Trip> startingSoonTrips;

		cancellableApplications = this.applicationService.findCancellableApplicationsByPrincipal();
		startingSoonTrips = this.tripService.findAllStartingSoonTrips();

		groupedApplications = this.applicationService.groupByStatus();

		if (applicationStatus == 0)
			applications = this.applicationService.findByPrincipal();
		else if (applicationStatus == 1)
			applications = new ArrayList<Application>(groupedApplications.get("ACCEPTED"));
		else if (applicationStatus == 2)
			applications = new ArrayList<Application>(groupedApplications.get("PENDING"));
		else if (applicationStatus == 3)
			applications = new ArrayList<Application>(groupedApplications.get("DUE"));
		else if (applicationStatus == 4)
			applications = new ArrayList<Application>(groupedApplications.get("REJECTED"));
		else if (applicationStatus == 5)
			applications = new ArrayList<Application>(groupedApplications.get("CANCELLED"));
		else
			applications = null;

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("cancellableApplications", cancellableApplications);
		result.addObject("startingSoonTrips", startingSoonTrips);
		result.addObject("requestURI", "application/explorer/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Application application;
		Trip trip;
		Collection<Trip> pendingTrips;

		pendingTrips = this.tripService.findAllPendingTrips();

		trip = this.tripService.findOne(tripId);
		if (pendingTrips.contains(trip)) {

			application = this.applicationService.create();

			application.setTrip(trip);
			application = this.applicationService.save(application);
			Assert.notNull(application);
		}
		result = new ModelAndView("redirect:list.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);
		result.addObject("comment", true);
		result.addObject("actionURI", "application/explorer/edit.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Collection<Application> cancellableApplications;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		cancellableApplications = this.applicationService.findCancellableApplicationsByPrincipal();

		if (cancellableApplications.contains(application))
			this.applicationService.cancel(application);
		result = new ModelAndView("redirect:list.do");

		return result;

	}

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);
		result.addObject("comment", false);
		result.addObject("actionURI", "application/explorer/pay.do");
		return result;
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST, params = "save")
	public ModelAndView pay(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.accept(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;
		boolean permission = false;
		final Explorer principal;

		principal = this.explorerService.findByPrincipal();

		if (application.getId() == 0)
			permission = true;
		else if (application.getApplicant().getId() == principal.getId())
			permission = true;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("permission", permission);
		result.addObject("message", message);
		return result;
	}
}
