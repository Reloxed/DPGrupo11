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


import services.TutorialService;

import controllers.AbstractController;


import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyWorker")
public class TutorialHandyWorkerController extends AbstractController{


	//Services

	@Autowired
	private TutorialService tutorialService;



	//Constructor

	public TutorialHandyWorkerController() {
		super();
	}

	//listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;	
		final Collection<Tutorial> tutorials;
		tutorials=this.tutorialService.findAllTutorialsByHandyWorker();
		result=new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestUri", "tutorial/handyWorker/list.do");
		return result;


	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial;

		tutorial=this.tutorialService.create();
		result=this.createEditModelAndView(tutorial);
		return result;


	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial tutorial;

		tutorial=this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);

		result=this.createEditModelAndView(tutorial);

		return result;



	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}

		return result;
	}


	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String message) {
		ModelAndView result;

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);

		result.addObject("message", message);
		return result;
	}

}
