
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
import domain.HandyWorker;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	// Managed repository ------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services -----------------------------------

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

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

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(complaint.getDescription());
		if (complaint.getAttachements() != null)
			atributosAComprobar.add(complaint.getAttachements());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		result = this.complaintRepository.saveAndFlush(complaint);
		final Collection<Complaint> collCom = principal.getComplaints();
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

	public Collection<Complaint> findComplaintsByReferee() {
		Collection<Complaint> result;
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		result = this.complaintRepository.findComplaintsByReferee(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Complaint> findComplaintsByCustomer() {
		Collection<Complaint> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.complaintRepository.findComplaintsByCustomer(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Complaint> findComplaintsByHandyWorkerId(final int handyWorkerId) {
		HandyWorker principal;
		Collection<Complaint> result;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.complaintRepository.findComplaintsByHandyWorkerId(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Complaint> findNotAssignedComplaints() {
		Collection<Complaint> result;

		result = this.complaintRepository.findNotAssignedComplaints();
		Assert.notNull(result);

		return result;
	}

	public Complaint assignReferee(final Complaint c) {
		Assert.notNull(c);
		Referee principal;
		Complaint saved;
		Collection<Complaint> assigned;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);

		assigned = principal.getComplaints();
		saved = this.complaintRepository.save(c);
		assigned.add(saved);
		Assert.isTrue(assigned.contains(saved));

		return saved;

	}

}
