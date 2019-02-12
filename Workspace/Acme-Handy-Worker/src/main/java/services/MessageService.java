
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------

	@Autowired
	private MessageRepository			messageRepository;

	//Supporting services -------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private MessageBoxService			messageBoxService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public MessageService() {
		super();
	}

	//CRUD Methods --------------------------------

	public Message create() {
		Message result;
		Actor principal;
		MessageBox outBox;
		Collection<MessageBox> boxes;

		boxes = new ArrayList<MessageBox>();
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Message();

		outBox = this.messageBoxService.findOutBoxActor(principal);
		boxes.add(outBox);
		result.setSender(principal);
		result.setMessageBoxes(boxes);
		result.setSendMoment(new Date(System.currentTimeMillis()-1));

		return result;
	}

	public Message save(final Message message) {
		Message result;
		Actor principal;
		MessageBox inSpamBox,outBox;
		Collection<MessageBox> boxes;



		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);


		//checking spam
		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] subject = message.getSubject().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : subject)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		if (!containsSpam) {
			final String[] body = message.getBody().split("(¿¡,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : body)
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}
			if (message.getTags() != null)
				if (!containsSpam) {
					final String[] tags = message.getTags().split("(¿¡,.-_/!?) ");
					for (final String word : spamWords) {
						for (final String titleWord : tags)
							if (titleWord.toLowerCase().contains(word.toLowerCase())) {
								containsSpam = true;
								break;
							}
						if (containsSpam) {
							principal.setIsSuspicious(true);
							break;
						}
					}
				}

		}



		//Obtaining message's values
		message.setSendMoment(new Date(System.currentTimeMillis()-1));
		message.setIsSpam(containsSpam);
		message.setSender(principal);
		Assert.isTrue(message.getRecipient().getId() != principal.getId());		

		//Checking spamBox

		if(containsSpam){
			inSpamBox = this.messageBoxService.findSpamBoxActor(message.getRecipient());
			Assert.notNull(inSpamBox);



		}else{

			inSpamBox = this.messageBoxService.findInBoxActor(message.getRecipient());

			Assert.notNull(inSpamBox);



		}
		boxes = new ArrayList<MessageBox>();
		outBox = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBox);

		boxes.add(outBox);
		boxes.add(inSpamBox);

		message.setMessageBoxes(boxes);

		result = this.messageRepository.save(message);

		outBox.getMessages().add(result);
		inSpamBox.getMessages().add(result);

		Assert.notNull(result);


		return result;
	}

	public void delete(final Message message) {
		Actor principal;
		MessageBox trashBoxPrincipal;
		Collection<Message> messagesTrashBox;


		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		trashBoxPrincipal = this.messageBoxService.findTrashBoxActor(principal);
		Assert.notNull(trashBoxPrincipal);

		messagesTrashBox = trashBoxPrincipal.getMessages();
		Assert.notNull(messagesTrashBox);

		if(messagesTrashBox.contains(message)){
			if(this.findMessage(message)){
				messagesTrashBox.remove(message);
			}else{
				messagesTrashBox.remove(message);
				this.messageRepository.delete(message);
			}
		}else{
			this.move(message, trashBoxPrincipal);
		}
	}



	public boolean findMessage(final Message message){
		boolean result = false;
		Actor sender;
		Actor recipient;

		sender = message.getRecipient();
		recipient = message.getSender();

		Assert.notNull(sender);
		Assert.notNull(recipient);

		for(MessageBox mb : sender.getMessageBoxes()){
			if(!mb.getName().equals("Trash Box")){
				if(mb.getMessages().contains(message)){
					result = true;
				}
			}
		}

		for(MessageBox mb : recipient.getMessageBoxes()){
			if(!mb.getName().equals("Trash Box")){
				if(mb.getMessages().contains(message)){
					result = true;
				}
			}
		}
		return result;

	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	//Other business methods -----------------------------

	public void move(final Message message, final MessageBox destination) {
		Actor principal;
		MessageBox origin = null;
		Collection<Message> updatedOriginBox,updatedDestinationBox,messages;
		Collection<MessageBox> messageBoxes;

		Message m;

		messages = new ArrayList<Message>();
		messageBoxes = new ArrayList<MessageBox>();
		Assert.notNull(message);
		Assert.notNull(destination);

		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(destination.getId() != 0);

		messageBoxes = message.getMessageBoxes();
		Assert.notNull(messageBoxes);


		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);


		for(MessageBox box: principal.getMessageBoxes()){
			messages.addAll(box.getMessages());
		}

		Assert.isTrue(messages.contains(message));

		for(MessageBox principalBox : principal.getMessageBoxes()){
			if(principalBox.getMessages().contains(message)){
				origin = principalBox;
				messageBoxes.remove(origin);
				break;
			}



		}

		messageBoxes.add(destination);


		Assert.isTrue(principal.getMessageBoxes().contains(origin));
		Assert.isTrue(principal.getMessageBoxes().contains(destination));

		message.setMessageBoxes(messageBoxes);
		m = this.messageRepository.save(message);

		updatedOriginBox = origin.getMessages();
		updatedDestinationBox = destination.getMessages();

		updatedOriginBox.remove(m);
		updatedDestinationBox.add(m);

		origin.setMessages(updatedOriginBox);
		destination.setMessages(updatedDestinationBox);


	}







	public void broadcast(final Message m) {
		Administrator principal;
		String subject,body,priority;
		Collection<Actor> recipients;
		boolean isSpam;
		Date sendMoment;
		Message saved,savedAdmin,outBoxAdmin;
		Collection<MessageBox>boxes,boxesAdmin,inBoxes;
		MessageBox outBox,inBox,adminBox;

		Assert.notNull(m);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		subject = m.getSubject();
		body = m.getBody();
		priority = "NEUTRAL";
		
		isSpam = false;

		sendMoment = new Date(System.currentTimeMillis()-1);

		recipients = this.actorService.findAllExceptPrincipal();
		Assert.notNull(recipients);
		outBox = this.messageBoxService.findOutBoxActor(principal);
		inBoxes = this.messageBoxService.findAllInBoxExceptAdmin();

		boxes = new ArrayList<MessageBox>();



		final Message message = new Message();
		message.setSubject(subject);
		message.setBody(body);
		message.setPriority(priority);
		message.setSender(principal);

		message.setIsSpam(isSpam);
		message.setSendMoment(sendMoment);

		message.setRecipient(principal);

		boxes.add(outBox);
		boxes.addAll(inBoxes);


		message.setMessageBoxes(boxes);

		saved = this.messageRepository.save(message);


		for(MessageBox mb: inBoxes){
			mb.getMessages().add(saved);
		}

		boxesAdmin = new ArrayList<MessageBox>();


		boxesAdmin.add(outBox);

		outBoxAdmin = new Message();
		outBoxAdmin.setSubject(subject);
		outBoxAdmin.setBody(body);
		outBoxAdmin.setPriority(priority);
		outBoxAdmin.setSender(principal);
		outBoxAdmin.setIsSpam(isSpam);
		outBoxAdmin.setSendMoment(sendMoment);
		outBoxAdmin.setMessageBoxes(boxesAdmin);
		outBoxAdmin.setRecipient(principal);
		
		savedAdmin = this.messageRepository.save(outBoxAdmin);

		outBox.getMessages().add(savedAdmin);

	}

	public Message createAndSaveStatus(final Actor actor, final String body, final Date moment) {

		Message result;
		Collection<MessageBox> boxes;
		MessageBox inBox,outBox;
		Actor principal;
		Message saved;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		boxes = new ArrayList<MessageBox>();

		inBox = this.messageBoxService.findInBoxActor(actor);
		Assert.notNull(inBox);

		outBox = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBox);

		boxes.add(inBox);
		boxes.add(outBox);

		result = new Message();
		result.setSender(principal);
		result.setRecipient(actor);
		result.setPriority("NORMAL");
		result.setSendMoment(moment);
		result.setIsSpam(false);
		result.setMessageBoxes(boxes);
		result.setBody(body);
		result.setSubject("New status update");

		saved = this.messageRepository.save(result);

		for(MessageBox mb : principal.getMessageBoxes()){
			if(mb.getName().equals("Out box")){
				mb.getMessages().add(saved);
			}
		}

		for(MessageBox mb : actor.getMessageBoxes()){
			if(mb.getName().equals("In box")){
				mb.getMessages().add(saved);
			}
		}
		return result;
	}

}
