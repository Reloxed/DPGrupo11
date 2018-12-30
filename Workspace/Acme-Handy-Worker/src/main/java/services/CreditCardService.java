
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.LoginService;
import security.UserAccount;
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
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors ------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD Methods

	public CreditCard create() {

		final CreditCard result = new CreditCard();
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;

		// Seguimos adelante si el user es un sponsor o customer
		try {
			ownerCustomer = this.customerService.findByPrincipal();

		} catch (final IllegalArgumentException e) {

			ownerSponsor = this.sponsorService.findByPrincipal();
		}

		if (ownerCustomer == null && ownerSponsor == null)
			Assert.notNull(ownerSponsor);
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

		try {
			ownerSponsor = this.sponsorService.findByPrincipal();

		} catch (final IllegalArgumentException e) {
			ownerCustomer = this.customerService.findByPrincipal();
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

		//		//Metemos la creditCard en la application o sponsorship correspondiente
		//		if (ownerCustomer != null) {
		//			if (creditCard.getId() == 0)
		//				for (final FixUpTask fixUpTasks : ownerCustomer.getFixUpTasks())
		//					for (final Application application : fixUpTasks.getApplications())
		//						if (application.getCreditCard() == null && application.getStatus().equals("ACCEPTED")) {
		//							application.setCreditCard(creditCard);
		//							this.applicationService.save(application);
		//						}
		//		} else if (ownerSponsor != null)
		//			if (creditCard.getId() == 0)
		//				for (final Sponsorship sponsorship : ownerSponsor.getSponsorships())
		//					if (sponsorship.getCreditCard() == null) {
		//						sponsorship.setCreditCard(creditCard);
		//						this.sponsorshipService.save(sponsorship);
		//					}
		return res;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Collection<UserAccount> collUA = new HashSet<>();
		Collection<Customer> collCus = null;
		Collection<Sponsor> collSpo = null;

		// Comprobamos que el customer o sponsor que quiere eliminar la CC es el
		// dueño de la misma
		final UserAccount userAccount = LoginService.getPrincipal();
		collCus = this.customerService.findByCreditCardId(creditCard.getId());
		collSpo = this.sponsorService.findByCreditCardId(creditCard.getId());
		for(Customer customer: collCus){
			collUA.add(customer.getUserAccount());
		}
		for(Sponsor sponsor: collSpo){
			collUA.add(sponsor.getUserAccount());
		}
		
		Assert.isTrue(collUA.contains(userAccount) || collSpo.contains(userAccount));

		// Comprobamos que no hay ninguna application ni sponsorship que tenga
		// asociada la CC a eliminar
		Assert.isNull(this.applicationService.findByCreditCardId(creditCard.getId()));
		Assert.isNull(this.sponsorshipService.findByCreditCardId(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	// Other business methods

}
