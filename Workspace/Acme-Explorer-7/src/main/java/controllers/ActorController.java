
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services

	@Autowired
	private ActorService	actorService;


	public ActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display() {
		final ModelAndView result;
		Actor principal;

		result = new ModelAndView("actor/display");

		principal = null;
		try {
			principal = this.actorService.findByPrincipal();
		} catch (final RuntimeException oops) {
		}
		if (principal == null)
			result.addObject("actor", null);
		else {

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

		}

		return result;

	}

	@RequestMapping(value = "/display", params = "actorId")
	public ModelAndView display(@RequestParam final int actorId) {
		final ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);

		if (actor != null)
			if (actor instanceof Explorer) {
				Explorer explorer;
				explorer = (Explorer) actor;
				result.addObject("explorer", explorer);
			} else if (actor instanceof Manager) {
				Manager manager;
				manager = (Manager) actor;
				result.addObject("manager", manager);
			} else if (actor instanceof Ranger) {
				Ranger ranger;
				ranger = (Ranger) actor;
				result.addObject("ranger", ranger);
			} else if (actor instanceof Auditor) {
				Auditor auditor;
				auditor = (Auditor) actor;
				result.addObject("auditor", auditor);
			} else if (actor instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = (Sponsor) actor;
				result.addObject("sponsor", sponsor);
			} else {
				Administrator administrator;
				administrator = (Administrator) actor;
				result.addObject("administrator", administrator);
			}
		return result;

	}

}
