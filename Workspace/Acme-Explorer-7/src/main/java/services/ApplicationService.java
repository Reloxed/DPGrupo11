
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Administrator;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Trip;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private ExplorerService			explorerService;
	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;


	// Functions

	private CreditCard generateFictitiousCreditCard() {
		final CreditCard result = new CreditCard();
		result.setHolderName("FICTICIA");
		result.setBrandName("FICTICIA");
		result.setNumber("0000000000000000");
		result.setExpirationMonth(1);
		result.setExpirationYear(10);
		result.setCVV(100);
		return result;
	}

	// Constructors

	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {
		Application result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Application();
		Assert.notNull(result);

		return result;
	}

	// Explorers must be able to apply for a trip
	public Application save(final Application a) {

		Explorer principal;
		Application result;
		CreditCard creditCard;
		Date moment;
		Trip trip;
		Collection<Application> applications, updated;
		Collection<String> spamwords;
		String bodyForExplorer;
		String bodyForManager;

		Assert.notNull(a);

		principal = this.explorerService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an explorer

		trip = a.getTrip();

		if (a.getId() == 0) {
			a.setStatus("PENDING"); // When an application is made, the initial status is "PENDING"	
			moment = new Date(System.currentTimeMillis() - 1);

			Assert.isTrue(moment.after(trip.getPublicationDate())); // The trip should be published. Therefore, an unpublished trip
																	// has no applications
			creditCard = this.generateFictitiousCreditCard();
			a.setCreditcard(creditCard);
			a.setRejectionReason(null);
			a.setMoment(moment);
			a.setComments(new ArrayList<String>());

		}

		a.setApplicant(principal);

		result = this.applicationRepository.save(a);
		Assert.notNull(result);

		applications = principal.getApplications();
		updated = new ArrayList<Application>(applications);
		updated.add(result);
		principal.setApplications(updated);

		applications = trip.getApplications();
		updated = new ArrayList<Application>(applications);
		updated.add(result);
		trip.setApplications(updated);

		boolean isSpam = false;
		spamwords = this.customisationService.find().getSpamWords();
		for (final String word : spamwords) {
			for (final String comment : result.getComments())
				if (comment.toLowerCase().contains(word.toLowerCase())) {
					isSpam = true;
					break;
				}
			if (isSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		//Creamos una notificación para el explorer y el manager implicado en la application
		bodyForExplorer = "Your application for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getApplicant(), bodyForExplorer, a.getMoment());

		bodyForManager = "The application of " + a.getApplicant().getUserAccount().getUsername() + " for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getTrip().getManager(), bodyForManager, a.getMoment());

		return result;

	}

	public Collection<Application> findAll() {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Application> result;
		result = this.applicationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Application> findByPrincipal() {
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Application> result;
		result = this.applicationRepository.findByExplorer(principal.getId());
		Assert.notNull(result);
		return result;

	}

	public Collection<Application> findCancellableApplicationsByPrincipal() {
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Application> result;
		result = this.applicationRepository.findCancellableApplicationsByExplorer(principal.getId());
		Assert.notNull(result);
		return result;

	}

	public Application findOne(final int applicationId) {
		Application result;
		result = this.applicationRepository.findOne(applicationId);
		return result;
	}

	// Other business methods

	// Managers must be able to list the applications for the trips that they manage
	public Collection<Application> findByManager() {
		Manager principal;
		Collection<Application> result;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager

		result = this.applicationRepository.findByManagerId(principal.getId());
		Assert.notNull(result);

		return result;

	}

	// Managers must be able to change the status of an application for a trip they manage from "PENDING" to "REJECTED"
	public void reject(final Application a) {
		Manager principal;
		String bodyForExplorer;
		String bodyForManager;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		final String reason = a.getRejectionReason();

		//	Assert.notNull(reason);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(this.findByManager().contains(a));
		Assert.isTrue(a.getStatus().equals("PENDING"));

		Assert.isTrue(!reason.isEmpty());
		a.setStatus("REJECTED");
		this.applicationRepository.save(a);

		//Creamos una notificación para el explorer y el manager implicado en la application
		bodyForExplorer = "Your application for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getApplicant(), bodyForExplorer, a.getMoment());

		bodyForManager = "The application of " + a.getApplicant().getUserAccount().getUsername() + " for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getTrip().getManager(), bodyForManager, a.getMoment());
	}

	// Managers must be able to change the status of an application for a trip they manage from "PENDING" to "DUE"
	public void expect(final Application a) {
		Manager principal;
		String bodyForExplorer;
		String bodyForManager;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		principal = this.managerService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(this.findByManager().contains(a));
		Assert.isTrue(a.getStatus().equals("PENDING"));
		a.setStatus("DUE");

		this.applicationRepository.save(a);

		//Creamos una notificación para el explorer y el manager implicado en la application
		bodyForExplorer = "Your application for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getApplicant(), bodyForExplorer, a.getMoment());

		bodyForManager = "The application of " + a.getApplicant().getUserAccount().getUsername() + " for the trip " + a.getTrip().getTitle() + "has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getTrip().getManager(), bodyForManager, a.getMoment());
	}

	// Explorers must be able to enter a credit card to get an application accepted, as long as its status is "DUE"
	public void accept(final Application a) {
		Explorer principal;
		String bodyForExplorer;
		String bodyForManager;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		principal = this.explorerService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an explorer

		Assert.isTrue(principal.getApplications().contains(a));
		Assert.isTrue(a.getStatus().equals("DUE"));
		Assert.isTrue(!a.getCreditcard().getHolderName().equals("FICTICIA"));

		a.setStatus("ACCEPTED");

		this.applicationRepository.save(a);

		//Creamos una notificación para el explorer y el manager implicado en la application
		bodyForExplorer = "Your application for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getApplicant(), bodyForExplorer, a.getMoment());

		bodyForManager = "The application of " + a.getApplicant().getUserAccount().getUsername() + " for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getTrip().getManager(), bodyForManager, a.getMoment());
	}

	// Explorers must be able to list the applications that he or she's made, grouped by status
	public Map<String, List<Application>> groupByStatus() {
		final Map<String, List<Application>> result = new HashMap<String, List<Application>>();
		final Explorer principal;
		final Collection<Application> applications;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		applications = this.findByPrincipal();
		Assert.notNull(applications);
		for (final Application a : applications)
			if (result.containsKey(a.getStatus()))
				result.get(a.getStatus()).add(a);
			else {
				final List<Application> l = new ArrayList<Application>();
				l.add(a);
				result.put(a.getStatus(), l);
			}

		if (!result.containsKey("ACCEPTED"))
			result.put("ACCEPTED", new ArrayList<Application>());
		if (!result.containsKey("PENDING"))
			result.put("PENDING", new ArrayList<Application>());
		if (!result.containsKey("DUE"))
			result.put("DUE", new ArrayList<Application>());
		if (!result.containsKey("REJECTED"))
			result.put("REJECTED", new ArrayList<Application>());
		if (!result.containsKey("CANCELLED"))
			result.put("CANCELLED", new ArrayList<Application>());

		return result;
	}

	// Explorers must be able to cancel a trip with status "ACCEPTED" as long as its starting date's not passed.
	public void cancel(final Application a) {
		Explorer principal;
		Date currentMoment;
		String bodyForExplorer;
		String bodyForManager;

		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		principal = this.explorerService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an explorer
		Assert.isTrue(principal.getApplications().contains(a));
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));

		currentMoment = new Date(System.currentTimeMillis() - 1);

		Assert.isTrue(a.getTrip().getStartDate().after(currentMoment));

		a.setStatus("CANCELLED");

		this.applicationRepository.save(a);

		//Creamos una notificación para el explorer y el manager implicado en la application
		bodyForExplorer = "Your application for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getApplicant(), bodyForExplorer, a.getMoment());

		bodyForManager = "The application of " + a.getApplicant().getUserAccount().getUsername() + " for the trip " + a.getTrip().getTitle() + " has changed to status " + a.getStatus();
		this.messageService.createAndSaveNotificationStatusApplicationChanged(a.getTrip().getManager(), bodyForManager, a.getMoment());

	}

	public Double averageApplicationsPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.averageApplicationsPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer maxApplicationsPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.maxApplicationsPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer minApplicationsPerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.minApplicationsPerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationApplicationsPerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.stdDeviationApplicationsPerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioPendingApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioPendingApplications();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioDueApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioDueApplications();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioAcceptedApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioAcceptedApplications();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioCancelledApplications() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.applicationRepository.ratioCancelledApplications();
		if (result == null)
			result = 0.0;

		return result;
	}

}
