
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
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
	private SystemConfigurationService	systemConfigurationService;


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
		final Date registeredMoment;
		final FixUpTask fixUpTask;
		final String messageHandyWorker;
		final String messageCustomer;
		final Collection<Application> applications, updated;
		final Collection<String> spamWords;
		final boolean containsSpam;

		Assert.notNull(a);
		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		fixUpTask = a.getFixUpTask();
		if (a.getId() == 0) { // Not saved in database yet
			a.setStatus("PENDING");
			registeredMoment = new Date(System.currentTimeMillis() - 1);
			Assert.isTrue(registeredMoment.after(fixUpTask.getPublishedMoment())); // The fixUpTask must be published.
			a.setRegisteredMoment(registeredMoment);
			a.setComment(new String());
		}

		a.setApplicant(applicant);

		result = this.applicationRepository.save(a);
		Assert.notNull(a);

		// Add application to collection of applications of handyWorker
		applications = applicant.getApplications();
		updated = new ArrayList<Application>(applications);
		updated.add(result);
		applicant.setApplications(updated);

		// Check contain of strings searching spamWords
		/*
		 * containsSpam = false;
		 * spamWords = this.systemConfigurationService
		 */

		// TODO: necesito un metodo de systemConfiguration que me devuelva las palabras spam para comprobar que el contenido de los comentarios no tiene spam

		// TODO: falta el mensaje de notificacion
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
