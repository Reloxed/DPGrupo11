
package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//Constructor

	public MessageActorController() {
		super();
	}

	//Create ----------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.createEditModelAndView(mensaje);

		return result;
	}

	//Edition ------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);

		result = this.createEditModelAndView(message);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message mensaje, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(mensaje);
		else
			try {
				this.messageService.save(mensaje);
				result = new ModelAndView("redirect:/box/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(mensaje, "message.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "move")
	public ModelAndView move(@Valid final Message mensaje, final BindingResult binding) {
		ModelAndView result;
		MessageBox destination;
		if (binding.hasErrors())
			result = this.createEditModelAndView(mensaje);
		else
			try {

				destination = mensaje.getMessageBoxes().iterator().next();
				this.messageService.move(mensaje, destination);
				result = new ModelAndView("redirect:/box/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(mensaje, "message.commit.error");

			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message mensaje, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(mensaje);
		else
			try {
				this.messageService.delete(mensaje);
				result = new ModelAndView("redirect:/box/actor/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(mensaje, "message.commit.error");

			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Message mensaje) {
		ModelAndView result;

		result = this.createEditModelAndView(mensaje, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message mensaje, final String messageError) {
		ModelAndView result;
		Collection<MessageBox> boxes, messageBoxes;
		Collection<Message> messages;
		Actor sender;
		Collection<Actor> recipients;
		boolean possible = false;
		Actor principal;
		Date sendMoment;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messages = new ArrayList<Message>();

		boxes = principal.getMessageBoxes();
		for (final MessageBox mb : boxes)
			messages.addAll(mb.getMessages());

		if (mensaje.getId() == 0)
			possible = true;
		else
			for (final Message m : messages)
				if (m.getId() == mensaje.getId()) {
					possible = true;
					break;
				}

		sendMoment = mensaje.getSendMoment();
		messageBoxes = mensaje.getMessageBoxes();
		sender = mensaje.getSender();
		recipients = this.actorService.findAllExceptPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("sendMoment", sendMoment);
		result.addObject("messageBoxes", messageBoxes);
		result.addObject("sender", sender);
		result.addObject("mensaje", mensaje);
		result.addObject("recipients", recipients);
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "message/actor/edit.do");
		result.addObject("possible", possible);
		result.addObject("broadcast", false);
		result.addObject("message", messageError);

		return result;
	}

}
