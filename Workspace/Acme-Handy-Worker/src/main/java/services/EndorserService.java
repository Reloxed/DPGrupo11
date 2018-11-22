package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRepository;
import domain.Customer;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {
	
	// Managed Repository
	
	@Autowired
	private EndorserRepository endorserRepository;
		
		
	// Supporting Services

	
		
	// Simple CRUD Methods
	
	public Endorser create() {
		return new Endorser();
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
