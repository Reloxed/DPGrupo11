package services;

import java.text.SimpleDateFormat;
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
	
	public CreditCard create(String holderName, String brandName, String number, int expirationMonth, int expirationYear, int CVV) {
		String monthYear = expirationMonth + " " + expirationYear;
		SimpleDateFormat formato = new SimpleDateFormat("MM YY");
		Date expiration = formato.parse(monthYear);
		Assert.isTrue(expiration.after(LocalDate.now().toDate()));
		
		**return Creditcard;
	}
	

}
