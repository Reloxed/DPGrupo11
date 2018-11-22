package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.CreditCard;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	// Supporting Services

	
	
	// Simple CRUD Methods
	
	
	
	// Other business methods
	
	public Customer findByCreditCardId(CreditCard creditCard) {
		Customer res;
		int creditCardId = creditCard.getId();
		
		res = this.customerRepository.findByCreditCardId(creditCardId);
		Assert.notNull(res);

		return res;
	}
	
}
