package controllers.administator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping(value = "/display")
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
}
