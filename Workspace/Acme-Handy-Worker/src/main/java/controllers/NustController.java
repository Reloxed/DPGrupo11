package controllers;



import java.util.Collection;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Nust;



import services.NustService;

@Controller
@RequestMapping("/nust")
public class NustController extends AbstractController{


	// Services
	@Autowired
	private NustService nustService;



	// Constructor
	public NustController() {
		super();
	}



	// listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Locale locale) {
		ModelAndView result;
		Collection<Nust> nusts;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		Collection<Nust> published;
		
		
		language = locale.getLanguage();
		nusts=this.nustService.findAll();
		published=this.nustService.nustsPublished();
		
		result = new ModelAndView("nust/list");
		result.addObject("nusts",nusts);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		result.addObject("published", published);

		result.addObject("requestUri", "nust/list.do");
		return result;
	}
	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int nustId,final Locale locale) {
		ModelAndView result;
		Nust nust;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";

		language = locale.getLanguage();
		nust=this.nustService.findOne(nustId);
		result = new ModelAndView("nust/display");

		result.addObject("nust",nust);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		result.addObject("requestUri", "nust/display.do");
		return result;

	}

}
