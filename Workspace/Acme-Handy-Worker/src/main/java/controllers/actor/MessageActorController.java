package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageBox;


@Controller
@RequestMapping("/message/actor")

public class MessageActorController extends AbstractController{

	//Services

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageBoxService messageBoxService;

	//Constructor

	public MessageActorController(){
		super();
	}


	//Create ----------------------------------------------------------------------
	
	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(){
		final ModelAndView result;
		Message mensaje;
		
		mensaje = this.messageService.create();
		
		result = this.createEditModelAndView(mensaje);
		
		return result;
	}



	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Message mensaje){
		ModelAndView result;

		result = this.createEditModelAndView(mensaje, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message mensaje, final String messageError){
		final ModelAndView result;
		Date sendMoment;
		MessageBox messageBox;
		Actor sender;
		Collection<Actor>recipients;
		Collection<MessageBox> boxes;
		Collection<Message> messagesPrincipal;
		boolean possible = false;;
		Actor principal;

		boxes = new ArrayList<MessageBox>();
		messagesPrincipal = new ArrayList<Message>();

		boxes = this.messageBoxService.findAllByPrincipal();
		Assert.notNull(boxes);

		for(MessageBox mb : boxes){
			messagesPrincipal.addAll(mb.getMessages());
		}
		principal = this.actorService.findByPrincipal();

		if(mensaje.getId() == 0){
			possible = true;
		}else{
			for(final Message m: messagesPrincipal){
				if(m.getId() == mensaje.getId()){
					possible = true;
					break;
				}

			}
		}

		sendMoment = mensaje.getSendMoment();
		messageBox = mensaje.getMessageBox();
		sender = mensaje.getSender();
		recipients = this.actorService.findAllExceptPrincipal();
		boxes = this.messageBoxService.findAllByPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("sendMoment", sendMoment);
		result.addObject("messageBox", messageBox);
		result.addObject("sender", sender);
		result.addObject("mensaje", mensaje);
		result.addObject("recipients", recipients);
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "message/actor/edit.do");
		result.addObject("broadcast", false);
		result.addObject("possible", possible);
		result.addObject("message", messageError);

		return result;

	}


}
