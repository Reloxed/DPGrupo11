package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	// Managed repository ----------------------
	@Autowired
	private MessageBoxRepository messageBoxRepository;
	
	//Supporting services -------------------
	@Autowired
	private ActorService actorService;
	
	//CRUD Methods --------------------------------
	public MessageBox create(){
		MessageBox result;
		Actor principal;
		Collection<Message> messages;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		messages = new ArrayList<Message>();
		
		result = new MessageBox();
		Assert.notNull(result);
		
		result.setMessages(messages);
		
		
		return result;
	}
	
	public MessageBox findOne(final int messageBoxId){
		MessageBox result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(messageBoxId!=0);
		
		result = this.messageBoxRepository.findOne(messageBoxId);
		Assert.notNull(result);
		
		return result;
		
	}
	
	public Collection<MessageBox> findAll(){
		Collection<MessageBox>result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = this.messageBoxRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public MessageBox save(final MessageBox messageBox){
		MessageBox result;
		Actor principal;
		Collection<Message>messages;
		
		Assert.notNull(messageBox);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		if(messageBox.getIsPredefined()==true){
			messageBox.setIsPredefined(true);
			messages = new ArrayList<Message>();
			messageBox.setMessages(messages);
			
		}else{
			
			Assert.isTrue(messageBox.getIsPredefined()==false);
			Assert.isTrue(!principal.getMessageBoxes().contains(messageBox));
			messageBox.setIsPredefined(false);
			Collection<Message>messagesBox = messageBox.getMessages();
			messageBox.setMessages(messagesBox);
			
		}
		
		result = this.messageBoxRepository.saveAndFlush(messageBox);
		result.setName(messageBox.getName());
		Assert.notNull(result);
		
		if(!principal.getMessageBoxes().contains(result)){
			principal.getMessageBoxes().add(result);
		}
		
		return result;
		
	}
	
	public void delete(MessageBox messageBox){
		Actor principal;
		
		Assert.isTrue(messageBox.getId()!=0);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(!messageBox.getIsPredefined());
		Assert.isTrue(principal.getMessageBoxes().contains(messageBox));
		
		this.messageBoxRepository.delete(messageBox);
		principal.getMessageBoxes().remove(messageBox);
	}
	
	
	public Collection<MessageBox> createSystemMessageBoxes(){
		Collection<MessageBox> result;
		Collection<String> names;
		Collection<Message> messages;
		MessageBox saved;
		
		names = new ArrayList<String>();
		names.add("In box");
		names.add("Out box");
		names.add("Spam box");
		names.add("Trash box");
		
		result = new ArrayList<MessageBox>();
		for(final String name : names){
			final MessageBox messageBox = new MessageBox();
			messageBox.setName(name);
			messageBox.setIsPredefined(true);
			messages = new ArrayList<Message>();
			messageBox.setMessages(messages);
			saved = this.messageBoxRepository.save(messageBox);
			Assert.notNull(saved);
			
			result.add(saved);
		}
		
		return result;
	}
	
	//Other business methods -----------------------------
	
	public MessageBox findOutBoxActor(final Actor a){
		Actor principal;
		MessageBox result;
		
		principal = this.actorService.findByPrincipal();
		
		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		
		result = this.messageBoxRepository.findOutBoxActorId(a.getId());
		Assert.notNull(result);
		
		return result;
	}
	
	public MessageBox findTrashBoxActor(final Actor a){
		Actor principal;
		MessageBox result;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.notNull(a);
		Assert.isTrue(a.getId()!=0);
		
		result = this.messageBoxRepository.findTrashBoxActorId(a.getId());
		Assert.notNull(result);
		
		return result;
	
	}
	
	public MessageBox findInBoxActor(final Actor a){
		Actor principal;
		MessageBox result;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		
		result = this.messageBoxRepository.findInBoxActorId(a.getId());
		Assert.notNull(result);
		
		return result;
	}
	
	public MessageBox findSpamBoxActor(final Actor a){
		Actor principal;
		MessageBox result;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		
		result = this.messageBoxRepository.findSpamBoxActorId(a.getId());
		Assert.notNull(result);
		
		return result;
	}
}
