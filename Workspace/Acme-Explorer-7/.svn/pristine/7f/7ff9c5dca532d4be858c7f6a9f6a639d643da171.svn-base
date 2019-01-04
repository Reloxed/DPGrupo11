
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
import services.CustomisationService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Manager;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private ActorService			actorService;


	// Constructors

	public ManagerAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.create();
		result = this.createEditModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				Collection<Actor> actors;
				actors = this.actorService.findAllMinusPrincipal();
				result = new ModelAndView("actor/list");
				result.addObject("actors", actors);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("actionURI", "manager/administrator/edit.do");
		result.addObject("redirectURI", "actor/administrator/list.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);
		result.addObject("message", message);

		return result;
	}

}
