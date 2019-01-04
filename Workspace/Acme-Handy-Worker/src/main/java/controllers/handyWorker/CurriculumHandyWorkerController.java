package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.HandyWorkerService;

import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;

@Controller
@RequestMapping("/curriculum/handyWorker")
public class CurriculumHandyWorkerController extends AbstractController{

	
	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	
	@RequestMapping(value="/display",method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumID){
		ModelAndView result;
		Curriculum curriculum;
		HandyWorker principal;
		//curriculum = this.curriculumService.findCurriculumByPrincipal();
		curriculum=this.curriculumService.findOne(curriculumID);
		Assert.notNull(curriculum);
		principal=this.handyWorkerService.findByPrincipal();
		
		
		result=new ModelAndView("curriculum/display");
		result.addObject("curriculum",curriculum);
		result.addObject("principal", principal);
		result.addObject("requestUri","curriculum/handyWorker/display.do");
		
		return result;
		
	}
	
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Curriculum curriculum;
		
		
		
		curriculum=this.curriculumService.create();
		
		result=this.createEditModelAndView(curriculum);
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/welcome/index.do");//redirijir al perfil no?
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST,params="save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumID) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumID);
		Assert.notNull(curriculum);
		result = this.createEditModelAndView(curriculum);

		return result;
	}
	
	 
	
	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		final ModelAndView result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}
	
}
