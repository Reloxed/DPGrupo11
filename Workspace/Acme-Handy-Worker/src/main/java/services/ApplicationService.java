
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Handy Worker : List, show, create
	// Customer : List, update

	// Simple CRUD methods ---------------------------
	public Application create() {
		final Application result;
		HandyWorker applicant;

		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		result = new Application();
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application a) {
		final HandyWorker applicant;
		final Application result;
		final CreditCard creditCard;
		final Date registeredMoment;
		final FixUpTask fixUpTask;
		final String messageHandyWorker;
		final String messageCustomer;

		Assert.notNull(a);
		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		fixUpTask = a.getFixUpTask();
		result = null;
		if (a.getId() == 0) { // Not saved in database yet
			a.setStatus("PENDING");
			registeredMoment = new Date(System.currentTimeMillis() - 1);
			Assert.isTrue(registeredMoment.after(fixUpTask.getPublishedMoment())); // The fixUpTask must be published.

			//creditCard = 
		}

		return result;
	}
	// Other business methods --------------------------

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

}
