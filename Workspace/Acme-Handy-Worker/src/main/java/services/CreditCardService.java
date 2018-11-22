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
import domain.Customer;
import domain.Sponsor;

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
	
//	public CreditCard create() {
//		
//		final CreditCard result;
//		Sponsor ownerSponsor;
//		Customer ownerCustomer;
//
//		ownerSponsor = this.sponsorService.findByPrincipal();
//		ownerCustomer = this.customerService.findByPrincipal();
//		if(!(ownerCustomer==null && ownerSponsor!=null||ownerCustomer!=null && ownerSponsor==null)){
//			
//			Assert.notNull(ownerSponsor);
//			Assert.notNull(ownerCustomer);
//			
//		} else {
//			result = new CreditCard();
//			
//			result.setHolderName("");
//			result.setBrandName("");
//			result.setNumber("");
//			result.setExpirationMonth(99);
//			result.setExpirationYear(99);
//			result.setCVV(999);
//
//		}
//		
//		return result;
//	}
//
//	public Collection<CreditCard> findAll(){
//		Collection<CreditCard> collCC = new ArrayList<>();
//		Sponsor ownerSponsor;
//		Customer ownerCustomer;
//
//		ownerSponsor = this.sponsorService.findByPrincipal();
//		ownerCustomer = this.customerService.findByPrincipal();
//		if(!(ownerCustomer==null && ownerSponsor!=null||ownerCustomer!=null && ownerSponsor==null)){		
//			Assert.notNull(ownerSponsor);
//			Assert.notNull(ownerCustomer);
//		}
//		if(ownerCustomer!=null) {
//			collCC = this.customerService.findCreditCardsByCustomerId(ownerCustomer.getId());
//		} else {
//			collCC = this.sponsorService.findCreditCardsBySponsorId(ownerSponsor.getId());
//		}
//
//		return collCC;
//	}
//	
//	public CreditCard findOne(int creditCardId){
//		return this.creditCardRepository.findOne(creditCardId);
//	}
//
//	public CreditCard save (CreditCard creditCard) throws ParseException{
//		Assert.notNull(creditCard);
//		Assert.isTrue(creditCard.getId() != 0);
//		
//		Sponsor ownerSponsor;
//		Customer ownerCustomer;
//		
//		ownerSponsor = this.sponsorService.findByPrincipal();
//		ownerCustomer = this.customerService.findByPrincipal();
//		if(!(ownerCustomer==null && ownerSponsor!=null||ownerCustomer!=null && ownerSponsor==null)){
//			
//			Assert.notNull(ownerSponsor);
//			Assert.notNull(ownerCustomer);
//			
//		} else {
//			String monthYear = creditCard.getExpirationMonth() + " " + creditCard.getExpirationYear();
//			SimpleDateFormat formato = new SimpleDateFormat("MM YY");
//			Date expiration = formato.parse(monthYear);
//			Assert.isTrue(expiration.after(LocalDate.now().toDate()));
//		}
//		return this.creditCardRepository.save(creditCard);
//	}
	
	public void delete(CreditCard creditCard) {
		
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		
//		Integer userAccountId = LoginService.getPrincipal().getId();
//		Assert.isTrue(userAccountId.equals(this.customerService.findByCreditCardId(creditCard).getUserAccount())
//				||userAccountId.equals(this.sponsorService.findByCreditCardId(creditCard).getUserAccount()));
		
		
		//Assert.isTrue(!(Assert.notEmpty(this.sponsorshipService.find))
	}
	
	
	// Other business methods	

}
