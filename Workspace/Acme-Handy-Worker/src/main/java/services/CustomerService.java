
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.MessageBox;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	// Managed Repository

	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting Services

	@Autowired
	private MessageBoxService	messageBoxService;


	// Simple CRUD Methods

	public Customer create() {
		Customer result = null;
		Collection<MessageBox> messageBoxes;
		Actor principal;
		Authority authority = new Authority();
		UserAccount userAccount = new UserAccount();

		
		
		try {
			principal = this.findByPrincipal();
			Assert.isNull(principal);
			
		} catch (IllegalArgumentException e){
			
			result = new Customer();
			
			messageBoxes = this.messageBoxService.createSystemMessageBoxes();

			result.setMessageBoxes(messageBoxes);
			result.setComplaints(new ArrayList<Complaint>());
			result.setFixUpTasks(new ArrayList<FixUpTask>());
			result.setSocialProfiles(new ArrayList<SocialProfile>());
			authority.setAuthority(Authority.CUSTOMER);
			userAccount.addAuthority(authority);
			result.setUserAccount(userAccount);
		}

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> collCus = new ArrayList<>();
		collCus = this.customerRepository.findAll();
		return collCus;
	}

	public Customer findOne(final int customerId) {
		final Integer id = customerId;
		final Customer customer = this.customerRepository.findOne(id);
		return customer;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer cus;

		if (customer.getId() == 0) {
			try {
				Actor principal;
				principal = this.findByPrincipal();
				Assert.isNull(principal);
				
			} catch (IllegalArgumentException e){
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				customer.getUserAccount().setPassword(passwordEncoder.encodePassword(customer.getUserAccount().getPassword(), null));
			}
		} else {
			Customer principalC;
			principalC = this.findByPrincipal();
			Assert.notNull(principalC);
			Assert.isTrue(principalC.getUserAccount().equals(customer.getUserAccount()));
			System.out.println(customer.getIsSuspicious()+"Hi");
			System.out.println(principalC.getIsSuspicious()+"Damn");
			Assert.isTrue(customer.getIsSuspicious() == principalC.getIsSuspicious());
		}
		cus = this.customerRepository.save(customer);
		this.customerRepository.flush();
		return cus;
	}

	// Other business methods

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

	public Customer findByCreditCardId(final int creditCardId) {
		Customer res;

		res = this.customerRepository.findByCreditCardId(creditCardId);

		return res;
	}

	public Collection<CreditCard> findCreditCardsByCustomerId(final int customerId) {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC = this.customerRepository.findCreditCardsByCustomerId(customerId);
		return collCC;
	}

	public Collection<Customer> topThreeCustomersTenPercentMoraThanAverage() {
		List<Customer> collC = this.customerTenPercentMoraThanAverage();
		if(collC.size()<3) {
			return collC;
		} else {
			return collC.subList(0, 3);
		}
	}

	public List<Customer> customerTenPercentMoraThanAverage() {
		final List<Customer> collC = this.customerRepository.customerTenPercentMoreFixUpTasksThanAverage();
		return collC;
	}

	public Customer findCustomerByApplicationId(final int applicationId) {
		Customer result;

		result = this.customerRepository.findCustomerByApplicationId(applicationId);

		return result;
	}
}
