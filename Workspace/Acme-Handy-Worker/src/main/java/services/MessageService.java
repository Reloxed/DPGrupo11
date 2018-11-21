package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------
	
	@Autowired
	private MessageRepository messageRepository;
	
	//Supporting services -------------------
	
	
	
	//CRUD Methods --------------------------------
	
	
	//Other business methods -----------------------------
}
