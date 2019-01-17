package controllers.actor;

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
import services.MessageBoxService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/box/actor")
public class MessageBoxActorController extends AbstractController{

	//Services

	@Autowired
	private MessageBoxService messageBoxService;

	@Autowired
	private ActorService actorService;

	//Constructor

	public MessageBoxActorController(){
		super();
	}

	//Listing

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;
		final Collection<MessageBox> boxes;

		boxes = this.messageBoxService.findAllByPrincipal();

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);

		return result;
	}

	@RequestMapping(value="/list", method = RequestMethod.GET, params={"messageBoxId"})
	public ModelAndView list(@RequestParam final int messageBoxId){

		final ModelAndView result;
		MessageBox actualMessageBox;
		Collection<Message> messages;

		actualMessageBox = this.messageBoxService.findOne(messageBoxId);

		messages = actualMessageBox.getMessages();

		result = new ModelAndView("box/list");
		result.addObject("actualMessageBox", actualMessageBox);
		result.addObject("messages", messages);

		return result;


		//Create -----------------------------------------------------------------


	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		MessageBox messageBox;
		
		messageBox = this.messageBoxService.create();
		
		result = this.createEditModelAndView(messageBox);
		
		
		return result;
	}

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageBoxId){
		ModelAndView result;
		MessageBox messageBox;
		
		messageBox = this.messageBoxService.findOne(messageBoxId);
		Assert.notNull(messageBox);
		
		result = this.createEditModelAndView(messageBox);
		
		return result;
	}

	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid final MessageBox messageBox, final BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = this.createEditModelAndView(messageBox);
		else
			try{
				this.messageBoxService.save(messageBox);
				result = new ModelAndView("redirect:list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
				
			}
		return result;
		
		
		
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(final MessageBox messageBox,final BindingResult binding){
		ModelAndView result;
		
		try{
			this.messageBoxService.delete(messageBox);
			result = new ModelAndView("redirect:list.do");
			
		}catch(final Throwable oops){
			result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
			
		}
		
		return result;
	}


	//Ancillary methods --------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(final MessageBox messageBox){
		ModelAndView result;
		
		result = this.createEditModelAndView(messageBox, null);
		
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox,final String messageError){
		ModelAndView result;
		Collection<Message> messages;
		String name;
		Actor principal;
		boolean possible = false;
		
		principal = this.actorService.findByPrincipal();
		
		if(messageBox.getId() == 0){
			possible = true;
		}else{
			
			for(final MessageBox mb: principal.getMessageBoxes()){
				if(mb.getId() == messageBox.getId()){
					possible = true;
					break;
				}
				
			}
		}
			
		messages = messageBox.getMessages();
		
		if(messageBox.getName() == null){
			name = null;
		}else{
			name = messageBox.getName();
		}
		
		result = new ModelAndView("box/edit");
		result.addObject("messageBox", messageBox);
		result.addObject("name", name);
		result.addObject("messages", messages);
		result.addObject("possible", possible);
		result.addObject("message", messageError);
		
		return result;
	}



}
