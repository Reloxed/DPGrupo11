
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import domain.Explorer;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class SurvivalClassService {

	//Managed Repository
	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	//Supporting Services
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	ApplicationService				applicationService;

	@Autowired
	TripService						tripService;


	// Constructors
	public SurvivalClassService() {
		super();
	}

	//Simple CRUD methods

	public SurvivalClass create() {
		Manager principal;
		SurvivalClass result;
		Collection<Explorer> explorers;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		explorers = new ArrayList<Explorer>();

		result = new SurvivalClass();
		result.setExplorers(explorers);

		return result;

	}

	public SurvivalClass findOne(final int survivalClassId) {
		SurvivalClass result;

		result = this.survivalClassRepository.findOne(survivalClassId);
		Assert.notNull(result);

		return result;
	}

	public Collection<SurvivalClass> findByPrincipal() {
		Collection<SurvivalClass> result;
		Manager principal;
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.survivalClassRepository.findByManager(principal.getId());

		Assert.notNull(result);

		return result;
	}

	// Other business methods

	// An actor who is authenticated as a manager must be able to delete the survival classes
	//	that are associated with his or her trips
	public void delete(final SurvivalClass sc) {
		Manager principal;
		Collection<SurvivalClass> classes;
		Collection<Explorer> explorers;
		Trip trip;

		Assert.notNull(sc);
		Assert.isTrue(sc.getId() != 0);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager

		classes = this.survivalClassRepository.findByManager(principal.getId());
		Assert.notNull(classes);
		Assert.isTrue(classes.contains(sc));

		explorers = sc.getExplorers();
		for (final Explorer explorer : explorers)
			if (explorer.getSurvivalClasses().contains(sc)) {
				final List<SurvivalClass> updated = new ArrayList<SurvivalClass>(explorer.getSurvivalClasses());
				updated.remove(sc);
				explorer.setSurvivalClasses(updated);
			}

		trip = sc.getTrip();
		final List<SurvivalClass> updated = new ArrayList<SurvivalClass>(trip.getSurvivalClasses());
		updated.remove(sc);
		trip.setSurvivalClasses(updated);

		this.survivalClassRepository.delete(sc);

	}

	// An actor who is authenticated as a manager must be able to create a survival class
	//	that is associated with his or her trips
	public SurvivalClass save(final SurvivalClass sc) {
		Manager principal;
		Trip trip;
		SurvivalClass result;
		Collection<Explorer> explorers;

		Assert.notNull(sc);

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		trip = sc.getTrip();
		Assert.isTrue(principal.getTrips().contains(trip));
		Assert.isTrue(sc.getMoment().after(trip.getStartDate()) && sc.getMoment().before(trip.getEndDate()));

		explorers = sc.getExplorers();
		for (final Explorer explorer : explorers) {
			Collection<Trip> paidTrips;
			paidTrips = this.tripService.ManagerfindPaidTripsByExplorerId(explorer.getId());
			Assert.notNull(paidTrips);
			Assert.isTrue(paidTrips.contains(trip));
		}

		result = this.survivalClassRepository.save(sc);

		for (final Explorer explorer : explorers) {
			Collection<SurvivalClass> updated;
			updated = new ArrayList<SurvivalClass>(explorer.getSurvivalClasses());
			updated.add(result);
			explorer.setSurvivalClasses(updated);

		}

		return result;

	}

	// An actor who is authenticated as an explorer must be able to enrol a survival class 
	// that is offered by any of the trips for which he or she has an accepted application as long as it isn't over.
	public void enrol(final SurvivalClass sc) {
		Explorer principal;
		List<SurvivalClass> classes;
		Collection<Trip> paidTrips;
		Date date;
		Collection<Explorer> explorers;
		Collection<SurvivalClass> survivalClasses;

		Assert.notNull(sc);
		Assert.isTrue(sc.getId() != 0);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		paidTrips = this.tripService.ExplorerfindPaidTripsByExplorerId(principal.getId());
		Assert.notNull(paidTrips);

		classes = new ArrayList<SurvivalClass>();
		date = new Date();

		for (final Trip t : paidTrips)
			classes.addAll(t.getSurvivalClasses());

		for (final SurvivalClass s : classes)
			if (s.getMoment().before(date))
				classes.remove(s);

		Assert.isTrue(classes.contains(sc));
		explorers = new ArrayList<Explorer>(sc.getExplorers());
		explorers.add(principal);
		sc.setExplorers(explorers);

		survivalClasses = new ArrayList<SurvivalClass>(principal.getSurvivalClasses());
		survivalClasses.add(sc);
		principal.setSurvivalClasses(survivalClasses);

	}

	public void unregister(final SurvivalClass sc) {
		Explorer principal;
		List<SurvivalClass> classes;
		Collection<Trip> paidTrips;
		Date date;
		Collection<Explorer> explorers;
		Collection<SurvivalClass> survivalClasses;

		Assert.notNull(sc);
		Assert.isTrue(sc.getId() != 0);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		paidTrips = this.tripService.ExplorerfindPaidTripsByExplorerId(principal.getId());
		Assert.notNull(paidTrips);

		classes = new ArrayList<SurvivalClass>();
		date = new Date();

		for (final Trip t : paidTrips)
			classes.addAll(t.getSurvivalClasses());

		for (final SurvivalClass s : classes)
			if (s.getMoment().before(date))
				classes.remove(s);

		Assert.isTrue(classes.contains(sc));
		explorers = new ArrayList<Explorer>(sc.getExplorers());
		explorers.remove(principal);
		sc.setExplorers(explorers);

		survivalClasses = new ArrayList<SurvivalClass>(principal.getSurvivalClasses());
		survivalClasses.remove(sc);
		principal.setSurvivalClasses(survivalClasses);

	}

}
