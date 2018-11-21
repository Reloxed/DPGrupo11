package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository
	
	@Autowired
	private SponsorshipRepository sr;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Sponsorship create(){
		return new Sponsorship();
	}
	
	public Collection<Sponsorship> findAll(){
		Collection<Sponsorship> sponsorships;
		
		sponsorships = this.sr.findAll();
		Assert.notNull(sponsorships);
		
		return sponsorships;
	}
	
//	public Collection<Sponsorship> find(){
//		
//	}
	
	public Sponsorship findOne(int sponsorshipId){
		Sponsorship result;
		
		Assert.isTrue(sponsorshipId != 0);
		result = this.sr.findOne(sponsorshipId);
		
		return result;
	}
	
	public Sponsorship save(Sponsorship s){
		Assert.notNull(s);
		
		Sponsorship result;
		result = this.sr.save(s);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		
		return result;
	}
	
	public void delete(Sponsorship s){
		Assert.notNull(s);
		Assert.notNull(this.sr.findOne(s.getId()));
		this.sr.delete(s);
	}

	//Other business methods
	
}
