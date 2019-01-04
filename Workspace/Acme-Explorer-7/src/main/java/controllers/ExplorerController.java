
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.ExplorerService;
import domain.Explorer;

@Controller
@RequestMapping("/explorer")
public class ExplorerController extends AbstractController {

	// Services

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors

	public ExplorerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Explorer explorer;

		explorer = this.explorerService.create();
		result = this.createEditModelAndView(explorer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				result = new ModelAndView("welcome/index");

				SimpleDateFormat formatter;
				String moment;
				final String welcomeMessage;

				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());

				welcomeMessage = "welcome.greeting.signUp.explorer";

				result.addObject("welcomeMessage", welcomeMessage);
				result.addObject("moment", moment);
				result.addObject("signUp", true);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "explorer.commit.error");

			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer explorer) {
		ModelAndView result;

		result = this.createEditModelAndView(explorer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("explorer/edit");
		result.addObject("explorer", explorer);
		result.addObject("actionURI", "explorer/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}
