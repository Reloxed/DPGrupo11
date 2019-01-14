
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
	private UtilityService	utilityService;


	// Constructors ------------------------------------

	public MessageService() {
		super();
	}

	//CRUD Methods --------------------------------

	public Message create() {
		Message result;
		Actor principal;
		MessageBox outBox;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Message();

		outBox = this.messageBoxService.findOutBoxActor(principal);

		result.setSender(principal);
		result.setMessageBox(outBox);
		result.setSendMoment(new Date(System.currentTimeMillis()-1));

		return result;
	}

	public Message save(final Message message) {
		Message result;
		Actor principal;
		MessageBox box,outBox;
		Message copy;
		Message copySaved;
		
		
		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
<<<<<<< HEAD
		
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
=======

		Assert.notNull(message.getSender());
		Assert.notNull(message.getSendMoment());

		recipients = message.getRecipients();
		Assert.notNull(recipients);

		result.setSubject(message.getSubject());
		result.setBody(message.getBody());
		result.setPriority("NEUTRAL");
		result.setTags(message.getTags());
		result.setRecipients(recipients);

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(message.getSubject());
		atributosAComprobar.add(message.getBody());
		if (message.getTags() != null)
			atributosAComprobar.add(message.getTags());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
>>>>>>> master
		}

	

		//Obtaining message's values
		message.setSendMoment(new Date(System.currentTimeMillis()-1));
		message.setIsSpam(containsSpam);
		message.setSender(principal);
		Assert.isTrue(message.getRecipient().getId() != principal.getId());		
	
		
		//Checking spamBox

		if(containsSpam){
			box = this.messageBoxService.findSpamBoxActor(message.getRecipient());
			Assert.notNull(box);

			
			
		}else{
			
			box = this.messageBoxService.findInBoxActor(message.getRecipient());
			
			Assert.notNull(box);
			
			
			
		}
		outBox = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBox);
		
		message.setMessageBox(box);
		
		result = this.messageRepository.save(message);
		box.getMessages().add(result);
	
		
		Assert.notNull(result);
		
		copy =  new Message();
		copy.setSubject(message.getSubject());
		copy.setBody(message.getBody());
		copy.setSendMoment(message.getSendMoment());
		copy.setIsSpam(message.getIsSpam());
		copy.setPriority(message.getPriority());
		copy.setRecipient(message.getRecipient());
		copy.setSender(message.getSender());
		copy.setMessageBox(outBox);
		
		
	
		copySaved = this.messageRepository.save(copy);

		
		
		for(MessageBox mb: principal.getMessageBoxes()){
			if(mb.getName().equals("Out box")){
				mb.getMessages().add(copySaved);
				System.out.println(mb.getMessages());
			}
		}
		
		return result;
	}

	public void delete(final Message message) {
		Actor principal;
		MessageBox box;
		MessageBox trashBox;
		Collection<Message> messages;
		
		messages = new ArrayList<Message>();
		
		
		Assert.notNull(message);
		Assert.notNull(message.getId()!=0);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		for(MessageBox mb : principal.getMessageBoxes()){
			
			messages.addAll(mb.getMessages());
		}
		
		Assert.isTrue(messages.contains(message));
		
		box = message.getMessageBox();
		
		if(box.getIsPredefined() && box.getName().equals("Trsh box")){
			this.messageRepository.delete(message);
			box.getMessages().remove(message);
			
		}else{
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
		MessageBox origin;
		Collection<Message> updatedOriginBox,updatedDestinationBox,messages;
		Message m;
		
		messages = new ArrayList<Message>();
		
		Assert.notNull(message);
		Assert.notNull(destination);
		
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(destination.getId() != 0);
		
		origin = message.getMessageBox();
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		for(MessageBox mb: principal.getMessageBoxes()){
			messages.addAll(mb.getMessages());
		}
		Assert.isTrue(messages.contains(message));
		Assert.isTrue(principal.getMessageBoxes().contains(origin));
		Assert.isTrue(principal.getMessageBoxes().contains(destination));
		
		message.setMessageBox(destination);
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
		Message outBoxAdmin;
		
		Assert.notNull(m);
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		
		subject = m.getSubject();
		body = m.getBody();
		priority = m.getPriority();
		isSpam = false;
		
		sendMoment = new Date(System.currentTimeMillis()-1);
		
		recipients = this.actorService.findAll();
		Assert.notNull(recipients);
		
		for(final Actor recipient: recipients){
			if(!(recipient instanceof Administrator)){
				final Message message = new Message();
				message.setSubject(subject);
				message.setBody(body);
				message.setPriority(priority);
				message.setSender(principal);
				message.setRecipient(recipient);
				message.setIsSpam(isSpam);
				message.setSendMoment(sendMoment);
				message.setMessageBox(this.messageBoxService.findInBoxActor(recipient));
				
				this.messageRepository.save(message);
			}
			
			
		}
		
		outBoxAdmin = new Message();
		outBoxAdmin.setSubject(subject);
		outBoxAdmin.setBody(body);
		outBoxAdmin.setPriority(priority);
		outBoxAdmin.setSender(principal);
		outBoxAdmin.setIsSpam(isSpam);
		outBoxAdmin.setSendMoment(sendMoment);
		outBoxAdmin.setMessageBox(this.messageBoxService.findOutBoxActor(principal));
		outBoxAdmin.setRecipient(principal);
		this.messageRepository.save(outBoxAdmin);
	}

	public Message createAndSaveStatus(final Actor actor, final String body, final Date moment) {
		Message result;
		
		
		result = new Message();
		result.setSender(actor);
		result.setRecipient(actor);
		result.setPriority("NORMAL");
		result.setSendMoment(moment);
		result.setIsSpam(false);
		result.setMessageBox(this.messageBoxService.findInBoxActor(actor));
		result.setBody(body);
		result.setSubject("New status update");
		
		this.messageRepository.save(result);

		return result;
	}

}
