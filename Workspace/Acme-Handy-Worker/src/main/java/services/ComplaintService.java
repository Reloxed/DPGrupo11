
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;

@Service
@Transactional
public class ComplaintService {

	// Managed repository ------------------------------------
	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services -----------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private UtilityService		utilityService;


	// CRUD methods -----------------------------------------

	public Complaint create() {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		result = new Complaint();

		result.setTicker(this.utilityService.generateTicker());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Complaint save(final Complaint c) {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(c.getFixUpTask());
		Assert.notNull(c.getDescription());

		result = this.complaintRepository.save(c);

		return result;
	}

	// Other business methods --------------------------------
}
