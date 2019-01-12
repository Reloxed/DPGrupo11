package controllers.handyWorker;

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

import services.PhaseService;

import controllers.AbstractController;

import domain.Phase;

@Controller
@RequestMapping("/phase/handyWorker")
public class PhaseHandyWorkerController extends AbstractController{

	//Services
	@Autowired
	private PhaseService phaseService;
	
	//Constructor

	public PhaseHandyWorkerController() {
		super();
	}
	
	//Create
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Phase phase;

		phase=this.phaseService.create();
		result=this.createEditModelAndView(phase);
		return result;


	}
	//List
	@RequestMapping(value="list",method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Phase> phases;
		phases=this.phaseService.findAll();

		result=new ModelAndView("phase/list");
		result.addObject("phases",phases);
		result.addObject("requestUri","phase/handyWorker/list.do");
		return result;

	}

	
	//Display
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int phaseId){
		final ModelAndView result;	
		Phase phase;

		phase=this.phaseService.findOne(phaseId);

		result=new ModelAndView("phase/display");
		result.addObject("phase", phase);
		result.addObject("requestUri", "phase/handyWorker/display.do");

		return result;

	}
	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		final ModelAndView result;
		Phase phase;

		phase=this.phaseService.findOne(phaseId);
		Assert.notNull(phase);

		result=this.createEditModelAndView(phase);

		return result;



	}
	
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}

		return result;
	}

		
	
	//Delete
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {
		ModelAndView result;

		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}

	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String message) {
		ModelAndView result;

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);

		result.addObject("message", message);
		return result;
	}


	
}
