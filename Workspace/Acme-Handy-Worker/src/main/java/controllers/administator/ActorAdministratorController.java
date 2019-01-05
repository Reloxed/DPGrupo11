package controllers.administator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ActorService actorService;

	// Constructors

	public ActorAdministratorController() {
		super();
	}

	// Lists

	@RequestMapping(value = "/list-suspicious-actors", method = RequestMethod.GET)
	public ModelAndView listSuspiciousActors() {
		ModelAndView res;
		Collection<Actor> suspiciousActors;

		suspiciousActors = this.actorService.findActorsBySuspicious();

		res = new ModelAndView("actor/listSuspiciousActors");
		res.addObject("requestURI",
				"actor/administrator/list-suspicious-actors.do");
		res.addObject("suspiciousActors", suspiciousActors);

		return res;
	}

	// Ban actor
	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "actorID")
	public ModelAndView banActor(@RequestParam int actorID) {
		ModelAndView res;
		Actor toBan;

		toBan = this.actorService.findOne(actorID);

		this.actorService.ban(toBan);

		Collection<Actor> suspiciousActors;

		suspiciousActors = this.actorService.findActorsBySuspicious();

		res = new ModelAndView("actor/listSuspiciousActors");
		res.addObject("requestURI",
				"actor/administrator/list-suspicious-actors.do");
		res.addObject("suspiciousActors", suspiciousActors);

		return res;
	}

	// Unban actor
	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "actorID")
	public ModelAndView unbanActor(@RequestParam int actorID) {
		ModelAndView res;
		Actor toUnban;

		toUnban = this.actorService.findOne(actorID);

		this.actorService.unban(toUnban);

		Collection<Actor> suspiciousActors;

		suspiciousActors = this.actorService.findActorsBySuspicious();

		res = new ModelAndView("actor/listSuspiciousActors");
		res.addObject("requestURI",
				"actor/administrator/list-suspicious-actors.do");
		res.addObject("suspiciousActors", suspiciousActors);

		return res;
	}

	// Remove from the list of suspicious actors
	@RequestMapping(value = "/remove-suspicious", method = RequestMethod.GET, params = "actorID")
	public ModelAndView removeFromSuspActors(@RequestParam int actorID) {
		ModelAndView res;
		Actor toRemove;

		toRemove = this.actorService.findOne(actorID);

		this.actorService.removeFromSuspicious(toRemove);

		Collection<Actor> suspiciousActors;

		suspiciousActors = this.actorService.findActorsBySuspicious();

		res = new ModelAndView("actor/listSuspiciousActors");
		res.addObject("requestURI",
				"actor/administrator/list-suspicious-actors.do");
		res.addObject("suspiciousActors", suspiciousActors);

		return res;
	}
}
