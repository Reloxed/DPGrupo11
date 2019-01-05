/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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

import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/sponsor")
public class SponsorAdministrationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SponsorAdministrationController() {
		super();
	}

	// Services

	@Autowired
	private SponsorService sponsorService;

	// Creating an admin

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Sponsor sponsor;

		sponsor = this.sponsorService.create();
		res = this.createEditModelAndView(sponsor);
		return res;
	}

	// Editing an admin
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "sponsorID")
	public ModelAndView edit(@RequestParam int sponsorID) {
		ModelAndView res;
		Sponsor toEdit;

		toEdit = this.sponsorService.findOne(sponsorID);
		Assert.notNull(toEdit);

		res = this.createEditModelAndView(toEdit);

		return res;
	}

	// Create edit ModelAndView para administrators
	// -----------------------------------------------
	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView res;

		res = this.createEditModelAndView(sponsor, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("sponsor/edit");
		res.addObject("sponsor", sponsor);
		res.addObject("message", messageCode);
		return res;
	}

	// Saving an admin
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(sponsor);
		} else {
			try {
				Sponsor saved;
				saved = this.sponsorService.save(sponsor);
				res = new ModelAndView("redirect:display.do?actorID="
						+ saved.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(sponsor,
						"sponsor.commit.error");
			}
		}
		return res;
	}

}
