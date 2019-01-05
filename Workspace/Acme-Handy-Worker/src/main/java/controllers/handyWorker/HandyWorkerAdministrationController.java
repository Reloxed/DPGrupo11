/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import controllers.AbstractController;
import domain.HandyWorker;

@Controller
@RequestMapping("/handyworker/handyworker")
public class HandyWorkerAdministrationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public HandyWorkerAdministrationController() {
		super();
	}

	// Services

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Creating an admin

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();
		res = this.createEditModelAndView(handyWorker);
		return res;
	}

	// Editing an admin
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "handyworkerID")
	public ModelAndView edit(@RequestParam int handyworkerID) {
		ModelAndView res;
		HandyWorker toEdit;

		toEdit = this.handyWorkerService.findOne(handyworkerID);
		Assert.notNull(toEdit);

		res = this.createEditModelAndView(toEdit);

		return res;
	}

	// Create edit ModelAndView para administrators
	// -----------------------------------------------
	protected ModelAndView createEditModelAndView(HandyWorker handyWorker) {
		ModelAndView res;

		res = this.createEditModelAndView(handyWorker, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(HandyWorker handyWorker,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("handyWorker/edit");
		res.addObject("handyWorker", handyWorker);
		res.addObject("message", messageCode);
		return res;
	}

	// Saving an admin
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid HandyWorker handyWorker,
			BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(handyWorker);
		} else {
			try {
				HandyWorker saved;
				saved = this.handyWorkerService.save(handyWorker);
				res = new ModelAndView("redirect:/actor/display.do?actorID="
						+ saved.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(handyWorker,
						"handyworker.commit.error");
			}
		}
		return res;
	}

}
