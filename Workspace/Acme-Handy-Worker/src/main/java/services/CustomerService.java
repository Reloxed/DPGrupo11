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
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	// Managed Repository

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting Services

	@Autowired
	private MessageBoxService messageBoxService;

	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD Methods

	public Customer create() {
		final Customer result = new Customer();
		Actor principal;

		final Authority authority = new Authority();
		final UserAccount userAccount = new UserAccount();

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isNull(principal);

			return null;

		} catch (final IllegalArgumentException e) {

			result.setMessageBoxes(this.messageBoxService
					.createSystemMessageBoxes());
			result.setComplaints(new ArrayList<Complaint>());
			result.setFixUpTasks(new ArrayList<FixUpTask>());
			result.setCreditCards(new ArrayList<CreditCard>());
			result.setSocialProfiles(new ArrayList<SocialProfile>());
			authority.setAuthority(Authority.CUSTOMER);
			userAccount.addAuthority(authority);
			result.setUserAccount(userAccount);

			return result;
		}

	}

	public Collection<Customer> findAll() {
		Collection<Customer> result = new ArrayList<>();
		result = this.customerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Customer findOne(final int customerId) {
		final Integer id = customerId;
		final Customer result = this.customerRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer cus;

		if (customer.getId() == 0)
			try {
				Actor principal;
				principal = this.actorService.findByPrincipal();
				Assert.isNull(principal);

			} catch (final IllegalArgumentException e) {
				final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				customer.getUserAccount().setPassword(
						passwordEncoder.encodePassword(customer
								.getUserAccount().getPassword(), null));
			}
		else {
			Customer principalC;
			principalC = this.findByPrincipal();
			Assert.notNull(principalC);
			Assert.isTrue(principalC.getUserAccount().equals(
					customer.getUserAccount()));
			Assert.isTrue(customer.getIsSuspicious() == principalC
					.getIsSuspicious());

			if (principalC.getCreditCards().size() > 0)
				Assert.isTrue(customer.getCreditCards().containsAll(
						principalC.getCreditCards()));
		}
		cus = this.customerRepository.save(customer);
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

		result = this.customerRepository
				.findCustomerByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Customer> findByCreditCardId(final int creditCardId) {
		Collection<Customer> res;

		res = this.customerRepository.findByCreditCardId(creditCardId);

		return res;
	}

	public Collection<CreditCard> findCreditCardsByCustomerId(
			final int customerId) {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC = this.customerRepository
				.findCreditCardsByCustomerId(customerId);
		return collCC;
	}

	public Collection<Customer> topThreeCustomersTenPercentMoraThanAverage() {
		final List<Customer> collC = this.customerTenPercentMoraThanAverage();
		if (collC.size() < 3)
			return collC;
		else
			return collC.subList(0, 3);
	}

	public List<Customer> customerTenPercentMoraThanAverage() {
		final List<Customer> collC = this.customerRepository
				.customerTenPercentMoreFixUpTasksThanAverage();
		return collC;
	}

	public Customer findCustomerByApplicationId(final int applicationId) {
		Customer result;

		result = this.customerRepository
				.findCustomerByApplicationId(applicationId);

		return result;
	}

	public List<Customer> findCustomersWithMoreComplaints() {
		List<Customer> res = this.customerRepository
				.findCustomersWithMoreComplaints();

		if (res.size() > 3) {
			res = res.subList(0, 2);
		}
		return res;
	}
}
