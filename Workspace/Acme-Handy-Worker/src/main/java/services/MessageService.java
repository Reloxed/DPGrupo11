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
import domain.SystemConfiguration;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------

	@Autowired
	private MessageRepository messageRepository;

	//Supporting services -------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private SystemConfigurationService systemConfiguratoinService;
	
	@Autowired
	private MessageBoxService messageBoxService;
	
	@Autowired
	private AdministratorService administratorService;
	//CRUD Methods --------------------------------
	
	//When an actor sends a message it is saved to the out box
	public Message create(){
		Message result;
		Actor principal;
		Collection<MessageBox> messageBoxes;
		MessageBox outBox;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messageBoxes = new ArrayList<MessageBox>();	
		outBox = this.messageBoxService.findOutBoxActor(principal);	
		Assert.notNull(outBox);
		messageBoxes.add(outBox);

		result = new Message();
		result.setSender(principal);
		result.setMessageBoxes(messageBoxes);
		result.setSendMoment(new Date(System.currentTimeMillis()-1));


		return result;
	}
	
	//exchange messages between authenticated actors
	public Message save(final Message message){
		Actor principal;
		Message result;
		Date sendMoment;
		boolean isSpam;
		String spamWords;
		Collection<MessageBox> copyMessageBox;//Principal's outBox
		Collection<MessageBox> messageBoxes;//Recipients' inBoxes
		Message copy;
		
		Assert.notNull(message);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		sendMoment = new Date(System.currentTimeMillis()-1);
		isSpam = false;
		
		SystemConfiguration sc = (SystemConfiguration) this.systemConfiguratoinService.findAll().toArray()[0];
		spamWords = sc.getSpamWords();
		String[]splitSpamWords = spamWords.split(",");
		
		for(final String spam: splitSpamWords){
			if(message.getSubject().toLowerCase().contains(spam.toLowerCase())){
				isSpam = true;
				break;
			}else if(message.getBody().toLowerCase().contains(spam.toLowerCase())){
				isSpam = true;
				break;
			}
		}
		
		message.setSendMoment(sendMoment);
		message.setIsSpam(isSpam);
		message.setSender(principal);
		
		Collection<Actor>recipients = message.getRecipients();
		messageBoxes = new ArrayList<MessageBox>();
		for(final Actor a: recipients){
			Assert.isTrue(a.getId() != principal.getId());
			MessageBox inBox = this.messageBoxService.findInBoxActor(a);
			Assert.notNull(inBox);
			messageBoxes.add(inBox);
			
			}
		
		message.setMessageBoxes(messageBoxes);
		
		if(isSpam = true){
			for(final Actor a : recipients){
				MessageBox spamBox = this.messageBoxService.findSpamBoxActor(a);
				Assert.notNull(spamBox);
				messageBoxes.add(spamBox);
				
			}
			
			message.setMessageBoxes(messageBoxes);
			principal.setIsSuspicious(true);
		}
		
		result = this.messageRepository.save(message);
		Assert.notNull(result);
		
		copy = new Message();
		
		copy.setSubject(message.getSubject());
		copy.setBody(message.getBody());
		copy.setSendMoment(message.getSendMoment());
		copy.setIsSpam(message.getIsSpam());
		copy.setPriority(message.getPriority());
		copy.setRecipients(message.getRecipients());
		copy.setSender(message.getSender());
		
		MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);
		copyMessageBox = new ArrayList<MessageBox>();
		copyMessageBox.add(outBox);
		copy.setMessageBoxes(copyMessageBox);
		
		this.messageRepository.save(copy);
	
		return result;
	
}

	public void delete(final Message message){
		Actor principal;
		Collection<MessageBox> messageBoxesActor;//message boxes from principal actor
		Collection<MessageBox> messageBoxesMessage;//message boxes where message appears
		MessageBox trashBox;
		Collection<Message> allMessages;
		
		Assert.notNull(message);
		Assert.isTrue(message.getId()!=0);
		
		//Pick principal actor
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		//Obtain all messages from principal actor message boxes
		messageBoxesActor = principal.getMessageBoxes();
		Assert.notNull(messageBoxesActor);
		
		allMessages = new ArrayList<Message>();
		for(final MessageBox mb : messageBoxesActor){
			Collection<Message> m = mb.getMessages();
			allMessages.addAll(m);
		}
		
		Assert.isTrue(allMessages.contains(message));
		
		//check that message box where message appears is spam or not
		messageBoxesMessage = message.getMessageBoxes();
		Assert.notNull(messageBoxesMessage);
		
		for(final MessageBox mb : messageBoxesMessage){
			if(mb.getIsPredefined() && mb.getName().equals("trash box")){
				this.messageRepository.delete(message);
			}else{
				trashBox = this.messageBoxService.findTrashBoxActor(principal);
				this.move(message,trashBox);
			}
		}
		
	}
	
	//Other business methods -----------------------------

	public void move(final Message message, final MessageBox destination){
		Actor principal;
		MessageBox origin = null;
		Collection<Message> allMessages;
		Collection<MessageBox> messageBoxesMessage;
		Collection<Message> updatedOriginMessageBox;
		Collection<Message> updatedDestinationFolder;
		Message m;
		
		Assert.notNull(message);
		Assert.notNull(destination);
		
		Assert.isTrue(message.getId()!=0);
		Assert.isTrue(destination.getId()!=0);
		
		messageBoxesMessage = message.getMessageBoxes();
		allMessages = new ArrayList<Message>();
		//origin = (MessageBox) messageBoxesMessage.toArray()[0];
		for(final MessageBox mb : messageBoxesMessage){
			
			allMessages.addAll(mb.getMessages());
			
			if(mb.getMessages().contains(message)){
				origin = mb;
				
			}
			
			
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(allMessages.contains(message));
		Assert.isTrue(principal.getMessageBoxes().contains(origin));
		Assert.isTrue(principal.getMessageBoxes().contains(destination));
		
		
		
		}
		
		
		
	}
	
	public Message findOne(final int messageId){
		Message result;
		
		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);
		
		return result;
	}
	
	public void broadcast(final Message m){
		Administrator principal;
		String subject;
		String body;
		String priority;
		Collection<Actor> recipients;
		
		Collection<Actor> actorsBroadcast;
		Collection<MessageBox>inBoxRecipients;
		
		Collection<Actor> sender;
		Collection<MessageBox> outBoxSender;
		
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
		
		sendMoment = new Date(System.currentTimeMillis()-1);
		
		recipients = this.actorService.findAll();
		
		actorsBroadcast = new ArrayList<Actor>();
		inBoxRecipients = new ArrayList<MessageBox>();
		
		for(final Actor a : recipients){
			if(!(a instanceof Administrator)){
				final Message message = new Message();
				
				message.setSubject(subject);
				message.setBody(body);
				message.setPriority(priority);
				message.setSender(principal);
				
				actorsBroadcast.add(a);
				message.setRecipients(actorsBroadcast);
				
				message.setIsSpam(isSpam);
				message.setSendMoment(sendMoment);
				
				MessageBox inBox = this.messageBoxService.findInBoxActor(a);
				inBoxRecipients.add(inBox);
				message.setMessageBoxes(inBoxRecipients);
				
				this.messageRepository.save(message);
				
			}
			
		}
		
		outBoxMessage = new Message();
		outBoxMessage.setSubject(subject);
		outBoxMessage.setBody(body);
		outBoxMessage.setPriority(priority);
		outBoxMessage.setSender(principal);
		outBoxMessage.setIsSpam(isSpam);
		outBoxMessage.setSendMoment(sendMoment);
		
		MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);
		outBoxSender = new ArrayList<MessageBox>();
		outBoxSender.add(outBox);
		outBoxMessage.setMessageBoxes(outBoxSender);
		
		sender = new ArrayList<Actor>();
		sender.add(principal);
		outBoxMessage.setRecipients(sender);
		
		this.messageRepository.save(outBoxMessage);
	}
	
}
