package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import domain.MessageBox;


@Controller
@RequestMapping(value="/message-box")
public class MessageBoxController extends AbstractController{
	
	//Services
	
	@Autowired
	private MessageBoxService messageBoxService;
	
	@Autowired
	private ActorService actorService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		MessageBox messageBox;
		
		messageBox = this.messageBoxService.create();
		
		result = new ModelAndView("message-box/list");
		result.addObject("messageBox", messageBox);
		result.addObject("requestURI", "message-box/list.do");
		
		return result;
		
	}
	
	
}
