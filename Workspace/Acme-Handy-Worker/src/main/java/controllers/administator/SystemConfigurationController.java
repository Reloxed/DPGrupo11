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

import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/system-configuration/administrator")
public class SystemConfigurationController extends AbstractController {

	// Services

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Constructor

	public SystemConfigurationController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView res;
		SystemConfiguration systemConfiguration;
		String[] spamWords;
		String[] negativeWords;
		String[] positiveWords;
		String[] creditCardMakes;

		systemConfiguration = this.systemConfigurationService
				.findMySystemConfiguration();

		spamWords = systemConfiguration.getSpamWords().split(",");
		negativeWords = systemConfiguration.getNegativeWords().split(",");
		positiveWords = systemConfiguration.getPositiveWords().split(",");
		creditCardMakes = systemConfiguration.getListCreditCardMakes().split(
				",");

		res = new ModelAndView("systemConfiguration/display");
		res.addObject("requestURI",
				"system-configuration/administrator/display.do");
		res.addObject("systemConfiguration", systemConfiguration);
		res.addObject("spamWords", spamWords);
		res.addObject("negativeWords", negativeWords);
		res.addObject("positiveWords", positiveWords);
		res.addObject("creditCardMakes", creditCardMakes);

		return res;
	}

	// Editing sysConfig

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "systemconfigurationID")
	public ModelAndView edit(@RequestParam int systemconfigurationID) {
		ModelAndView res;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService
				.findOne(systemconfigurationID);
		Assert.notNull(systemConfiguration);
		res = createEditModelAndView(systemConfiguration);

		return res;
	}

	protected ModelAndView createEditModelAndView(
			SystemConfiguration systemConfiguration) {
		ModelAndView res;

		res = createEditModelAndView(systemConfiguration, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(
			SystemConfiguration systemConfiguration, String messageCode) {
		ModelAndView res;

		res = new ModelAndView("systemConfiguration/edit");
		res.addObject("systemConfiguration", systemConfiguration);
		res.addObject("message", messageCode);

		return res;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SystemConfiguration systemConfiguration,
			BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createEditModelAndView(systemConfiguration);
		} else {
			try {
				this.systemConfigurationService.save(systemConfiguration);
				res = new ModelAndView("redirect:display.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(systemConfiguration,
						"system.commit.error");
			}
		}
		return res;
	}

}
