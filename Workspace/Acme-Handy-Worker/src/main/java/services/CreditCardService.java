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
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {
	
	// Managed Repository
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	// Supporting Services
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SponsorService sponsorService;
	
	
	// Simple CRUD Methods
	
	public CreditCard create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(userAccount.getAuthorities().contains("SPONSOR")||
				userAccount.equals(userAccount.getAuthorities().contains("CUSTOMER"))));
		
		return new CreditCard();
	}
	
	public Collection<CreditCard> findAll(){
		Collection<CreditCard> collCC = new ArrayList<>(this.creditCardRepository.findAll());
		return collCC;
	}
	
	public CreditCard findOne(int creditCardId){
		return this.creditCardRepository.findOne(creditCardId);
	}
	
	public CreditCard save (CreditCard creditCard) throws ParseException{
		
		Assert.notNull(creditCard);
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(userAccount.getAuthorities().contains("SPONSOR")||
				userAccount.equals(userAccount.getAuthorities().contains("HANDYWORKER"))));		

		String monthYear = creditCard.getExpirationMonth() + " " + creditCard.getExpirationYear();
		SimpleDateFormat formato = new SimpleDateFormat("MM YY");
		Date expiration = formato.parse(monthYear);
		Assert.isTrue(expiration.after(LocalDate.now().toDate()));
		return this.creditCardRepository.save(creditCard);
	}
	
	public void delete(CreditCard creditCard) {
		
		Assert.notNull(creditCard);
		
		Integer userAccountId = LoginService.getPrincipal().getId();
		Assert.isTrue(userAccountId.equals(this.customerService.findByCreditCardId(creditCard).getUserAccount())
				||userAccountId.equals(this.sponsorService.findByCreditCardId(creditCard).getUserAccount()));
		//Assert.isTrue(!(Assert.notEmpty(sponsorshipService.find))
	}
	
	
	// Other business methods
	

}
