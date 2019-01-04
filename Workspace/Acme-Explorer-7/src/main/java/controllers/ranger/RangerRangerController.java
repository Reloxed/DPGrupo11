
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.RangerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/ranger")
public class RangerRangerController extends AbstractController {

	// Services

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public RangerRangerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rangerId) {
		ModelAndView result;
		Ranger ranger;

		ranger = this.rangerService.findOne(rangerId);
		Assert.notNull(ranger);

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
				Ranger saved;
				Actor actor;
				saved = this.rangerService.save(ranger);

				result = new ModelAndView("actor/display");
				result.addObject("ranger", saved);
				actor = saved;
				result.addObject("actor", actor);

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
		boolean permission = false;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();

		if (principal.getId() == ranger.getId())
			permission = true;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("ranger/edit");

		result.addObject("ranger", ranger);
		result.addObject("actionURI", "ranger/ranger/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
