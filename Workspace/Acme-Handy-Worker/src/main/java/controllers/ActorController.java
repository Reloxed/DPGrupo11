package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import services.AdministratorService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController{

	// Services

	@Autowired
	private ActorService actorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors

	public ActorController() {
		super();
	}

	// Display with no parameters
	@RequestMapping(value = "/display")
	public ModelAndView display() {
		ModelAndView res;
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
				customer = this.customerService.findOne(actor.getId());
				res.addObject("customer", customer);
				res.addObject("creditCards", customer.getCreditCards());
			} else if (actor instanceof Referee) {
				Referee referee;
				referee = this.refereeService.findOne(actor.getId());
				res.addObject("referee", referee);
			} else if (actor instanceof HandyWorker) {
				HandyWorker handyWorker;
				handyWorker = this.handyWorkerService.findOne(actor.getId());
				res.addObject("handyWorker", handyWorker);
			} else if (actor instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = this.sponsorService.findOne(actor.getId());
				res.addObject("sponsor", sponsor);
				res.addObject("creditCards", sponsor.getCreditCards());
			} else {
				Administrator administrator;
				administrator = this.administratorService
						.findOne(actor.getId());
				res.addObject("administrator", administrator);
			}

		}
		return res;
	}

	// Display with "actorID" as parameter
	@RequestMapping(value = "/display", params = "actorID")
	public ModelAndView display(@RequestParam int actorID) {
		ModelAndView res;
		Actor actor;

		actor = this.actorService.findOne(actorID);

		res = new ModelAndView("actor/display");
		res.addObject("requestURI", "actor/display.do");
		res.addObject("actor", actor);

		if (actor != null) {
			if (actor instanceof Customer) {
				Customer customer;
				customer = this.customerService.findOne(actor.getId());
				res.addObject("customer", customer);
			} else if (actor instanceof Referee) {
				Referee referee;
				referee = this.refereeService.findOne(actor.getId());
				res.addObject("referee", referee);
			} else if (actor instanceof HandyWorker) {
				HandyWorker handyWorker;
				handyWorker = this.handyWorkerService.findOne(actor.getId());
				res.addObject("handyWorker", handyWorker);
			} else if (actor instanceof Sponsor) {
				Sponsor sponsor;
				sponsor = this.sponsorService.findOne(actor.getId());
				res.addObject("sponsor", sponsor);
			} else {
				Administrator administrator;
				administrator = this.administratorService
						.findOne(actor.getId());
				res.addObject("administrator", administrator);
			}

		}
		return res;
	}

}
