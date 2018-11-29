package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Endorsement;
import domain.Endorser;


import repositories.EndorsementRepository;


@Service
@Transactional
public class EndorsementService {

	//Managed repository-----------
	
	@Autowired
	private EndorsementRepository endorsementRepository;
	
	
	//Supporting services ----------
	
	@Autowired
	private EndorserService endorserService;
	
	// Constructors ------------------------------------
	public EndorsementService() {
		super();
	}
	
	
	//Simple CRUD methods-------
	public Endorsement create(){

		Endorsement result;
		Endorser endorser;
		
		endorser=this.endorserService.findByPrincipal();
		Assert.notNull(endorser);
		
		result=new Endorsement();
	
		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		
		
		return result;
		
	}
	public Collection<Endorsement> findAll(){
		Collection<Endorsement> result;
		result=this.endorsementRepository.findAll();
		
		return result;
		
	} 
	
	public Endorsement findOne(int endorsementId){
		Endorsement result;
		
		result=this.endorsementRepository.findOne(endorsementId);
		
		return result;

	}
	
	public Endorsement save(Endorsement endorsement){
		Endorsement result;
		Endorser principal;
		
		Assert.isTrue(endorsement.getSender()!=null);
		Assert.isTrue(endorsement.getRecipient()!=null);
		Assert.notNull(endorsement.getPublishedMoment());
		Assert.notNull(endorsement.getComment());
		
		principal=this.endorserService.findByPrincipal();
		Assert.isTrue(endorsement.getSender().getId()==principal.getId());
		Assert.notNull(principal);
		Assert.isTrue(endorsement.getId()==0);
		result=this.endorsementRepository.save(endorsement);
		
		return result;
		
	}
	
	public void  delete(Endorsement endorsement){
		Endorser principal;
		
		
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId()!=0);
		principal=this.endorserService.findByPrincipal();
		Assert.notNull(principal);
		
		
		this.endorsementRepository.delete(endorsement);
		
	}
	
	
	
	
	//Other business methods--------
	
	
	
}
