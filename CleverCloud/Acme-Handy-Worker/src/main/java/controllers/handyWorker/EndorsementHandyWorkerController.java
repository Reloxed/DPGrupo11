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

import services.EndorsementService;
import services.EndorserService;

import controllers.AbstractController;

import domain.Endorsement;
import domain.Endorser;


@Controller
@RequestMapping("/endorsement/handyWorker")
public class EndorsementHandyWorkerController extends AbstractController{

	//Services

	@Autowired
	private EndorsementService endorsermentService;

	@Autowired
	private EndorserService endorserService ;
	
		
	

	//Constructor

	public EndorsementHandyWorkerController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;
		

		endorsement=this.endorsermentService.create();
		result=this.createEditModelAndView(endorsement);
		return result;


	}

	//List

	@RequestMapping(value="list",method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Endorsement> endorsements;
		Endorser endorser;

		endorser = this.endorserService.findByPrincipal();
		endorsements=this.endorserService.findEndorsementsByEndorser(endorser.getId());

		result=new ModelAndView("endorsement/list");
		result.addObject("endorsements",endorsements);
		result.addObject("requestUri","endorsement/handyWorker/list.do");
		return result;

	}


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorsementId){
		final ModelAndView result;	
		Endorsement endorsement;

		endorsement=this.endorsermentService.findOne(endorsementId);

		result=new ModelAndView("endorsement/display");
		result.addObject("endorsement", endorsement);
		result.addObject("requestUri", "endorsement/handyWorker/display.do");

		return result;


	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		final ModelAndView result;
		Endorsement endorsement;

		endorsement=this.endorsermentService.findOne(endorsementId);
		Assert.notNull(endorsement);

		result=this.createEditModelAndView(endorsement);

		return result;



	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement);
		else
			try {
				this.endorsermentService.save(endorsement);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}

		return result;
	}


	//Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorsermentService.delete(endorsement);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String message) {
		ModelAndView result;
		Collection<Endorser> endorsers;
		//int guardado;
		//guardado=endorsement.getId();
		//String receptor;
		//receptor=endorsement.getRecipient().getName();
		endorsers=this.endorserService.findAll();
		
		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("endorsers",endorsers);
		//result.addObject("guardado",guardado);
		//result.addObject("receptor",receptor);
		
		result.addObject("message", message);
		return result;
	}



}
