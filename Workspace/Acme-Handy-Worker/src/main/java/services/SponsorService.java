package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository
	
	@Autowired
	private SponsorRepository sr;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Sponsor create(){
		return new Sponsor();
	}
	
	public Collection<Sponsor> findAll(){
		Collection<Sponsor> sponsors;
		
		sponsors = this.sr.findAll();
		
		return sponsors;
	}
	
//	public Collection<Sponsor> find(){
//		
//	}
	
	public Sponsor findOne(int sponsorId){
		Sponsor result;
		
		result = this.sr.findOne(sponsorId);
		
		return result;
	}
	
	public Sponsor save(Sponsor s){
		Assert.notNull(s);
		
		Sponsor result;
		result = this.sr.save(s);
		
		return result;
	}
	
	public void delete(Sponsor s){
		Assert.notNull(s);
		Assert.notNull(this.sr.findOne(s.getId()));
		this.sr.delete(s);
	}
	
	//Other business methods
	
}
