
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;

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

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

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

		result.setTicker(this.utilityService.generateTicker());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		// A complaint cannot be updated once they are saved to the database
		Assert.isTrue(complaint.getId() == 0);
		Assert.notNull(customer);
		Assert.notNull(complaint.getFixUpTask());
		Assert.notNull(complaint.getDescription());
		
		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] description = complaint.getDescription().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : description)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				customer.setIsSuspicious(true);
				break;
			}
		}

		result = this.complaintRepository.saveAndFlush(complaint);
		Collection<Complaint> collCom = customer.getComplaints();
		collCom.add(result);
		customer.setComplaints(collCom);
		this.customerService.save(customer);
		

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
	
	public Collection<Complaint> findAllComplaintsInvolvedByHW(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		
		final Collection<Complaint> result=new ArrayList<Complaint>() ;
		final Collection<Application> applications = handyWorker.getApplications();
		
		for (final Application a : applications){
			for (final Complaint c : a.getFixUpTask().getComplaints()){
				result.add(c);
				}
		}
		return result;
	}
}
