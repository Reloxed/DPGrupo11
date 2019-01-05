/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.referee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RefereeService;
import controllers.AbstractController;
import domain.Referee;

@Controller
@RequestMapping("/referee/referee")
public class RefereeAdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public RefereeAdministratorController() {
		super();
	}

	// Services

	@Autowired
	private RefereeService refereeService;

	// Creating an referee

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Referee referee;

		referee = this.refereeService.create();
		res = this.createEditModelAndView(referee);
		return res;
	}

	// Editing an admin
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "refereeID")
	public ModelAndView edit(@RequestParam int refereeID) {
		ModelAndView res;
		Referee toEdit;

		toEdit = this.refereeService.findOne(refereeID);
		Assert.notNull(toEdit);

		res = this.createEditModelAndView(toEdit);

		return res;
	}

	// Create edit ModelAndView para administrators
	// -----------------------------------------------
	protected ModelAndView createEditModelAndView(Referee referee) {
		ModelAndView res;

		res = this.createEditModelAndView(referee, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Referee referee,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("referee/edit");
		res.addObject("referee", referee);
		res.addObject("message", messageCode);
		return res;
	}

	// Saving an admin
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Referee referee, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(referee);
		} else {
			try {
				Referee saved;
				saved = this.refereeService.save(referee);
				res = new ModelAndView("redirect:display.do?actorID="
						+ saved.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(referee,
						"referee.commit.error");
			}
		}
		return res;
	}

}
