
package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Sponsor;

@Controller
@RequestMapping("/socialIdentity/actor")
public class SocialIdentityActorController extends AbstractController {

	// Services

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private ActorService			actorService;


	// Constructors

	public SocialIdentityActorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.create();
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIdentityId) {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.findOne(socialIdentityId);
		Assert.notNull(socialIdentity);
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialIdentityService.save(socialIdentity);
				Actor principal;

				principal = this.actorService.findByPrincipal();

				result = new ModelAndView("actor/display");
				result.addObject("actor", principal);

				if (principal instanceof Explorer) {
					Explorer explorer;
					explorer = (Explorer) principal;
					result.addObject("explorer", explorer);
				} else if (principal instanceof Manager) {
					Manager manager;
					manager = (Manager) principal;
					result.addObject("manager", manager);
				} else if (principal instanceof Ranger) {
					Ranger ranger;
					ranger = (Ranger) principal;
					result.addObject("ranger", ranger);
				} else if (principal instanceof Auditor) {
					Auditor auditor;
					auditor = (Auditor) principal;
					result.addObject("auditor", auditor);
				} else if (principal instanceof Sponsor) {
					Sponsor sponsor;
					sponsor = (Sponsor) principal;
					result.addObject("sponsor", sponsor);
				} else {
					Administrator administrator;
					administrator = (Administrator) principal;
					result.addObject("administrator", administrator);
				}

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialIdentityService.delete(socialIdentity);
			Actor principal;

			principal = this.actorService.findByPrincipal();

			result = new ModelAndView("actor/display");
			result.addObject("actor", principal);

			if (principal instanceof Explorer) {
				Explorer explorer;
				explorer = (Explorer) principal;
				result.addObject("explorer", explorer);
			} else if (principal instanceof Manager) {
				Manager manager;
				manager = (Manager) principal;
				result.addObject("manager", manager);
			} else if (principal instanceof Ranger) {
				Ranger ranger;
				ranger = (Ranger) principal;
				result.addObject("ranger", ranger);
			} else if (principal instanceof Auditor) {
				Auditor auditor;
				auditor = (Auditor) principal;
				result.addObject("auditor", auditor);
			} else if (principal instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = (Sponsor) principal;
				result.addObject("sponsor", sponsor);
			} else {
				Administrator administrator;
				administrator = (Administrator) principal;
				result.addObject("administrator", administrator);
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity) {
		ModelAndView result;

		result = this.createEditModelAndView(socialIdentity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String message) {
		ModelAndView result;
		boolean permission = false;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		if (socialIdentity.getId() == 0)
			permission = true;
		else
			for (final SocialIdentity si : principal.getSocialIdentities())
				if (si.getId() == socialIdentity.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("permission", permission);

		result.addObject("message", message);

		return result;
	}

}
