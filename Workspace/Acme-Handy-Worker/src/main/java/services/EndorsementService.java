package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorsement;

import repositories.EndorsementRepository;

@Service
@Transactional
public class EndorsementService {

	//Managed repository-----------
	
	@Autowired
	private EndorsementRepository endorsementRepository;
	
	
	//Supporting services ----------
	
	
	
	//Simple CRUD methods-------
	public Endorsement create(){
		return null;
		
	}
	public Collection<Endorsement> findAll(){
		return null;
		
	} 
	

	public Endorsement findOne( final int endorsementId){
		Endorsement result;
		
		result=this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);
		
	}
	
	public Endorsement save(final Endorsement endorsement){
		Endorsement result;
		Endorser endorser;
		
		Assert.isTrue(endorsement.getSender()!=null);
		endorser=this.endorserService.findByPrincipal();
		Assert.notNull(endorser);
		result=this.endorsementRepository.save(endorsement);
		
		return result;
		
	}
	
	public void  delete(final Endorsement endorsement){
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId()!=0);

		
		this.endorsementRepository.delete(endorsement);
		
	}
	
	
	
	
	//Other business methods--------
	
	
	
	
	
	
}
