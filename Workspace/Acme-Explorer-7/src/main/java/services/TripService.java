
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Administrator;
import domain.Application;
import domain.Audit;
import domain.Category;
import domain.Explorer;
import domain.LegalText;
import domain.Manager;
import domain.Note;
import domain.Ranger;
import domain.Sponsorship;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TripService {

	// Managed Repository
	@Autowired
	private TripRepository			tripRepository;

	// Supporting services
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private CustomisationService	customisationService;


	// Additional functions
	private String generateTicker() {
		String result;
		final Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		final String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String date = String.valueOf(now.get(Calendar.DATE));
		date = date.length() == 1 ? "0".concat(date) : date;
		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d);
		code = code.toUpperCase();
		result = year + month + date + "-" + code;
		return result;
	}

	// Constructors

	public TripService() {
		super();
	}

	// Simple CRUD methods

	public Trip create() {
		Manager principal;
		Trip trip;
		List<String> requirements;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		trip = new Trip();
		trip.setApplications(new ArrayList<Application>());
		trip.setStories(new ArrayList<Story>());
		trip.setAudits(new ArrayList<Audit>());
		trip.setNotes(new ArrayList<Note>());
		trip.setSponsorships(new ArrayList<Sponsorship>());
		trip.setSurvivalClasses(new ArrayList<SurvivalClass>());
		trip.setManager(principal);
		requirements = new ArrayList<String>();
		trip.setRequirements(requirements);
		return trip;
	}

	public Collection<Trip> ExplorerfindPaidTripsByExplorerId(final int explorerId) {
		Collection<Trip> result;
		Explorer principal;

		Assert.notNull(explorerId);
		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.findPaidTripsByExplorerId(explorerId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Trip> ManagerfindPaidTripsByExplorerId(final int explorerId) {
		Collection<Trip> result;
		Manager principal;

		Assert.notNull(explorerId);
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.findPaidTripsByExplorerId(explorerId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Trip> findAllPassedTripsAcceptedApplicationPrincipal() {
		Explorer principal;
		Collection<Trip> result;
		Collection<Trip> allPassedNoCancelledTrips;
		Collection<Trip> paidTrips;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		allPassedNoCancelledTrips = this.tripRepository.findAllOverAndNoCancelledTrips();
		Assert.notNull(allPassedNoCancelledTrips);

		paidTrips = this.ExplorerfindPaidTripsByExplorerId(principal.getId());
		Assert.notNull(paidTrips);

		allPassedNoCancelledTrips.retainAll(paidTrips);
		result = new ArrayList<Trip>(allPassedNoCancelledTrips);

		Assert.notNull(result);

		return result;

	}

	//  An actor who is not authenticated must be able to browse the list of trips and display them
	public Collection<Trip> findAll() {
		final Collection<Trip> result = this.tripRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findAllPendingTrips() {
		final Collection<Trip> result = this.tripRepository.findAllPendingTrips();
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findAllAuditableTrips() {
		final Collection<Trip> result = this.tripRepository.findAllAuditableTrips();
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findAllFutureNoCancelledTrips() {
		final Collection<Trip> result = this.tripRepository.findAllFutureNoCancelledTrips();
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findAllStartingSoonTrips() {
		Collection<Trip> result;
		Collection<Trip> allTrips;
		Date currentMoment;
		Date nextMonth;

		result = new ArrayList<Trip>();

		currentMoment = new Date();
		nextMonth = DateUtils.addMonths(currentMoment, 1);

		allTrips = this.findAll();
		for (final Trip trip : allTrips)
			if (trip.getStartDate().before(nextMonth))
				result.add(trip);

		return result;

	}

	// Other business methods

	public void cancel(final Trip trip) {
		Manager principal;
		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);

		final String reason = trip.getCancellationReason();

		Assert.notNull(reason);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(this.findByManager().contains(trip));

		Assert.isTrue(!reason.isEmpty());

		this.tripRepository.save(trip);
	}

	// Managers must be able to create trips
	public Trip save(final Trip t) {
		Manager principal;
		Trip result;
		Date currentMoment;
		boolean isSpam;
		Collection<String> spamWords;
		Collection<Trip> trips, updated;
		Ranger ranger;
		Category category;
		Double vatTax;

		Assert.notNull(t);

		principal = this.managerService.findByPrincipal();

		Assert.notNull(principal);

		t.setManager(principal);

		if (t.getId() == 0) {
			Collection<Application> applications;
			Collection<Sponsorship> sponsorships;
			Collection<Story> stories;
			Collection<Audit> audits;
			Collection<SurvivalClass> survivalClasses;
			Collection<Note> notes;

			applications = new ArrayList<Application>();
			sponsorships = new ArrayList<Sponsorship>();
			stories = new ArrayList<Story>();
			audits = new ArrayList<Audit>();
			survivalClasses = new ArrayList<SurvivalClass>();
			notes = new ArrayList<Note>();

			t.setApplications(applications);
			t.setSponsorships(sponsorships);
			t.setAudits(audits);
			t.setStories(stories);
			t.setSurvivalClasses(survivalClasses);
			t.setNotes(notes);

			t.setTicker(this.generateTicker());
			t.setCancellationReason(null);

		}

		currentMoment = new Date(System.currentTimeMillis() - 1);

		Assert.isTrue(t.getPublicationDate().after(currentMoment));
		Assert.isTrue(t.getStartDate().after(t.getPublicationDate()));
		Assert.isTrue(t.getEndDate().after(t.getStartDate()));
		Assert.isTrue(!t.getLegaltext().getIsDraft());

		vatTax = this.customisationService.find().getVatTax();

		double price = 0;
		for (final Stage stage : t.getStage())
			price += stage.getPrice();

		price += (price * (vatTax / 100));
		t.setPrice(price);

		isSpam = false;

		spamWords = this.customisationService.find().getSpamWords();
		for (final String spam : spamWords)
			if (t.getTitle().toLowerCase().contains(spam.toLowerCase())) {
				isSpam = true;
				break;
			} else if (t.getDescription().toLowerCase().contains(spam.toLowerCase())) {
				isSpam = true;
				break;
			} else
				for (final String r : t.getRequirements())
					if (r.toLowerCase().contains(spam.toLowerCase())) {
						isSpam = true;
						break;
					}

		if (isSpam == true)
			principal.setIsSuspicious(true);

		List<Stage> stages;
		List<Stage> orderedStages;
		int i;
		boolean areAllStagesValid;

		orderedStages = new ArrayList<>();
		stages = t.getStage();
		i = 1;
		areAllStagesValid = true;

		for (final Stage stg : stages) {
			stg.setNumber(i);
			i++;
			orderedStages.add(stg);
			if (stg.getNumber() == null || stg.getDescription().length() == 0 || stg.getTitle().length() == 0 || stg.getPrice() == null) {
				areAllStagesValid = false;
				break;
			}
		}
		Assert.isTrue(areAllStagesValid);

		t.setStage(orderedStages);

		/*
		 * boolean areStagesNumberedCorrectly = true;
		 * List<Stage> stages;
		 * List<Stage> orderedStages;
		 * orderedStages = new ArrayList<>();
		 * stages = t.getStage();
		 * 
		 * for (int i = 1; i <= stages.size(); i++) {
		 * boolean existsStageNumberI = false;
		 * for (final Stage stg : stages)
		 * if (stg.getNumber() == i) {
		 * orderedStages.add(stg);
		 * existsStageNumberI = true;
		 * break;
		 * }
		 * if (!existsStageNumberI) {
		 * areStagesNumberedCorrectly = false;
		 * break;
		 * }
		 * }
		 * 
		 * Assert.isTrue(areStagesNumberedCorrectly);
		 * t.setStage(orderedStages);
		 */

		result = this.tripRepository.save(t);

		trips = principal.getTrips();
		updated = new ArrayList<Trip>(trips);
		updated.add(result);
		principal.setTrips(updated);

		ranger = result.getRanger();

		trips = ranger.getTrips();
		updated = new ArrayList<Trip>(trips);
		updated.add(result);
		ranger.setTrips(updated);

		category = result.getCategory();

		trips = category.getTrips();
		updated = new ArrayList<Trip>(trips);
		updated.add(result);
		category.setTrips(updated);

		return result;
	}
	//  Managers must be able to list the trips they manage 
	public Collection<Trip> findByManager() {
		Manager principal;
		Collection<Trip> result;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager

		result = principal.getTrips();

		Assert.notNull(result);

		return result;

	}

	// Managers must be able to delete a trip they manage if the trip has not been published yet. Once published, the trip cannot be deleted.
	// A trip that has not been published yet, doesn't have any stories, notes, applications, sponsorships or audit records .

	public void delete(final Trip t) {
		Manager principal;
		final Date currentMoment;
		Collection<SurvivalClass> survivalClasses;
		Collection<Trip> trips;
		Ranger ranger;
		Category category;

		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager

		Assert.isTrue(principal.getTrips().contains(t));

		currentMoment = new Date(System.currentTimeMillis() - 1);

		Assert.isTrue(t.getPublicationDate().after(currentMoment));

		survivalClasses = t.getSurvivalClasses();

		for (final SurvivalClass survivalClass : survivalClasses)
			this.survivalClassService.delete(survivalClass);

		trips = principal.getTrips();
		Collection<Trip> updated = new ArrayList<Trip>(trips);
		updated.remove(t);
		principal.setTrips(updated);

		ranger = t.getRanger();

		trips = ranger.getTrips();
		updated = new ArrayList<Trip>(trips);
		updated.remove(t);
		ranger.setTrips(updated);

		category = t.getCategory();

		trips = category.getTrips();
		updated = new ArrayList<Trip>(trips);
		updated.remove(t);
		category.setTrips(updated);

		this.tripRepository.delete(t);

	}

	// V Managers must be able to cancel any of their trips that has been published but has not started, yet.
	public void cancel(final Trip t, final String reason) {
		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);
		Assert.notNull(reason);
		Assert.isTrue(!reason.isEmpty());
		Assert.isNull(t.getCancellationReason());
		final Manager principal = this.managerService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager
		Assert.isTrue(principal.getTrips().contains(t));
		final Date currentMoment = new Date();
		Assert.isTrue(t.getPublicationDate().before(currentMoment));
		Assert.isTrue(t.getStartDate().after(currentMoment));
		t.setCancellationReason(reason);
		this.tripRepository.save(t);
	}

	public Collection<Trip> findBySingleKey(String singleKey) {
		Collection<Trip> result;
		singleKey = "%" + singleKey + "%";
		result = this.tripRepository.findBySingleKey(singleKey);
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findByCategory(final int categoryId) {
		Collection<Trip> result;
		result = this.tripRepository.findByCategory(categoryId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findByTag(final Tag tag) {
		Collection<Trip> result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.findByTagId(tag.getId());
		Assert.notNull(result);

		return result;

	}

	public Trip findOne(final int tripId) {
		Trip result;

		result = this.tripRepository.findOne(tripId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Trip> findAllPublishedTrips() {
		Collection<Trip> result;

		result = this.tripRepository.findAllPublishedTrips();
		Assert.notNull(result);

		return result;

	}

	public Double averageTripsPerManager() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.averageTripsPerManager();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer minTripsPerManager() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.maxTripsPerManager();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer maxTripsPerManager() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.maxTripsPerManager();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationTripsPerManager() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.stdDeviationTripsPerManager();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double averagePricePerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.averagePricePerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer maxPricePerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.maxPricePerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer minPricePerTrip() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.minPricePerTrip();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationPricePerTrip() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.stdDeviationPricePerTrip();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double averageTripsPerRanger() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.averageTripsPerRanger();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Integer minTripsPerRanger() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.minTripsPerRanger();
		if (result == null)
			result = 0;

		return result;
	}

	public Integer maxTripsPerRanger() {
		Administrator principal;
		Integer result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.maxTripsPerRanger();
		if (result == null)
			result = 0;

		return result;
	}

	public Double stdDeviationTripsPerRanger() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.stdDeviationTripsPerRanger();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioCancelledTrips() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.ratioCancelledTrips();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioTripsWithAudit() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.ratioTripsWithAudit();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Collection<Trip> tripsMostApplications() {
		Administrator principal;
		Collection<Trip> result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tripRepository.tripsMostApplications();
		Assert.notNull(result);

		return result;
	}

	public Map<LegalText, Integer> groupByNumberOfTrips() {
		final Map<LegalText, Integer> result = new HashMap<LegalText, Integer>();
		final Administrator principal;
		final Collection<Trip> trips;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		trips = this.tripRepository.findAll();
		for (final Trip t : trips)
			if (result.containsKey(t.getLegaltext())) {
				Integer index = result.get(t.getLegaltext());
				index++;
				result.put(t.getLegaltext(), index);
			} else
				result.put(t.getLegaltext(), 1);

		return result;
	}

}
