
package controllers.sponsor;

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
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/sponsor")
public class SponsorSponsorController extends AbstractController {

	// Services

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public SponsorSponsorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorId) {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(sponsorId);
		Assert.notNull(sponsor);

		result = this.createEditModelAndView(sponsor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				Sponsor saved;
				Actor actor;
				saved = this.sponsorService.save(sponsor);

				result = new ModelAndView("actor/display");
				result.addObject("sponsor", saved);
				actor = saved;
				result.addObject("actor", actor);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {
		ModelAndView result;
		String countryCode;
		boolean permission = false;
		Sponsor principal;

		principal = this.sponsorService.findByPrincipal();

		if (principal.getId() == sponsor.getId())
			permission = true;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("sponsor/edit");

		result.addObject("sponsor", sponsor);
		result.addObject("actionURI", "sponsor/sponsor/edit.do");
		result.addObject("redirectURI", "actor/display.do");
		result.addObject("countryCode", countryCode);
		result.addObject("message", message);
		result.addObject("permission", permission);

		return result;
	}

}
