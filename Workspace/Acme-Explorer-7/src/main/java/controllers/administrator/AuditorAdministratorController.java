
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private ActorService			actorService;


	// Constructors

	public AuditorAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Auditor auditor;

		auditor = this.auditorService.create();
		result = this.createEditModelAndView(auditor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				Collection<Actor> actors;
				actors = this.actorService.findAllMinusPrincipal();
				result = new ModelAndView("actor/list");
				result.addObject("actors", actors);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditor, "auditor.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Auditor auditor) {
		ModelAndView result;

		result = this.createEditModelAndView(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("actionURI", "auditor/administrator/edit.do");
		result.addObject("redirectURI", "actor/administrator/list.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}
