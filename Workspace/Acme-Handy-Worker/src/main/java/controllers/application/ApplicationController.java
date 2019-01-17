package controllers.application;

import java.util.Collection;
import java.util.Locale;

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
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	// Constructors

	public ApplicationController() {
		super();
	}

	// List

	@RequestMapping(value = "/customer,handy-worker/list")
	public ModelAndView list(@RequestParam(required = false) Integer fixUpTaskId) {
		ModelAndView res;
		Collection<Application> applications;

		try {
			HandyWorker principal;

			principal = this.handyWorkerService.findByPrincipal();

			applications = this.applicationService
					.findAllApplicationsByHandyWorker(principal.getId());

			res = new ModelAndView("application/list");
			res.addObject("applications", applications);
			res.addObject("owner", principal);

			return res;

		} catch (Throwable oops) {
			Customer fixUpTaskOwner;
			boolean hasAccepted = false;

			applications = this.applicationService
					.findAllApplicationsByFixUpTask(fixUpTaskId);
			fixUpTaskOwner = this.customerService
					.findCustomerByApplicationId(applications.iterator().next()
							.getId());

			for (Application application : applications) {
				if (application.getStatus().equals("ACCEPTED")) {
					hasAccepted = true;
					break;
				}
			}
			String requestURI = "application/customer,handyworker/list.do?fixUpTaskId="
					+ fixUpTaskId;
			res = new ModelAndView("application/list");
			res.addObject("requestURI", requestURI);
			res.addObject("applications", applications);
			res.addObject("owner", fixUpTaskOwner);
			res.addObject("hasAccepted", hasAccepted);
			res.addObject("fixUpTaskId", fixUpTaskId);

			return res;

		}

	}

	// Accept application

	@RequestMapping(value = "/customer/acceptv")
	public ModelAndView acceptView(@RequestParam int applicationID) {
		ModelAndView res;
		Application toAccept;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		toAccept = this.applicationService.findOne(applicationID);
		res = new ModelAndView("application/accept");
		res.addObject("application", toAccept);
		res.addObject("principal", principal);
		// res.addObject(attributeValue)
		return res;
	}

	@RequestMapping(value = "/customer/acceptb", method = RequestMethod.POST, params = "save")
	public ModelAndView acceptButton(@Valid Application application,
			Locale locale) {
		ModelAndView res;
		String language;

		try {
			Application accepted;
			language = locale.getLanguage();
			this.applicationService.accept(application,
					application.getCreditCard(), language);
			accepted = this.applicationService.findOne(application.getId());
			res = new ModelAndView(
					"redirect:/application/customer,handy-worker/list.do?fixUpTaskId="
							+ accepted.getFixUpTask().getId());
		} catch (Throwable oops) {
			res = this.createEditModelAndViewC(application,
					"administrator.commit.error");
		}
		return res;
	}

	// Reject

	@RequestMapping(value = "/customer/reject")
	public ModelAndView reject(@RequestParam int applicationID, Locale locale) {
		ModelAndView res;
		Application toReject;
		Collection<Application> applications;
		Customer fixUpTaskOwner;
		boolean hasAccepted = false;
		String language;
		
		language = locale.getLanguage();
		
		toReject = this.applicationService.findOne(applicationID);

		this.applicationService.reject(toReject, language);

		applications = this.applicationService
				.findAllApplicationsByFixUpTask(toReject.getFixUpTask().getId());
		fixUpTaskOwner = this.customerService
				.findCustomerByApplicationId(applications.iterator().next()
						.getId());

		for (Application application : applications) {
			if (application.getStatus().equals("ACCEPTED")) {
				hasAccepted = true;
				break;
			}
		}

		res = new ModelAndView("application/list");
		res.addObject("requestURI", "application/customer,handy-worker/list.do");
		res.addObject("applications", applications);
		res.addObject("owner", fixUpTaskOwner);
		res.addObject("hasAccepted", hasAccepted);
		res.addObject("fixUpTaskId", toReject.getFixUpTask().getId());
		return res;
	}

	@RequestMapping(value = "/handy-worker/create", method = RequestMethod.GET, params = "fixUpTaskId")
	public ModelAndView create(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.create();
		FixUpTask fix = this.fixUpTaskService.findOne(fixUpTaskId);
		application.setFixUpTask(fix);

		result = this.createEditModelAndViewHW(application);

		return result;
	}

	@RequestMapping(value = "/handy-worker/edit", method = RequestMethod.GET)
	public ModelAndView editHW(@RequestParam int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		result = createEditModelAndViewHW(application);

		return result;
	}

	@RequestMapping(value = "/handy-worker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHW(@Valid Application application,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndViewHW(application);
		} else {
			try {
				this.applicationService.save(application);
				result = new ModelAndView(
						"redirect:/application/customer,handy-worker/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndViewHW(application,
						"application.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editC(@RequestParam int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		result = createEditModelAndViewC(application);

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveC(@Valid Application application,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndViewC(application);
		} else {
			try {
				this.applicationService.save(application);
				result = new ModelAndView(
						"redirect:/application/customer,handy-worker/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndViewC(application,
						"application.commit.error");
			}
		}
		return result;
	}

	// Create edit ModelAndView para customers
	// -----------------------------------------------

	protected ModelAndView createEditModelAndViewHW(Application application) {
		ModelAndView result;

		result = createEditModelAndViewHW(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewHW(Application application,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/handy-worker/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewC(Application application) {
		ModelAndView result;

		result = createEditModelAndViewHW(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewC(Application application,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/customer/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}
}
