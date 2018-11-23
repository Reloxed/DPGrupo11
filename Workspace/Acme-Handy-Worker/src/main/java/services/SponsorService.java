package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.CreditCard;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Sponsor create(){
		return new Sponsor();
	}
	
	public Collection<Sponsor> findAll(){
		Collection<Sponsor> sponsors;
		
		sponsors = this.sponsorRepository.findAll();
		
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
	
	public Sponsor save(Sponsor s){
		Assert.notNull(s);
		
		Sponsor result;
		result = this.sponsorRepository.save(s);
		
		return result;
	}
	
	public void delete(Sponsor s){
		Assert.notNull(s);
		Assert.notNull(this.sponsorRepository.findOne(s.getId()));
		this.sponsorRepository.delete(s);
	}
	
	//Other business methods
	
//	public Sponsor findByCreditCardId(CreditCard creditCard) {
//		Sponsor res;
//		int creditCardId = creditCard.getId();
//		
//		res = this.sponsorRepository.findByCreditCardId(creditCardId);
//		Assert.notNull(res);
//
//		return res;
//	}
	
	public Collection<CreditCard> findCreditCardsBySponsorId (int sponsorId){
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC = sponsorRepository.findCreditCardsBySponsorId(sponsorId);
		return collCC;
	}
	
}
