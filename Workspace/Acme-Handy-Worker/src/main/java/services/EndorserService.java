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
		
	// Simple CRUD Methods
	
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
	
	// Other business methods

}
