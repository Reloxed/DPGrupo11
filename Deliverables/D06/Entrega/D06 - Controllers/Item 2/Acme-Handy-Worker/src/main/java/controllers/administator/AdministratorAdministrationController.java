/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministrationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorAdministrationController() {
		super();
	}

	// Services

	@Autowired
	private AdministratorService administratorService;

	// Creating an admin

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Administrator administrator;

		administrator = this.administratorService.create();
		res = this.createEditModelAndView(administrator);
		return res;
	}

	// Editing an admin
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "administratorID")
	public ModelAndView edit(@RequestParam int administratorID) {
		ModelAndView res;
		Administrator toEdit;

		toEdit = this.administratorService.findOne(administratorID);
		Assert.notNull(toEdit);

		res = this.createEditModelAndView(toEdit);

		return res;
	}

	// Create edit ModelAndView para administrators
	// -----------------------------------------------
	protected ModelAndView createEditModelAndView(Administrator administrator) {
		ModelAndView res;

		res = this.createEditModelAndView(administrator, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Administrator administrator,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("administrator/edit");
		res.addObject("administrator", administrator);
		res.addObject("message", messageCode);
		return res;
	}

	// Saving an admin
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Administrator administrator,
			BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(administrator);
		} else {
			try {
				Administrator saved;
				saved = this.administratorService.save(administrator);
				res = new ModelAndView("redirect:/actor/display.do?actorID="
						+ saved.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(administrator,
						"administrator.commit.error");
			}
		}
		return res;
	}

}
