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
	private CreditCardRepository creditCardRepository;

	// Supporting Services

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private UtilityService utilityService;

	// Simple CRUD Methods

	public CreditCard create() {

		final CreditCard result = new CreditCard();
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;

		// Seguimos adelante si el user es un sponsor o customer
		try {
			ownerCustomer = this.customerService.findByPrincipal();

		} catch (IllegalArgumentException e) {

			ownerSponsor = this.sponsorService.findByPrincipal();
		}

		if (ownerCustomer == null && ownerSponsor == null)
			Assert.notNull(ownerSponsor);
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> collCC = new ArrayList<>();
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;

		try {
			ownerSponsor = this.sponsorService.findByPrincipal();

		} catch (IllegalArgumentException e) {
			ownerCustomer = this.customerService.findByPrincipal();
		}
		if (ownerCustomer == null && ownerSponsor == null)
			Assert.notNull(ownerSponsor);

		// Según sea un customer o un sponsor, cogemos la lista de sus CC
		if (ownerCustomer != null)
			collCC = this.customerService
					.findCreditCardsByCustomerId(ownerCustomer.getId());
		else
			collCC = this.sponsorService
					.findCreditCardsBySponsorId(ownerSponsor.getId());

		return collCC;
	}

	public CreditCard findOne(int creditCardId) {
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;
		CreditCard creditCard;

		try {
			ownerSponsor = this.sponsorService.findByPrincipal();

		} catch (IllegalArgumentException e) {
			ownerCustomer = this.customerService.findByPrincipal();
		}
		if (ownerCustomer == null && ownerSponsor == null) {
			Assert.notNull(ownerSponsor);
		}

		// Según sea un customer o un sponsor, vemos si la credit card que pide
		// es suya
		if (ownerCustomer != null) {
			Customer customer;
			customer = this.customerService.findByCreditCardId(creditCardId);
			Assert.notNull(customer);
			Assert.isTrue(customer.equals(ownerCustomer));
			creditCard = this.creditCardRepository.findOne(creditCardId);
		} else {
			Sponsor sponsor;
			sponsor = this.sponsorService.findByCreditCardId(creditCardId);
			Assert.notNull(ownerSponsor);
			Assert.notNull(sponsor);
			Assert.isTrue(sponsor.equals(ownerSponsor));
			creditCard = this.creditCardRepository.findOne(creditCardId);
		}

		return creditCard;
	}

	public CreditCard save (CreditCard creditCard){

		Assert.notNull(creditCard);

		CreditCard res;
		Sponsor ownerSponsor = null;
		Customer ownerCustomer = null;
		Calendar expiration;

		try {
			ownerSponsor = this.sponsorService.findByPrincipal();
			
		} catch (IllegalArgumentException e){
			ownerCustomer = this.customerService.findByPrincipal();
		}
		if (ownerCustomer == null && ownerSponsor == null)
			Assert.notNull(ownerSponsor);

		// Comprobacion de fecha
		expiration = Calendar.getInstance();
		expiration.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth()+1, 1);
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

		// Comprobamos que el customer o sponsor que quiere eliminar la CC es el
		// dueño de la misma
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(this.customerService
				.findByCreditCardId(creditCard.getId()).getUserAccount())
				|| userAccount.equals(this.sponsorService.findByCreditCardId(
						creditCard.getId()).getUserAccount()));

		// Comprobamos que no hay ninguna application ni sponsorship que tenga
		// asociada la CC a eliminar
		Assert.isNull(this.applicationService.findByCreditCardId(creditCard
				.getId()));
		Assert.isNull(this.sponsorshipService.findByCreditCardId(creditCard
				.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	// Other business methods

}
