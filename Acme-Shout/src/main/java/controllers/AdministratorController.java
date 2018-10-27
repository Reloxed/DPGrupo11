/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ShoutService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ShoutService	shoutService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/shouts-counter", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		Map<String, Double> statistics;

		statistics = this.shoutService.computeStatistics();

		result = new ModelAndView("administrator/shouts-counter");
		result.addObject("statistics", statistics);

		return result;
	}
	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/shouts-graphics")
	public ModelAndView action2() {
		ModelAndView result;
		Map<String, Double> statistics;

		statistics = this.shoutService.computeStatistics();

		result = new ModelAndView("administrator/shouts-graphics");
		result.addObject("statistics", statistics);

		return result;
	}

}