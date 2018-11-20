package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {
	
	// Managed Repository
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	// Supporting Repositories
	
	// Simple CRUD Methods
	
	public CreditCard create() {
		CreditCard creditCard = new CreditCard();
		return creditCard;
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> collCC = new ArrayList<>(creditCardRepository.getAllCreditCards());
		return collCC;
	}
	
	public CreditCard findOne(int creditCardId){
		return creditCardRepository.getByCreditCardId(creditCardId);
	}
	
	public CreditCard save (CreditCard creditCard) throws ParseException{
		String monthYear = creditCard.getExpirationMonth() + " " + creditCard.getExpirationYear();
		SimpleDateFormat formato = new SimpleDateFormat("MM YY");
		Date expiration = formato.parse(monthYear);
		Assert.isTrue(expiration.after(LocalDate.now().toDate()));
		return creditCard;
	}
	
	public void delete(CreditCard creditCard) {
		
	}
	
	
	
	

}
