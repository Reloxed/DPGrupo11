package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;

import domain.Customer;
import domain.HandyWorker;
import domain.Administrator;
import domain.CreditCard;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting Services

	private AdministratorService administratorService;
	
	// Simple CRUD Methods
	
	public Customer create() {
		return new Customer();
	}
	
	public Collection<Customer> findAll(){
		Collection<Customer> collCus = new ArrayList<>();
		collCus = customerRepository.findAll();
		return collCus;
	}
	
	public Customer findOne(int customerId) {
		Integer id = customerId;
		Customer customer = customerRepository.findOne(id);	
		return customer;
	}
	
	public Customer save(Customer customer) {
		Customer cus;
		cus = customerRepository.save(customer);
		return cus;
	}
	
	public void Delete (Customer customer) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		customerRepository.delete(customer);
	}
	
	// Other business methods
	
	public Collection<CreditCard> findCreditCardsByCustomerId (int customerId){
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC = customerRepository.findCreditCardsByCustomerId(customerId);
		return collCC;
	}
	
	public Customer findByPrincipal() {
		Customer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findCustomerByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Customer findCustomerByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}
}
