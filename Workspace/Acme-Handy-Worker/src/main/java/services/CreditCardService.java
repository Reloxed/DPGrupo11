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
		
		// Según sea un customer o un sponsor, cogemos la lista de sus CC 
		if(ownerCustomer!=null) {
			collCC = this.customerService.findCreditCardsByCustomerId(ownerCustomer.getId());
		} else {
			collCC = this.sponsorService.findCreditCardsBySponsorId(ownerSponsor.getId());
		}

		return collCC;
	}
	
	public CreditCard findOne(int creditCardId){
		return this.creditCardRepository.findOne(creditCardId);
	}

	public CreditCard save (CreditCard creditCard) throws ParseException{
		Assert.notNull(creditCard);
		
		CreditCard res;
		Sponsor ownerSponsor;
		Customer ownerCustomer;
		
		ownerSponsor = this.sponsorService.findByPrincipal();
		ownerCustomer = this.customerService.findByPrincipal();
		if(ownerCustomer==null && ownerSponsor==null){
			Assert.notNull(ownerSponsor);
		}

		// Comprobacion de fecha
		String monthYear = creditCard.getExpirationMonth() + " " + creditCard.getExpirationYear();
		SimpleDateFormat formato = new SimpleDateFormat("MM YY");
		Date expiration = formato.parse(monthYear);
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
		
		// Comprobamos que el customer o sponsor que quiere eliminar la CC es el dueño de la misma
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
