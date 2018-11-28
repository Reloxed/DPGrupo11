package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Endorsement;
import domain.Endorser;

import repositories.CustomerRepository;
import repositories.EndorsementRepository;
import repositories.EndorserRepository;
import repositories.HandyWorkerRepository;
import security.UserAccount;

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
		//comprobar autoridades endorser es un handyworker o un customer?
		//comprobarlo
		Endorsement result;
		Endorser endorser;
		
		endorser=this.endorserService.findByPrincipal();
		Assert.notNull(endorser);
		
		result=new Endorsement();
		result.setSender(endorser);
		result.setRecipient(endorser);
		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		result.setComment("");
		
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
		Assert.notNull(result);
		
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
		Assert.notNull(principal);
		Assert.notNull(endorsement.getId()==0);
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
	
	
	//checkear que el actor que envía elimina o hace cualquien accion es el sender
	
	/*public void isSender(){
		
	}*/
	
	
	public Double  computeScore(Endorser actor){
		
		return null;
	
	}
	
	
}
