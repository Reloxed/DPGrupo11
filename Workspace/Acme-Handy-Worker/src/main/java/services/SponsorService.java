package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.MessageBox;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	//Supporting services
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MessageBoxService messageBoxService;
	
	//Simple CRUD Methods
	
	public Sponsor create(){
		Sponsor result;
		Collection<MessageBox> messageBoxes;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.isNull(principal);
		
		result = new Sponsor();
		
		messageBoxes = this.messageBoxService.createSystemMessageBoxes();
		
		result.setIsSuspicious(false);
		result.setMessageBoxes(messageBoxes);
		result.setSocialProfiles(Collections.<SocialProfile> emptyList());
		
		return result;
	}
	
	public Collection<Sponsor> findAll(){
		Collection<Sponsor> sponsors;
		
		sponsors = this.sponsorRepository.findAll();
		Assert.notNull(sponsors);
		
		return sponsors;
	}
	
//	public Collection<Sponsor> find(){
//		
//	}
	
	public Sponsor findOne(int sponsorId){
		Sponsor result;
		
		result = this.sponsorRepository.findOne(sponsorId);
		
		return result;
	}
	
	public Sponsor findByPrincipal(){
		Sponsor result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}
	
	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}
	
	public Sponsor save(Sponsor s){
		Sponsor saved;
		Assert.notNull(s);
		
		if(s.getId() == 0){
			Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			s.getUserAccount().setPassword(passwordEncoder.encodePassword(s.getUserAccount().getPassword(), null));
		} else{
			Sponsor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
		}
		
		saved = this.sponsorRepository.save(s);
		return saved;
	}
	
	//Other business methods
	
	public Sponsor findByCreditCardId(CreditCard creditCard) {
		Sponsor res;
		int creditCardId = creditCard.getId();
		
		res = this.sponsorRepository.findByCreditCardId(creditCardId);
		Assert.notNull(res);

		return res;
	}
	
}
