
package controllers.explorer;

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
import services.ExplorerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Explorer;

@Controller
@RequestMapping("/explorer/explorer")
public class ExplorerExplorerController extends AbstractController {

	// Services

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public ExplorerExplorerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int explorerId) {
		ModelAndView result;
		Explorer explorer;

		explorer = this.explorerService.findOne(explorerId);
		Assert.notNull(explorer);

		result = this.createEditModelAndView(explorer);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				Explorer saved;
				Actor actor;
				saved = this.explorerService.save(explorer);

				result = new ModelAndView("actor/display");
				result.addObject("explorer", saved);
				actor = saved;
				result.addObject("actor", actor);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "explorer.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer explorer) {
		ModelAndView result;

		result = this.createEditModelAndView(explorer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {
		ModelAndView result;
		String countryCode;
		Boolean permission;
		countryCode = this.customisationService.find().getCountryCode();
		Explorer principal;

		principal = this.explorerService.findByPrincipal();

		permission = false;
		if (principal.getId() == explorer.getId())
			permission = true;

		result = new ModelAndView("explorer/edit");

		result.addObject("explorer", explorer);
		result.addObject("actionURI", "explorer/explorer/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
