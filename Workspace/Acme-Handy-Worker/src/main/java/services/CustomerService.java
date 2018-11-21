package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	// Managed Repository
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	// Supporting Services

	
	
	// Simple CRUD Methods
	
	
	
	// Other business methods
	
	
}
