
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------

	@Autowired
	private ApplicationRepository		applicationRepository;

	// Supporting services --------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private MessageService				messageService;


	// Simple CRUD methods ---------------------------

	public Application create() {
		final Application result;
		HandyWorker applicant;

		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		result = new Application();
		result.setRegisteredMoment(new Date(System.currentTimeMillis() - 1));
		result.setApplicant(applicant);
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application a) {
		HandyWorker applicant;
		Application result;
		Date registeredMoment;
		FixUpTask fixUpTask;
		Collection<Application> applications, updated;
		String[] spamWords, comments;
		final String bodyHW, bodyCustomer;
		boolean containsSpam;

		Assert.notNull(a);
		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);
		fixUpTask = a.getFixUpTask();

		if (a.getId() == 0) { // Not saved in database yet
			a.setStatus("PENDING");
			registeredMoment = new Date(System.currentTimeMillis() - 1);
			Assert.isTrue(registeredMoment.after(fixUpTask.getPublishedMoment())); // The fixUpTask must be published.
			a.setRegisteredMoment(registeredMoment);
			a.setComments(new String());
		}

		a.setApplicant(applicant);

		result = this.applicationRepository.saveAndFlush(a);
		Assert.notNull(a);

		// Add application to collection of applications of handyWorker
		applications = applicant.getApplications();
		updated = new ArrayList<Application>(applications);
		updated.add(result);
		applicant.setApplications(updated);

		// Add application to collection of applications of fixUpTask
		applications = fixUpTask.getApplications();
		updated = new ArrayList<Application>(applications);
		updated.add(result);
		fixUpTask.setApplications(updated);

		// Check contain of strings searching spamWords

		containsSpam = false;
		spamWords = this.systemConfigurationService.findSpamWords().split(",");
		comments = result.getComments().split(",");
		for (final String word : spamWords) {

			for (final String comment : comments)
				if (comment.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				applicant.setIsSuspicious(true);
				break;
			}

		}

		bodyHW = "The status of your application of the fix up task whose ticker is" + result.getFixUpTask().getTicker() + "has been changed to " + result.getStatus();
		bodyCustomer = "The status of application of the fix up task whose ticker is" + result.getFixUpTask().getTicker() + "has been changed to " + result.getStatus();
		this.messageService.createAndSaveStatus(applicant, bodyHW, result.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(result.getId()), bodyCustomer, result.getRegisteredMoment());

		return result;
	}
	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	// Other business methods --------------------------

	public void accept(final Application a, final CreditCard creditCard) {
		final Customer customer;
		final String bodyCustomer, bodyHandyWorker;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		Assert.isTrue(a.getFixUpTask().getApplications().contains(a));
		Assert.isTrue(a.getStatus() == "PENDING");
		a.setStatus("ACCEPTED");
		this.applicationRepository.saveAndFlush(a);

		bodyHandyWorker = "The status of your application of the fix up task whose ticker is" + a.getFixUpTask().getTicker() + "has been changed to " + a.getStatus();
		bodyCustomer = "The status of application of the fix up task whose ticker is" + a.getFixUpTask().getTicker() + "has been changed to " + a.getStatus();
		this.messageService.createAndSaveStatus(a.getApplicant(), bodyHandyWorker, a.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(a.getId()), bodyCustomer, a.getRegisteredMoment());

		if (a.getStatus() == "ACCEPTED")
			a.setCreditCard(creditCard);

	}

	public void reject(final Application a) {
		final Customer customer;
		final String bodyCustomer, bodyHandyWorker;
		Application saved;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		Assert.isTrue(a.getFixUpTask().getApplications().contains(a));
		Assert.isTrue(a.getStatus() == "PENDING");
		a.setStatus("REJECTED");
		saved = this.applicationRepository.saveAndFlush(a);

		bodyHandyWorker = "The status of your application of the fix up task whose ticker is" + saved.getFixUpTask().getTicker() + "has been changed to " + saved.getStatus();
		bodyCustomer = "The status of application of the fix up task whose ticker is" + saved.getFixUpTask().getTicker() + "has been changed to " + saved.getStatus();
		this.messageService.createAndSaveStatus(a.getApplicant(), bodyHandyWorker, a.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(a.getId()), bodyCustomer, a.getRegisteredMoment());
	}

	public Collection<Application> findAllApplicationsByHandyWorker(final int handyWorkerId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllApplicationsByHandyWorker(handyWorkerId);

		return result;
	}

	public Collection<Application> findAllApplicationsByFixUpTask(final int fixUptaskId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllApplicationsByFixUpTask(fixUptaskId);

		return result;
	}

	public Double ratioPendingApplications() {
		final Double result;

		result = this.applicationRepository.ratioPendingApplications();

		return result;
	}

	public Double ratioAcceptedApplications() {
		Double result;

		result = this.applicationRepository.ratioAcceptedApplications();

		return result;
	}

	public Double ratioRejectedApplications() {
		Double result;

		result = this.applicationRepository.ratioRejectedApplications();

		return result;
	}

	public Double ratioPendingApplicationsElapsedPeriod() {
		Double result;

		result = this.applicationRepository.ratioPendingApplicationsElapsedPeriod();

		return result;
	}

	public Double[] findDataApplicationsOfferedPrice() {
		Double[] result;

		result = this.applicationRepository.findDataApplicationsOfferedPrice();

		return result;
	}

	public Collection<Application> findByCreditCardId(final int creditCardId) {
		Collection<Application> res = new ArrayList<>();

		res = this.applicationRepository.findByCreditCardId(creditCardId);

		return res;
	}

}
