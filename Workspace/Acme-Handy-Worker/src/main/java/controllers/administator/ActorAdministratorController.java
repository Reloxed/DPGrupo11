package controllers.administator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.FinderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private FinderService finderService;

	// Constructors

	public ActorAdministratorController() {
		super();
	}

	// Lists

	@RequestMapping(value = "/list-suspicious-actors", method = RequestMethod.GET)
	public ModelAndView listSuspiciousActors() {
		ModelAndView res;
		Collection<Actor> suspiciousActors;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

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
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

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
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

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
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

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

	// Delete expired finders
	@RequestMapping(value = "/delete-expired-finders", method = RequestMethod.GET)
	public ModelAndView deleteExpired() {
		ModelAndView res;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		this.finderService.deleteExpiredFinders();

		res = new ModelAndView("actor/display");
		res.addObject("actor", principal);

		return res;

	}
}
