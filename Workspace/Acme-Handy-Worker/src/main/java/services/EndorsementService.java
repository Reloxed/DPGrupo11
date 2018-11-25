package services;

import java.util.Collection;

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
	@Autowired
	private HandyWorkerService handyWorkerService;
	@Autowired
	private CustomerService customerService;
	
	
	// Constructors ------------------------------------
	public EndorsementService() {
		super();
	}
	
	
	//Simple CRUD methods-------
	public Endorsement create(){
		Endorsement result;
		Endorser endorser;
		
		endorser=this.endorserService.findByPrincipal();
		
		result=new Endorsement();
		result.setSender(endorser);
		
		return result;
		
	}
	public Collection<Endorsement> findAll(){
		Collection<Endorsement> result;
		result=this.endorsementRepository.findAll();
		
		return result;
		
	} 
	
	public Endorsement findOne( final int endorsementId){
		Endorsement result;
		
		result=this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);
		
		return result;

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
	
	
	//checkear que el actor que envía elimina o hace cualquien accion es el sender
	
	/*public void isSender(){
		
	}*/
	
	
	public Double  computeScore(Endorser actor){
		
		return null;
	
	}
	
	
}
