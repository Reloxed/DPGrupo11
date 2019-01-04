
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

import services.ApplicationService;
import services.ManagerService;
import services.TripService;
import controllers.AbstractController;
import domain.Application;
import domain.Manager;
import domain.Trip;

@Controller
@RequestMapping("/application/manager")
public class ApplicationManagerController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private ManagerService		managerService;


	// Constructors

	public ApplicationManagerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;
		final Collection<Trip> startingSoonTrips;

		applications = this.applicationService.findByManager();

		startingSoonTrips = this.tripService.findAllStartingSoonTrips();
		result = new ModelAndView("application/list");

		result.addObject("applications", applications);
		result.addObject("startingSoonTrips", startingSoonTrips);
		result.addObject("requestURI", "application/manager/list.do");

		return result;

	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Manager principal;

		principal = this.managerService.findByPrincipal();

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		if (application.getTrip().getManager().getId() == principal.getId() && application.getStatus().equals("PENDING"))
			this.applicationService.expect(application);
		result = new ModelAndView("redirect:list.do");

		return result;

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;
		Manager principal;

		principal = this.managerService.findByPrincipal();

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		if (application.getTrip().getManager().getId() == principal.getId() && application.getStatus().equals("PENDING"))
			result = this.createEditModelAndView(application);
		else
			result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView reject(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.reject(application);
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
		Manager principal;

		principal = this.managerService.findByPrincipal();

		if (application.getTrip().getManager().getId() == principal.getId())
			permission = true;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("permission", permission);
		result.addObject("message", message);
		return result;
	}

}
