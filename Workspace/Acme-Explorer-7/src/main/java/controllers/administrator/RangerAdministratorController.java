
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
import services.RangerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/administrator")
public class RangerAdministratorController extends AbstractController {

	// Services

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private ActorService			actorService;


	// Constructors

	public RangerAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Ranger ranger;

		ranger = this.rangerService.create();
		result = this.createEditModelAndView(ranger);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				Collection<Actor> actors;
				actors = this.actorService.findAllMinusPrincipal();
				result = new ModelAndView("actor/list");
				result.addObject("actors", actors);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "ranger.commit.error");

			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Ranger ranger) {
		ModelAndView result;

		result = this.createEditModelAndView(ranger, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Ranger ranger, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("ranger/edit");
		result.addObject("ranger", ranger);
		result.addObject("actionURI", "ranger/administrator/edit.do");
		result.addObject("redirectURI", "actor/administrator/list.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}
