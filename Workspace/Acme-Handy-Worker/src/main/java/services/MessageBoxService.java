package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	// Managed repository ----------------------
	@Autowired
	private MessageBoxRepository messageBoxRepository;
	
	//Supporting services -------------------
	
	private ActorService actorService;
	
	//CRUD Methods --------------------------------
	
	public List<MessageBox> createSystemMessageBoxes(){
		List<MessageBox> result;
		List<String> names;
		Collection<Message> messages;
		MessageBox saved;
		
		names = new ArrayList<String>();
		names.add("in box");
		names.add("out box");
		names.add("spam box");
		names.add("trash box");
		
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
}
