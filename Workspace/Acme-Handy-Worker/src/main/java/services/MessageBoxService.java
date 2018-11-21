package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MessageBoxRepository;

@Service
@Transactional
public class MessageBoxService {

	// Managed repository ----------------------
	@Autowired
	private MessageBoxRepository messageBoxRepository;
	
	//Supporting services -------------------
	
	
	
	//CRUD Methods --------------------------------
	
	
	//Other business methods -----------------------------
}
