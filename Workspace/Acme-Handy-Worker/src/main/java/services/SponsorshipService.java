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
		
		return sponsorships;
	}
	
//	public Collection<Sponsorship> find(){
//		
//	}
	
	public Sponsorship findOne(int sponsorshipId){
		Sponsorship result;
		
		result = this.sr.findOne(sponsorshipId);
		
		return result;
	}
	
	public Sponsorship save(Sponsorship s){
		Assert.notNull(s);
		
		Sponsorship result;
		result = this.sr.save(s);
		
		return result;
	}
	
	public void delete(Sponsorship s){
		Assert.notNull(s);
		Assert.notNull(this.sr.findOne(s.getId()));
		this.sr.delete(s);
	}

	//Other business methods
	
}
