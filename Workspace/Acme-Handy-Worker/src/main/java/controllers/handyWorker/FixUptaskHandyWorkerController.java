package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;

import controllers.AbstractController;

import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUptaskHandyWorkerController extends AbstractController{

	//Services
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	


	//Constructor
	
	public FixUptaskHandyWorkerController() {
		super();
	}
	
	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int taskId){
		final ModelAndView result;	
		FixUpTask fixUpTask;
		
		fixUpTask=this.fixUpTaskService.findOne(taskId);
		
		result=new ModelAndView("fixUpTask/display");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("requestUri", "fixUpTask/handyWorker/display.do");
		
		return result;
	
		
	}
	
	//list
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks=this.fixUpTaskService.findAll();
		
		result=new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks",fixUpTasks);
		result.addObject("requestUri","fixUpTask/handyWorker/list.do");
		return result;
		
	}
	
}
