
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public AdministratorAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndView(administrator);
		result.addObject("permission", true);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int administratorId) {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findOne(administratorId);
		Assert.notNull(administrator);

		result = this.createEditModelAndView(administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(administrator);
			result.addObject("permission", true);
		} else
			try {
				Administrator saved;
				Actor actor;
				saved = this.administratorService.save(administrator);

				result = new ModelAndView("actor/display");
				result.addObject("administrator", saved);
				actor = saved;
				result.addObject("actor", actor);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "administrator.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;
		String countryCode;
		Boolean permission;
		Administrator principal;

		permission = false;

		principal = this.administratorService.findByPrincipal();

		if (principal.getId() == administrator.getId())
			permission = true;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		result.addObject("actionURI", "administrator/administrator/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("countryCode", countryCode);
		result.addObject("message", message);
		result.addObject("permission", permission);

		return result;
	}

}
