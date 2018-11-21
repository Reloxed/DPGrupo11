package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MoneyRepository;

@Service
@Transactional
public class MoneyService {

	//Managed repository ----------------------
	
	@Autowired
	private MoneyRepository moneyRepository;
	
	//Supporting services -------------------
	
	
	
	//CRUD Methods --------------------------------
	
	
	//Other business methods -----------------------------
}
