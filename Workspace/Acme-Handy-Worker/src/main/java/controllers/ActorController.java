package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController {

	// Services

	@Autowired
	private ActorService actorService;

	// Constructors

	public ActorController() {
		super();
	}

	// Display with no parameters
	@RequestMapping(value = "/display")
	public ModelAndView display() {
		final ModelAndView res;
		Actor actor;

		res = new ModelAndView("actor/display");
		res.addObject("requestURI", "actor/display.do");

		try {
			actor = this.actorService.findByPrincipal();
			res.addObject("actor", actor);
		} catch (RuntimeException oops) {
			actor = null;
		}

		if (actor == null) {
			res.addObject("actor", null);
		} else {
			if (actor instanceof Customer) {
				Customer customer;
				customer = (Customer) actor;
				res.addObject("customer", customer);
			} else if (actor instanceof Referee) {
				Referee referee;
				referee = (Referee) actor;
				res.addObject("referee", referee);
			} else if (actor instanceof HandyWorker) {
				HandyWorker handyWorker;
				handyWorker = (HandyWorker) actor;
				res.addObject("handyWorker", handyWorker);
			} else if (actor instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = (Sponsor) actor;
				res.addObject("sponsor", sponsor);
			} else {
				Administrator administrator;
				administrator = (Administrator) actor;
				res.addObject("administrator", administrator);
			}

		}
		return res;
	}

	// Display with "actorID" as parameter
	@RequestMapping(value = "/display", params = "actorID")
	public ModelAndView display(@RequestParam int actorID) {
		final ModelAndView res;
		Actor actor;

		actor = this.actorService.findOne(actorID);

		res = new ModelAndView("actor/display");
		res.addObject("requestURI", "actor/display.do");
		res.addObject("actor", actor);

		if (actor != null) {
			if (actor instanceof Customer) {
				Customer customer;
				customer = (Customer) actor;
				res.addObject("customer", customer);
			} else if (actor instanceof Referee) {
				Referee referee;
				referee = (Referee) actor;
				res.addObject("referee", referee);
			} else if (actor instanceof HandyWorker) {
				HandyWorker handyWorker;
				handyWorker = (HandyWorker) actor;
				res.addObject("handyWorker", handyWorker);
			} else if (actor instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = (Sponsor) actor;
				res.addObject("sponsor", sponsor);
			} else {
				Administrator administrator;
				administrator = (Administrator) actor;
				res.addObject("administrator", administrator);
			}

		}
		return res;
	}
}
