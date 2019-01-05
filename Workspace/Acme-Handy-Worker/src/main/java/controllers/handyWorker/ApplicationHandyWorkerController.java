package controllers.handyWorker;

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

import services.HandyWorkerService;

import controllers.AbstractController;
import domain.Application;

import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyWorker")
public class ApplicationHandyWorkerController extends AbstractController{

	// Services


	@Autowired
	private HandyWorkerService handyWorkerService;

	// Services
	
	@Autowired
	private ApplicationService	applicationService;

	// Constructors

	public ApplicationHandyWorkerController() {
		super();
	}
	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int hwId) {
		final ModelAndView result;
		final Collection<Application> applications;
		//como vamos a mostrar las solicitudes , creo q se puede hacer en una vista todos los estados y si se acepta que salga un boton en la vista
		//Por cada estado con un if q se muestre la tabla de diferentes formas
		applications=this.applicationService.findAllApplicationsByHandyWorker(hwId);
		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyWorker/list.do");
		return result;	

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Application application;
		
		//task = this.fixUpTaskService.findOne(fixUpTaskId);
		//me hace falta un metodo que compruebe si he hecho una application de una fixUpTask para que no me deje solcitarla dos veces
		//realmente puede hacerse dos solicitudes diferentes para una fixs supongo


		application = this.applicationService.create();
		result = this.createEditModelAndView(application);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);

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

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;
		boolean permission = false;
		final HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();

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

