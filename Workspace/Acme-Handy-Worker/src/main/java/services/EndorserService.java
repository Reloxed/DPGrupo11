package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import domain.Actor;
import domain.Customer;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {
	
	// Managed Repository
	
	@Autowired
	private EndorserRepository endorserRepository;
		
		
	// Supporting Services

	@Autowired
	private ActorService actorService;
		
	// Simple CRUD Methods
	
	public Endorser create() {
		Endorser result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.isNull(principal);
		
		result = new Customer();
		
		return result;
	}
	
	public Collection<Endorser> findAll(){
		Collection<Endorser> collEnd = new ArrayList<>();
		collEnd = endorserRepository.findAll();
		return collEnd;
	}
	
	public Endorser findOne(int endorserId) {
		Integer id = endorserId;
		Endorser endorser = endorserRepository.findOne(id);	
		return endorser;
	}
	
	public Endorser save(Endorser endorser) {
		Endorser end;
		end = endorserRepository.save(endorser);
		return end;
	}
	
	// Other business methods

}
