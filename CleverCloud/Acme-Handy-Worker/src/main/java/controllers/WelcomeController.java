/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	SystemConfigurationService systemConfigurationService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(
			@RequestParam(required = false, defaultValue = "John Doe") final String name,
			Locale locale) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Map<String, String> welcomeMessage;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";

		language = locale.getLanguage();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		// welcomeMessage = this.systemConfigurationService
		// .findMySystemConfiguration().getWelcomeMessage();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		// result.addObject("welcomeMessage", welcomeMessage);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);

		return result;
	}
}
