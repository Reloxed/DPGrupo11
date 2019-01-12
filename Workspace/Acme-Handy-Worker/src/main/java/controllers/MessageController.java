package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController{
	
	// Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	// Create
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Message message;
		
		message = this.messageService.create();
		
		result = this.createEditModelAndView(message);
		
		return result;
	}
	
//	@RequestMapping(value="/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam int messageId){
//		ModelAndView result;
//		Message message;
//		
//		message = this.messageService.findOne(messageId);
//		Assert.notNull(message);
//		
//		result = this.createEditModelAndView(message);
//		
//		return result;
//	}
	
	// Edition
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message m, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
				result = this.createEditModelAndView(m);
		else
			try {
				this.messageService.save(m);
				result = new ModelAndView("redirect:messageBox/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "message.commit.error");
			}
		return result;
	}
	
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(Message m){
		ModelAndView result;
		
		result = this.createEditModelAndView(m, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Message m, String messageCode){
		ModelAndView result;
		Collection<Actor> actors = this.actorService.findAll();
		
		
		result = new ModelAndView("message/edit");
		result.addObject("m", m);
		result.addObject("messageCode", messageCode);
		result.addObject("actors", actors);
		result.addObject("sendMoment", m.getSendMoment());
		result.addObject("sender", m.getSender());
		result.addObject("isSpam", m.getIsSpam());
		result.addObject("messageBoxes", m.getMessageBoxes());
		
		
		return result;
	}
}
