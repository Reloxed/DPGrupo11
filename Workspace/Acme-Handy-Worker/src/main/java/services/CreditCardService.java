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
	
	// Supporting Services
	
	@Autowired
	//private SponsorshipService sponsorshipService;
	
	
	// Simple CRUD Methods
	
	public CreditCard create() {
		return new CreditCard();
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> collCC = new ArrayList<>(creditCardRepository.findAll());
		return collCC;
	}
	
	public CreditCard findOne(int creditCardId){
		return creditCardRepository.findOne(creditCardId);
	}
	
	public CreditCard save (CreditCard creditCard) throws ParseException{
		Assert.notNull(creditCard);
		String monthYear = creditCard.getExpirationMonth() + " " + creditCard.getExpirationYear();
		SimpleDateFormat formato = new SimpleDateFormat("MM YY");
		Date expiration = formato.parse(monthYear);
		Assert.isTrue(expiration.after(LocalDate.now().toDate()));
		return creditCardRepository.save(creditCard);
	}
	
	public void delete(CreditCard creditCard) {
		//Assert.isTrue(!(Assert.notEmpty(sponsorshipService.find))
	}
	
	
	
	

}
