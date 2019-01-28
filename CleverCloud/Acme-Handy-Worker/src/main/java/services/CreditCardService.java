
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.CreditCard;
import domain.Customer;
import domain.Sponsor;

@Service
@Transactional
public class CreditCardService {

	// Managed Repository

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting Services

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private UtilityService			utilityService;
	
	@Autowired
	private ActorService			actorService;


	// Constructors ------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD Methods

	public CreditCard create() {
		Actor principal;
		CreditCard result;
		boolean validActor = false;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);	

		if (principal instanceof Customer || principal instanceof Sponsor) {
			validActor = true;
		}
		
		Assert.isTrue(validActor);		
		result = new CreditCard();
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result = new ArrayList<>();

		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);
		return result;
	}

	public CreditCard save(final CreditCard creditCard) {

		Assert.notNull(creditCard);

		CreditCard res;
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;
		Calendar expiration;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);	

		if (principal instanceof Customer) {
			
			ownerCustomer = this.customerService.findByPrincipal();
			
		} else if (principal instanceof Sponsor) {
			
			ownerSponsor = this.sponsorService.findByPrincipal();
			
		}
		if (ownerCustomer == null && ownerSponsor == null)
			Assert.notNull(ownerSponsor);

		// Comprobacion de fecha
		expiration = Calendar.getInstance();
		expiration.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() + 1, 1);
		Assert.isTrue(expiration.getTime().before(LocalDate.now().toDate()));

		// Comprobacion de que el tipo de tarjeta es uno de los almacenados en systemConf
		Assert.isTrue(this.utilityService.getCreditCardMakes().contains(creditCard.getBrandName()));

		res = this.creditCardRepository.save(creditCard);
		Assert.notNull(res);
		this.creditCardRepository.flush();

		return res;
		
	}

//	public void delete(final CreditCard creditCard) {
//		Assert.notNull(creditCard);
//		Assert.isTrue(creditCard.getId() != 0);
//		Collection<UserAccount> collUA = new HashSet<>();
//		Collection<Customer> collCus = null;
//		Collection<Sponsor> collSpo = null;
//
//		// Comprobamos que el customer o sponsor que quiere eliminar la CC es el
//		// due�o de la misma	
//		final UserAccount userAccount = LoginService.getPrincipal();
//		collCus = this.customerService.findByCreditCardId(creditCard.getId());
//		collSpo = this.sponsorService.findByCreditCardId(creditCard.getId());
//		
//		for(Customer customer: collCus){
//			collUA.add(customer.getUserAccount());
//		}
//		for(Sponsor sponsor: collSpo){
//			collUA.add(sponsor.getUserAccount());
//		}
//		
//		Assert.isTrue(collUA.contains(userAccount) || collSpo.contains(userAccount));
//
//		// Comprobamos que no hay ninguna application ni sponsorship que tenga
//		// asociada la CC a eliminar
//		Assert.isNull(this.applicationService.findByCreditCardId(creditCard.getId()));
//		Assert.isNull(this.sponsorshipService.findByCreditCardId(creditCard.getId()));
//
//		this.creditCardRepository.delete(creditCard);
//	}

	// Other business methods

}