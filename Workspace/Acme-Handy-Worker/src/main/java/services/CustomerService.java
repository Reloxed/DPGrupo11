package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.HandyWorker;

@Service
@Transactional
public class CustomerService {

	// Managed Repository

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting Services

	// Simple CRUD Methods

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

}
