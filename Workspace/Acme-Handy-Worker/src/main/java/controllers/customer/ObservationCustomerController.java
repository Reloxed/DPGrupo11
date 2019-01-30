package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FixUpTaskService;
import services.ObservationService;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.Observation;


@Controller
@RequestMapping("/observation/customer")
public class ObservationCustomerController {

	//Services --------------------------------------------
	
	@Autowired
	private ObservationService observationService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FixUpTaskService fixService;
	//Constructor -----------------------------------
	
	public ObservationCustomerController(){
		
		super();
	}
	
	//Create --------------------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		final ModelAndView result;
		Observation observation;
		
		observation = this.observationService.create();
		
		result = this.createEditModelAndView(observation);
		
		return result;
		
		
	}
	
	//List ---------------------------------------------------------
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;
		final Collection<Observation> observations;
		
		observations = this.observationService.findAll();
		Assert.notNull(observations);
		
		result = new ModelAndView("observation/list");
		result.addObject("observations", observations);
		result.addObject("requestURI", "observation/customer/list.do");
		
		return result;
	}
	
	//Display --------------------------------------------------------
	
	@RequestMapping(value="/display", method= RequestMethod.GET)
	public ModelAndView display(@RequestParam final int observationId){
		final ModelAndView result;
		final Observation observation;
		
		observation = this.observationService.findOne(observationId);
		Assert.notNull(observation);
		
		result = new ModelAndView("observation/display");
		result.addObject("observation", observation);
		result.addObject("requestURI", "observation/customer/display.do");
		
		return result;
	}
	
	
	//Edition ----------------------------------------------------
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int observationId){
		ModelAndView result;
		Observation observation;
		
		observation = this.observationService.findOne(observationId);
		Assert.notNull(observation);
		
		result = this.createEditModelAndView(observation);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="saveDraft")
	public ModelAndView saveDraft(@Valid final Observation observation, final BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(observation);
		}else{
			try{
				this.observationService.save(observation, false);
				result = new ModelAndView("redirect:/observation/customer/list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(observation, "message.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="saveFinal")
	public ModelAndView saveFinal(@Valid final Observation observation, final BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(observation);
		}else{
			try{
				this.observationService.save(observation, true);
				result = new ModelAndView("redirect:/observation/customer/list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(observation, "message.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid final Observation observation, final BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(observation);
		}else{
			try{
				this.observationService.delete(observation);
				result = new ModelAndView("redirect:/observation/customer/list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(observation, "message.commit.error");
			}
		}
		
		return result;
	}
	
	//Ancillary methods ------------------------------------------------
	
	protected ModelAndView createEditModelAndView(final Observation observation){
		ModelAndView result;
		
		result = this.createEditModelAndView(observation, null);
		
		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(final Observation observation, String messageCode){
		ModelAndView result;
		Collection<FixUpTask> fixs;
		Actor principal;
		boolean possible = false;
		boolean isFinal = false;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		if(principal instanceof Customer){
			possible = true;
		}
		
		fixs = this.fixService.findAll();
		Assert.notNull(fixs);
		
		isFinal = observation.getIsFinal();
		Assert.notNull(isFinal);
		
		result = new ModelAndView("observation/edit");
		result.addObject("message", messageCode);
		result.addObject("requestURI", "observation/customer/edit.do");
		result.addObject("fixs", fixs);
		result.addObject("possible", possible);
		result.addObject("isFinal", isFinal);
		
		return result;
		
	}
	
}
