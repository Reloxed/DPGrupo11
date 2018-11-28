package services;

import java.text.ParseException;
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
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.Sponsor;
import domain.Sponsorship;

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
	private SystemConfigurationService systemConfigurationService;
	
	// Simple CRUD Methods
	
	public CreditCard create() {
		
		final CreditCard result = new CreditCard();
		Sponsor ownerSponsor;
		Customer ownerCustomer;

		// Seguimos adelante si el user es un sponsor o customer
		ownerSponsor = this.sponsorService.findByPrincipal();
		ownerCustomer = this.customerService.findByPrincipal();
		if(ownerCustomer==null && ownerSponsor==null){		
			Assert.notNull(ownerSponsor);
		}		
		return result;
	}

	public Collection<CreditCard> findAll(){
		Collection<CreditCard> collCC = new ArrayList<>();
		Sponsor ownerSponsor;
		Customer ownerCustomer;

		ownerSponsor = this.sponsorService.findByPrincipal();
		ownerCustomer = this.customerService.findByPrincipal();
		if(ownerCustomer==null && ownerSponsor==null){
			Assert.notNull(ownerSponsor);
		}
		
		// Seg�n sea un customer o un sponsor, cogemos la lista de sus CC 
		if(ownerCustomer!=null) {
			collCC = this.customerService.findCreditCardsByCustomerId(ownerCustomer.getId());
		} else {
			collCC = this.sponsorService.findCreditCardsBySponsorId(ownerSponsor.getId());
		}

		return collCC;
	}
	
	public CreditCard findOne(int creditCardId){
		Sponsor ownerSponsor;
		Customer ownerCustomer;
		CreditCard creditCard;

		ownerSponsor = this.sponsorService.findByPrincipal();
		ownerCustomer = this.customerService.findByPrincipal();
		if(ownerCustomer==null && ownerSponsor==null){
			Assert.notNull(ownerSponsor);
		}
		
		// Seg�n sea un customer o un sponsor, vemos si la credit card que pide es suya 
		if(ownerCustomer!=null) {
			Customer customer;
			customer = this.customerService.findByCreditCardId(creditCardId);
			Assert.isTrue(customer.equals(ownerCustomer));
			creditCard = this.findOne(creditCardId);
		} else {
			Sponsor sponsor;
			sponsor = this.sponsorService.findByCreditCardId(creditCardId);
			Assert.isTrue(sponsor.equals(ownerCustomer));
			creditCard = this.findOne(creditCardId);
		}

		return creditCard;
	}

	public CreditCard save (CreditCard creditCard){
		Assert.notNull(creditCard);
		
		CreditCard res;
		Sponsor ownerSponsor;
		Customer ownerCustomer;
		Calendar expiration;
		
		ownerSponsor = this.sponsorService.findByPrincipal();
		ownerCustomer = this.customerService.findByPrincipal();
		if(ownerCustomer==null && ownerSponsor==null){
			Assert.notNull(ownerSponsor);
		}

		// Comprobacion de fecha
		expiration = Calendar.getInstance();
		expiration.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth(), 0);
		Assert.isTrue(expiration.after(LocalDate.now().toDate()));
		
		
		// Comprobacion de que el tipo de tarjeta es uno de los almacenados en systemConf
		Assert.isTrue(systemConfigurationService.findMySystemConfiguration().getListCreditCardMakes().contains(creditCard.getBrandName()));
		
		res = this.creditCardRepository.save(creditCard);
		this.creditCardRepository.flush();
		
		//Metemos la creditCard en la application o sponsorship correspondiente
		if(ownerCustomer != null){
			if (creditCard.getId()==0) {
				for(FixUpTask fixUpTasks : ownerCustomer.getFixUpTasks()) {
					for(Application application : fixUpTasks.getApplications()) {
						if(application.getCreditCard()==null && application.getStatus().equals("ACCEPTED")) {
							application.setCreditCard(creditCard);
							this.applicationService.save(application);
						}
					}
				}
			}
		} else if (ownerSponsor != null){
			if (creditCard.getId()==0) {
				for(Sponsorship sponsorship : ownerSponsor.getSponsorships()) {
					if(sponsorship.getCreditCard()==null) {
						sponsorship.setCreditCard(creditCard);
						this.sponsorshipService.save(sponsorship);
					}
				}
			}
		}
		return res;
	}
	
	public void delete(CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		
		// Comprobamos que el customer o sponsor que quiere eliminar la CC es el due�o de la misma
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(this.customerService.findByCreditCardId(creditCard.getId()).getUserAccount())
				||userAccount.equals(this.sponsorService.findByCreditCardId(creditCard.getId()).getUserAccount()));
		
		// Comprobamos que no hay ninguna application ni sponsorship que tenga asociada la CC a eliminar
		Assert.isNull(applicationService.findByCreditCardId(creditCard.getId()));
		Assert.isNull(sponsorshipService.findByCreditCardId(creditCard.getId()));
		
		creditCardRepository.delete(creditCard);
	}
	
	
	// Other business methods	

}
