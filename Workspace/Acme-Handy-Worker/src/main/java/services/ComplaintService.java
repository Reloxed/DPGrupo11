
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	// Constructors ------------------------------------

	public ComplaintService() {
		super();
	}

	// CRUD methods -----------------------------------------

	public Complaint create() {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		result = new Complaint();

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Complaint result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		// A complaint cannot be updated once they are saved to the database
		Assert.isTrue(complaint.getId() == 0);
		Assert.notNull(complaint.getFixUpTask());
		Assert.notNull(complaint.getDescription());
		
		complaint.setTicker(this.utilityService.generateTicker());
		complaint.setMoment(new Date(System.currentTimeMillis() - 1));
		
		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(complaint.getDescription());
		if (complaint.getAttachements() != null)
			atributosAComprobar.add(complaint.getAttachements());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}

		result = this.complaintRepository.saveAndFlush(complaint);
		Collection<Complaint> collCom = principal.getComplaints();
		collCom.add(result);
		principal.setComplaints(collCom);
		this.customerService.save(principal);
		
		return result;
	}

	public Collection<Complaint> findAll() {
		Collection<Complaint> result;

		result = this.complaintRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Complaint findOne(final int complaintId) {
		Complaint result;

		result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------
	
	public Collection<Complaint> findComplaintsByHandyWorkerId(int handyWorkerId) {
		final Collection<Complaint> collCom = this.complaintRepository.findComplaintsByHandyWorkerId(handyWorkerId);
		return collCom;
	}
	
}
