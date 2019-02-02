package controllers.actor;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ObservationService;
import controllers.AbstractController;
import domain.Observation;

@Controller
@RequestMapping("/observation/actor")
public class ObservationActorController extends AbstractController{

	//Services --------------------------------------------------
	
	@Autowired
	private ObservationService observationService;
	
	
	//Constructor ----------------------------------------------------
	
	public ObservationActorController(){
		
		super();
	}
	
	//List --------------------------------------------------------
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listActor(final Locale locale){	
		
		final ModelAndView result;	
		Collection<Observation> finalObservations;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		
		language = locale.getLanguage();
		
		finalObservations = this.observationService.getFinalObservations();
		Assert.notNull(finalObservations);
		
		result = new ModelAndView("observation/list");
		result.addObject("finalObservations", finalObservations);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		
		return result;
	}
	
	
	//Display ---------------------------------------------------------
	
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int observationId){
		final ModelAndView result;
		Observation observation;
		
		observation = this.observationService.findOne(observationId);
		Assert.notNull(observation);
		
		result = new ModelAndView("observation/display");
		result.addObject("observation", observation);
		
		return result;
	}
}
