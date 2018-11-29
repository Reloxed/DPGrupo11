
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
<<<<<<< HEAD
	private MessageBoxService messageBoxService;
=======
	private SystemConfigurationService	systemConfiguratoinService;

	@Autowired
	private MessageBoxService			messageBoxService;
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

	@Autowired
	private AdministratorService		administratorService;


	@Autowired
	private SystemConfigurationService systemConfigurationService;

	//CRUD Methods --------------------------------

	public Message create() {
		Message result;
		Actor principal;
		Collection<MessageBox> messageBoxes;
		Collection<Actor> recipients;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messageBoxes = new ArrayList<MessageBox>();
		recipients = new ArrayList<Actor>();

		result = new Message();
		result.setSender(principal);
		result.setMessageBoxes(messageBoxes);
		result.setRecipients(recipients);
		result.setSendMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Message save(final Message message) {
		Actor principal;
<<<<<<< HEAD
		Message result,saved,copy,principalMessage; 
		Collection<MessageBox>inBoxesRecipients;
		Collection<MessageBox>copyOutBoxes;
		Collection<Message>messages;
		Collection<Message>updated,updated2,trashMessages,updatedTrashMessages,trashBoxMessagesPrincipal,updatedTrashPrincipal;
=======
		Message result, saved, copy, principalMessage; //copy is the message that is saved into principal outBox
		Collection<MessageBox> inBoxesRecipients;
		Collection<MessageBox> copyOutBoxes;
		Collection<Message> messages;
		Collection<Message> updated, updated2;
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
		MessageBox inBox;
		MessageBox outBoxPrincipal;
		Collection<Actor> recipients;
		boolean isSpam;
<<<<<<< HEAD
		MessageBox trashBox,trashBoxPrincipal;
		Collection<MessageBox>trashBoxes;
		Collection<MessageBox>trashBoxesPrincipal;
=======
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		result = this.create();
		updatedTrashPrincipal = new ArrayList<Message>();
		trashBoxes = new ArrayList<MessageBox>();
		trashBoxMessagesPrincipal = new ArrayList<Message>();
		trashBoxesPrincipal = new ArrayList<MessageBox>();
		messages = new ArrayList<Message>();
		updated2 = new ArrayList<Message>();
		recipients = new ArrayList<Actor>();
		copyOutBoxes = new ArrayList<MessageBox>();
		inBoxesRecipients = new ArrayList<MessageBox>();

		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(message.getSender());
		Assert.notNull(message.getSendMoment());

<<<<<<< HEAD

		recipients = message.getRecipients();
		Assert.notNull(recipients);
		isSpam = false;


=======
		recipients = message.getRecipients();
		Assert.notNull(recipients);
		isSpam = false;
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		result.setSubject(message.getSubject());
		result.setBody(message.getBody());
		result.setPriority(message.getPriority());
		result.setTags(message.getTags());
		result.setRecipients(recipients);

		String[] spamWords = this.systemConfigurationService
				.findMySystemConfiguration().getSpamWords().trim().split(",");
		Assert.notNull(spamWords);


		String[]subjectSplit = message.getSubject().trim().split(" ");
		String[]bodySplit = message.getBody().trim().split(" ");
		for(String word : spamWords){


			for(String wordSubject : subjectSplit){

				if(wordSubject.toLowerCase().indexOf(word.toLowerCase())!= -1){

					isSpam = true;
					principal.setIsSuspicious(true);
				}
			}

			for(String wordBody : bodySplit){

				if(wordBody.toLowerCase().indexOf(word.toLowerCase())!= -1){

					isSpam = true;
					principal.setIsSuspicious(true);

				}
			}
		}

		result.setIsSpam(isSpam);

<<<<<<< HEAD
		for(Actor a: recipients){

			if(isSpam == true){
				trashBox = this.messageBoxService.findTrashBoxActor(a);
				trashMessages = trashBox.getMessages();
				updatedTrashMessages = new ArrayList<Message>(trashMessages);
				updatedTrashMessages.add(result);
				trashBox.setMessages(updatedTrashMessages);
				Assert.isTrue(!(trashBox.getMessages().size()==0));
				trashBoxes.add(trashBox);
				result.setMessageBoxes(trashBoxes);
			}else{

				inBox =this.messageBoxService.findInBoxActor(a);
				messages = inBox.getMessages();
				updated = new ArrayList<Message>(messages);
				updated.add(result);
				inBox.setMessages(updated);
				Assert.isTrue(!(inBox.getMessages().size()==0));
				inBoxesRecipients.add(inBox);
				Assert.notNull(inBoxesRecipients);
				result.setMessageBoxes(inBoxesRecipients);
			}

		}


=======
		for (final Actor a : recipients) {
			inBox = this.messageBoxService.findInBoxActor(a);
			messages = inBox.getMessages();
			updated = new ArrayList<Message>(messages);
			updated.add(result);
			inBox.setMessages(updated);
			Assert.isTrue(!(inBox.getMessages().size() == 0));
			inBoxesRecipients.add(inBox);
			Assert.notNull(inBoxesRecipients);
		}

		result.setMessageBoxes(inBoxesRecipients);
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		saved = this.messageRepository.saveAndFlush(result);
		Assert.notNull(saved);
		System.out.println("Message saved: \n" + saved);
		Assert.isTrue(this.messageRepository.findAll().contains(saved));

<<<<<<< HEAD
		System.out.println("Saved messageBoxes: \n"+ saved.getMessageBoxes());
=======
		System.out.println("Saved messageBoxes: \n" + saved.getMessageBoxes());
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		copy = this.create();
		copy.setSubject(saved.getSubject());
		copy.setBody(saved.getBody());
		copy.setPriority(saved.getPriority());
		copy.setTags(saved.getTags());
		copy.setSender(saved.getSender());
		copy.setRecipients(saved.getRecipients());
		copy.setIsSpam(saved.getIsSpam());

<<<<<<< HEAD
		if(principal.getIsSuspicious() == true){
			trashBoxPrincipal = this.messageBoxService.findTrashBoxActor(principal);
			Assert.notNull(trashBoxPrincipal);
			trashBoxMessagesPrincipal = trashBoxPrincipal.getMessages();
			Assert.notNull(trashBoxMessagesPrincipal);
			updatedTrashPrincipal = new ArrayList<Message>(trashBoxMessagesPrincipal);
			updatedTrashPrincipal.add(copy);
			trashBoxPrincipal.setMessages(updatedTrashPrincipal);
			trashBoxesPrincipal.add(trashBoxPrincipal);


			copy.setMessageBoxes(trashBoxesPrincipal);
		}else{
			outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);		
			Assert.notNull(outBoxPrincipal);

			Collection<Message>outBoxMessages = outBoxPrincipal.getMessages();
			Assert.notNull(outBoxMessages);
			outBoxMessages.add(copy);


			updated2 = outBoxPrincipal.getMessages();
			Assert.notNull(updated2);
			updated2.add(copy);
			outBoxPrincipal.setMessages(updated2);

			copyOutBoxes = saved.getMessageBoxes();
			Assert.notNull(copyOutBoxes);
			copyOutBoxes.removeAll(inBoxesRecipients);
			copyOutBoxes.add(outBoxPrincipal);
			copy.setMessageBoxes(copyOutBoxes);
		}


		principalMessage = this.messageRepository.saveAndFlush(copy);

		Assert.isTrue(this.messageRepository.findAll().contains(principalMessage));


		System.out.println("Copy message: \n"+ principalMessage);

		return saved;

=======
		outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBoxPrincipal);

		final Collection<Message> outBoxMessages = outBoxPrincipal.getMessages();
		Assert.notNull(outBoxMessages);
		outBoxMessages.add(copy);

		updated2 = outBoxPrincipal.getMessages();
		Assert.notNull(updated2);
		updated2.add(copy);
		outBoxPrincipal.setMessages(updated2);

		copyOutBoxes = saved.getMessageBoxes();
		Assert.notNull(copyOutBoxes);
		copyOutBoxes.removeAll(inBoxesRecipients);
		copyOutBoxes.add(outBoxPrincipal);
		copy.setMessageBoxes(copyOutBoxes);

		principalMessage = this.messageRepository.saveAndFlush(copy);

		Assert.isTrue(this.messageRepository.findAll().contains(principalMessage));

		System.out.println("Copy message: \n" + principalMessage);

		return saved;

		/*
		 * Actor principal;
		 * Message result;
		 * Date sendMoment;
		 * boolean isSpam;
		 * //String spamWords;
		 * Collection<MessageBox> copyMessageBox;//Principal's outBox
		 * Collection<MessageBox> messageBoxes;//Recipients' inBoxes
		 * Message copy;
		 * 
		 * Assert.notNull(message);
		 * 
		 * principal = this.actorService.findByPrincipal();
		 * Assert.notNull(principal);
		 * 
		 * sendMoment = new Date(System.currentTimeMillis()-1);
		 * isSpam = false;
		 * 
		 * /*SystemConfiguration sc = this.systemConfiguratoinService.findMySystemConfiguration();
		 * spamWords = sc.getSpamWords().trim();
		 * String[]splitSpamWords = spamWords.split(",");
		 * 
		 * for(final String spam: splitSpamWords){
		 * if(message.getSubject().toLowerCase().contains(spam.toLowerCase())){
		 * isSpam = true;
		 * principal.setIsSuspicious(true);
		 * break;
		 * }else if(message.getBody().toLowerCase().contains(spam.toLowerCase())){
		 * isSpam = true;
		 * principal.setIsSuspicious(true);
		 * break;
		 * }
		 * }
		 */

		/*
		 * message.setSendMoment(sendMoment);
		 * message.setIsSpam(isSpam);
		 * message.setSender(principal);
		 * 
		 * Collection<Actor>recipients = message.getRecipients();
		 * messageBoxes = new ArrayList<MessageBox>();
		 * for(final Actor a: recipients){
		 * Assert.isTrue(a.getId() != principal.getId());
		 * MessageBox inBox = this.messageBoxService.findInBoxActor(a);
		 * Assert.notNull(inBox);
		 * messageBoxes.add(inBox);
		 * 
		 * }
		 * 
		 * message.setMessageBoxes(messageBoxes);
		 * 
		 * /*if(isSpam = true){
		 * for(final Actor a : recipients){
		 * MessageBox spamBox = this.messageBoxService.findSpamBoxActor(a);
		 * Assert.notNull(spamBox);
		 * messageBoxes.add(spamBox);
		 * 
		 * }
		 * 
		 * message.setMessageBoxes(messageBoxes);
		 * principal.setIsSuspicious(true);
		 * }
		 * 
		 * result = this.messageRepository.save(message);
		 * Assert.notNull(result);
		 * 
		 * copy = new Message();
		 * 
		 * copy.setSubject(message.getSubject());
		 * copy.setBody(message.getBody());
		 * copy.setSendMoment(message.getSendMoment());
		 * copy.setIsSpam(message.getIsSpam());
		 * copy.setPriority(message.getPriority());
		 * copy.setRecipients(message.getRecipients());
		 * copy.setSender(message.getSender());
		 * 
		 * MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);
		 * copyMessageBox = new ArrayList<MessageBox>();
		 * copyMessageBox.add(outBox);
		 * copy.setMessageBoxes(copyMessageBox);
		 * 
		 * this.messageRepository.save(copy);
		 * 
		 * return result;
		 */
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
	}

	public void delete(final Message message) {
		Actor principal;
		Collection<MessageBox> messageBoxesActor;
		Collection<MessageBox> messageBoxesMessage;
		MessageBox trashBox;
		Collection<Message> allMessages;

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messageBoxesActor = principal.getMessageBoxes();
		Assert.notNull(messageBoxesActor);

		allMessages = new ArrayList<Message>();
		for (final MessageBox mb : messageBoxesActor) {
			final Collection<Message> m = mb.getMessages();
			allMessages.addAll(m);
		}

		Assert.isTrue(allMessages.contains(message));

		
		messageBoxesMessage = message.getMessageBoxes();
		Assert.notNull(messageBoxesMessage);

		for (final MessageBox mb : messageBoxesMessage)
			if (mb.getIsPredefined() && mb.getName().equals("trsh box"))
				this.messageRepository.delete(message);
			else {
				trashBox = this.messageBoxService.findTrashBoxActor(principal);
				this.move(message, trashBox);
			}

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
		Collection<MessageBox> origin;
		Collection<Message> allMessagesOrigin;
<<<<<<< HEAD
		
=======
		final Collection<Message> allMessagesDestination;
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
		Collection<MessageBox> messageBoxesMessage;
		Collection<Message> updatedOriginBox;
		Collection<Message> updatedDestinationBox;
		Message result;

		Assert.notNull(message);
		Assert.notNull(destination);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

<<<<<<< HEAD
		Assert.isTrue(message.getId()!=0);
		Assert.isTrue(destination.getId()!=0);
=======
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(destination.getId() != 0);
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		updatedOriginBox = new ArrayList<Message>();
		origin = new ArrayList<MessageBox>();
		allMessagesOrigin = new ArrayList<Message>();
		messageBoxesMessage = new ArrayList<MessageBox>();

		origin = message.getMessageBoxes();
		for (final MessageBox mb : origin) {
			allMessagesOrigin.addAll(mb.getMessages());
			Assert.notNull(allMessagesOrigin);
		}
		Assert.isTrue(allMessagesOrigin.contains(message));
		messageBoxesMessage.add(destination);

		message.setMessageBoxes(messageBoxesMessage);

		result = this.messageRepository.saveAndFlush(message);

<<<<<<< HEAD
		if(allMessagesOrigin.contains(result)){
=======
		if (allMessagesOrigin.contains(result)) {
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
			Assert.isTrue(allMessagesOrigin.contains(result));
			allMessagesOrigin.remove(result);

		}
		updatedOriginBox = allMessagesOrigin;
		for (final MessageBox box : origin)
			box.setMessages(updatedOriginBox);
<<<<<<< HEAD
		}
=======
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

		updatedDestinationBox = destination.getMessages();
		Assert.isTrue(!updatedDestinationBox.contains(result));
		updatedDestinationBox.add(result);
<<<<<<< HEAD


	}
=======
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e

	}

	public void broadcast(final Message m) {
		Administrator principal;
		String subject;
		String body;
		String priority;
		Collection<Actor> recipients;
		Collection<Actor> actorsBroadcast;
		Collection<MessageBox> inBoxRecipients;

		Collection<Actor> sender;
		Collection<MessageBox> outBoxSender;
		Collection<Message> messages;

		boolean isSpam;
		Date sendMoment;

		Message outBoxMessage;

		Assert.notNull(m);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		subject = m.getSubject();
		body = m.getBody();
		priority = m.getPriority();
		isSpam = false;

		sendMoment = new Date(System.currentTimeMillis() - 1);

		recipients = this.actorService.findAll();
		Assert.notNull(recipients);

		
		actorsBroadcast = new ArrayList<Actor>();
		inBoxRecipients = new ArrayList<MessageBox>();
		messages = new ArrayList<Message>();

<<<<<<< HEAD

		for(final Actor a : recipients){
			if(!(a instanceof Administrator)){
				final Message message = this.create();
=======
		for (final Actor a : recipients)
			if (!(a instanceof Administrator)) {
				final Message message = new Message();

>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
				message.setSubject(subject);
				message.setBody(body);
				message.setPriority(priority);
				message.setSender(principal);

				actorsBroadcast.add(a);


				message.setIsSpam(isSpam);
				message.setSendMoment(sendMoment);

				final MessageBox inBox = this.messageBoxService.findInBoxActor(a);
				messages = inBox.getMessages();
				messages.add(m);
				inBox.setMessages(messages);
				inBoxRecipients.add(inBox);
				message.setMessageBoxes(inBoxRecipients);
				message.setRecipients(actorsBroadcast);

				this.messageRepository.save(message);
			}

<<<<<<< HEAD
		}

		outBoxMessage = this.create();
=======
		outBoxMessage = new Message();
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
		outBoxMessage.setSubject(subject);
		outBoxMessage.setBody(body);
		outBoxMessage.setPriority(priority);
		outBoxMessage.setSender(principal);
		outBoxMessage.setIsSpam(isSpam);
		outBoxMessage.setSendMoment(sendMoment);

<<<<<<< HEAD
		MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);

		Collection<Message>messagesOutBox = new ArrayList<Message>();
		messagesOutBox = outBox.getMessages();
		messagesOutBox.add(outBoxMessage);
		outBox.setMessages(messagesOutBox);

=======
		final MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);
>>>>>>> 9af37acc128b635c8afb461d610cd8791d183f9e
		outBoxSender = new ArrayList<MessageBox>();
		outBoxSender.add(outBox);
		outBoxMessage.setMessageBoxes(outBoxSender);

		sender = new ArrayList<Actor>();
		sender.add(principal);
		outBoxMessage.setRecipients(sender);

		this.messageRepository.save(outBoxMessage);
	}

	public Message createAndSaveStatus(final Actor actor, final String body, final Date moment) {
		Message result;
		Collection<Actor> recipients;
		Collection<MessageBox> boxes;
		MessageBox outBox;
		MessageBox inBox;
		recipients = new ArrayList<Actor>();
		boxes = new ArrayList<MessageBox>();

		result = new Message();
		result.setSender(actor);
		recipients.add(actor);
		result.setRecipients(recipients);
		result.setSendMoment(moment);
		result.setIsSpam(false);
		outBox = this.messageBoxService.findOutBoxActor(actor);
		inBox = this.messageBoxService.findInBoxActor(actor);
		boxes.add(outBox);
		boxes.add(inBox);
		result.setMessageBoxes(boxes);
		result.setBody(body);
		result.setSubject("Status updated");
		result.setPriority("NEUTRAL");

		this.messageRepository.save(result);

		return result;
	}

}
