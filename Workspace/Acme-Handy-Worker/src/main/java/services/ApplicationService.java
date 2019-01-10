
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
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
	private ApplicationRepository	applicationRepository;

	// Supporting services --------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;


	// Constructors ------------------------------------

	public ApplicationService() {
		super();
	}

	// Simple CRUD methods ---------------------------

	public Application create() {
		final Application result;
		HandyWorker applicant;

		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		result = new Application();

		result.setApplicant(this.handyWorkerService.findByPrincipal());
		result.setStatus("PENDING");
		return result;
	}

	public Application save(final Application application) {
		HandyWorker applicant = null;
		Application result;
		Actor actor;
		Customer customer = null;
		Date registeredMoment;
		FixUpTask fixUpTask;
		Collection<Application> applications, updated;
		final String bodyHW, bodyHWSpa, bodyCustomer, bodyCustomerSpa;

		Assert.notNull(application);
		Assert.notNull(application.getOfferedPrice());
		Assert.notNull(application.getComments());
		fixUpTask = application.getFixUpTask();
		Assert.notNull(fixUpTask);

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(application.getComments());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);

		if (actor instanceof HandyWorker) {
			applicant = this.handyWorkerService.findByPrincipal();
			Assert.notNull(applicant);

			if (application.getId() == 0) { // Not saved in database yet
				application.setStatus("PENDING");
				registeredMoment = new Date(System.currentTimeMillis() - 1);
				application.setApplicant(applicant);
				Assert.isTrue(registeredMoment.after(fixUpTask.getPublishedMoment())); // The fixUpTask must be published.
				application.setRegisteredMoment(registeredMoment);

			} else {

				Assert.isTrue(application.getFixUpTask().equals(this.findOne(application.getId()).getFixUpTask()));
				Assert.isTrue(application.getApplicant().equals(applicant));
				Assert.isTrue(application.getRegisteredMoment().equals(this.findOne(application.getId()).getRegisteredMoment()));
				Assert.isTrue(application.getStatus().equals(this.findOne(application.getId()).getStatus()));
				Assert.isTrue(application.getOfferedPrice() == this.findOne(application.getId()).getOfferedPrice());
			}

			if (containsSpam)
				applicant.setIsSuspicious(true);

		} else if (actor instanceof Customer) {

			customer = this.customerService.findByPrincipal();
			Assert.notNull(customer);

			Assert.isTrue(application.getFixUpTask().equals(this.findOne(application.getId()).getFixUpTask()));
			Assert.isTrue(application.getApplicant().equals(this.findOne(application.getId()).getApplicant()));
			Assert.isTrue(application.getRegisteredMoment().equals(this.findOne(application.getId()).getRegisteredMoment()));
			Assert.isTrue(application.getOfferedPrice() == this.findOne(application.getId()).getOfferedPrice());

			if (containsSpam)
				customer.setIsSuspicious(true);
		}

		if (application.getStatus().equals("ACCEPTED"))
			Assert.notNull(application.getCreditCard());

		result = this.applicationRepository.saveAndFlush(application);
		Assert.notNull(application);
		this.applicationRepository.flush();

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

		bodyHW = "The status of your application of the fix up task which ticker is" + result.getFixUpTask().getTicker() + "has been changed to " + result.getStatus();
		bodyCustomer = "The status of application of the fix up task which ticker is" + result.getFixUpTask().getTicker() + "has been changed to " + result.getStatus();
		bodyHWSpa = "El estado de su solicitud de la chapuza cuyo ticker es " + result.getFixUpTask().getTicker() + "ha sido cambiado a " + result.getStatus();
		bodyCustomerSpa = "El estado de la solicitud de la chapuza cuyo ticker es " + result.getFixUpTask().getTicker() + "ha sido cambiado a " + result.getStatus();

		this.messageService.createAndSaveStatus(applicant, bodyHW, result.getRegisteredMoment());
		this.messageService.createAndSaveStatus(applicant, bodyHWSpa, result.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(result.getId()), bodyCustomer, result.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(result.getId()), bodyCustomerSpa, result.getRegisteredMoment());
		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result = new ArrayList<>();

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

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

		bodyHandyWorker = "The status of your application of the fix up task with ticker number" + saved.getFixUpTask().getTicker() + "has been changed to " + saved.getStatus();
		bodyCustomer = "The status of application of the fix up task with ticker number" + saved.getFixUpTask().getTicker() + "has been changed to " + saved.getStatus();
		this.messageService.createAndSaveStatus(a.getApplicant(), bodyHandyWorker, a.getRegisteredMoment());
		this.messageService.createAndSaveStatus(this.customerService.findCustomerByApplicationId(a.getId()), bodyCustomer, a.getRegisteredMoment());
	}

	public Collection<Application> findAllApplicationsByHandyWorker(final int handyWorkerId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllApplicationsByHandyWorker(handyWorkerId);

		return result;
	}

	public Collection<Application> findAllApplicationsByCustomer() {
		Collection<Application> result;
		Customer principal;
		
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		result = this.applicationRepository.findAllApplicationsByCustomer(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findAllApplicationsByFixUpTask(final int fixUptaskId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllApplicationsByFixUpTask(fixUptaskId);

		return result;
	}

	public Double ratioStatusApplications(final String status) {
		Double result;

		result = this.applicationRepository.ratioStatusApplications(status);

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
