
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/auditor")
public class AuditorAuditorController extends AbstractController {

	// Services

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public AuditorAuditorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditorId) {
		ModelAndView result;
		Auditor auditor;

		auditor = this.auditorService.findOne(auditorId);
		Assert.notNull(auditor);

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
				Auditor saved;
				Actor actor;
				saved = this.auditorService.save(auditor);

				result = new ModelAndView("actor/display");
				result.addObject("auditor", saved);
				actor = saved;
				result.addObject("actor", actor);

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
		boolean permission = false;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();

		if (principal.getId() == auditor.getId())
			permission = true;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("auditor/edit");

		result.addObject("auditor", auditor);
		result.addObject("actionURI", "auditor/auditor/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("countryCode", countryCode);
		result.addObject("message", message);
		result.addObject("permission", permission);

		return result;
	}

}
