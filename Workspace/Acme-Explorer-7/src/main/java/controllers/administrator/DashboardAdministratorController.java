
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.ManagerService;
import services.NoteService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.LegalText;
import domain.Trip;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services

	@Autowired
	private TripService			tripService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private ManagerService		managerService;


	// Constructors

	public DashboardAdministratorController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Double avgApplicationsPerTrip, avgTripsPerManager, avgPricePerTrips, avgTripsPerRanger, avgNotesPerTrip, avgAuditsPerTrip;
		final Integer minApplicationsPerTrip, minTripsPerManager, minPricePerTrips, minTripsPerRanger, minNotesPerTrip, minAuditsPerTrip;
		final Integer maxApplicationsPerTrip, maxTripsPerManager, maxPricePerTrips, maxTripsPerRanger, maxNotesPerTrip, maxAuditsPerTrip;
		final Double stdApplicationsPerTrip, stdTripsPerManager, stdPricePerTrips, stdTripsPerRanger, stdNotesPerTrip, stdAuditsPerTrip;
		final Double ratioStatusPENDING, ratioStatusDUE, ratioStatusACCEPTED, ratioStatusCANCELLED, ratioCancelledTrips, ratioTripsWithAudit, ratioRangersWithCV, ratioRangersWithCVEndorsed, ratioSuspiciousManagers, ratioSuspiciousRangers;
		Collection<Trip> tripMostApplications;
		Map<LegalText, Integer> legaltextGroupedByNumberOfTrips;

		//Stadistics
		avgApplicationsPerTrip = this.applicationService.averageApplicationsPerTrip();
		avgTripsPerManager = this.tripService.averageTripsPerManager();
		avgPricePerTrips = this.tripService.averagePricePerTrip();
		avgTripsPerRanger = this.tripService.averageTripsPerRanger();
		avgNotesPerTrip = this.noteService.averageNotesPerTrip();
		avgAuditsPerTrip = this.auditService.averageAuditsPerTrip();

		minApplicationsPerTrip = this.applicationService.minApplicationsPerTrip();
		minTripsPerManager = this.tripService.minTripsPerManager();
		minPricePerTrips = this.tripService.minPricePerTrip();
		minTripsPerRanger = this.tripService.minTripsPerRanger();
		minNotesPerTrip = this.noteService.minNotesPerTrip();
		minAuditsPerTrip = this.auditService.minAuditsPerTrip();

		maxApplicationsPerTrip = this.applicationService.maxApplicationsPerTrip();
		maxTripsPerManager = this.tripService.maxTripsPerManager();
		maxPricePerTrips = this.tripService.maxPricePerTrip();
		maxTripsPerRanger = this.tripService.maxTripsPerRanger();
		maxNotesPerTrip = this.noteService.maxNotesPerTrip();
		maxAuditsPerTrip = this.auditService.maxAuditsPerTrip();

		stdApplicationsPerTrip = this.applicationService.stdDeviationApplicationsPerTrip();
		stdTripsPerManager = this.tripService.stdDeviationTripsPerManager();
		stdPricePerTrips = this.tripService.stdDeviationPricePerTrip();
		stdTripsPerRanger = this.tripService.stdDeviationTripsPerRanger();
		stdNotesPerTrip = this.noteService.stdDeviationNotesPerTrip();
		stdAuditsPerTrip = this.auditService.stdDeviationAuditsPerTrip();

		//Ratios

		ratioStatusPENDING = this.applicationService.ratioPendingApplications();
		ratioStatusDUE = this.applicationService.ratioDueApplications();
		ratioStatusACCEPTED = this.applicationService.ratioAcceptedApplications();
		ratioStatusCANCELLED = this.applicationService.ratioCancelledApplications();
		ratioCancelledTrips = this.tripService.ratioCancelledTrips();
		ratioTripsWithAudit = this.tripService.ratioTripsWithAudit();
		ratioRangersWithCV = this.rangerService.ratioRangersWithRegisteredCurriculum();
		ratioRangersWithCVEndorsed = this.rangerService.ratioRangersWithEndorsedCurriculum();
		ratioSuspiciousRangers = this.rangerService.ratioSuspiciousRangers();
		ratioSuspiciousManagers = this.managerService.ratioSuspiciousManagers();

		//List

		tripMostApplications = this.tripService.tripsMostApplications();

		//Map

		legaltextGroupedByNumberOfTrips = this.tripService.groupByNumberOfTrips();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgApplicationsPerTrip", avgApplicationsPerTrip);
		result.addObject("avgTripsPerManager", avgTripsPerManager);
		result.addObject("avgPricePerTrips", avgPricePerTrips);
		result.addObject("avgTripsPerRanger", avgTripsPerRanger);
		result.addObject("avgNotesPerTrip", avgNotesPerTrip);
		result.addObject("avgAuditsPerTrip", avgAuditsPerTrip);

		result.addObject("maxApplicationsPerTrip", maxApplicationsPerTrip);
		result.addObject("maxTripsPerManager", maxTripsPerManager);
		result.addObject("maxPricePerTrips", maxPricePerTrips);
		result.addObject("maxTripsPerRanger", maxTripsPerRanger);
		result.addObject("maxNotesPerTrip", maxNotesPerTrip);
		result.addObject("maxAuditsPerTrip", maxAuditsPerTrip);

		result.addObject("minApplicationsPerTrip", minApplicationsPerTrip);
		result.addObject("minTripsPerManager", minTripsPerManager);
		result.addObject("minPricePerTrips", minPricePerTrips);
		result.addObject("minTripsPerRanger", minTripsPerRanger);
		result.addObject("minNotesPerTrip", minNotesPerTrip);
		result.addObject("minAuditsPerTrip", minAuditsPerTrip);

		result.addObject("stdApplicationsPerTrip", stdApplicationsPerTrip);
		result.addObject("stdTripsPerManager", stdTripsPerManager);
		result.addObject("stdPricePerTrips", stdPricePerTrips);
		result.addObject("stdTripsPerRanger", stdTripsPerRanger);
		result.addObject("stdNotesPerTrip", stdNotesPerTrip);
		result.addObject("stdAuditsPerTrip", stdAuditsPerTrip);

		result.addObject("ratioStatusPENDING", ratioStatusPENDING);
		result.addObject("ratioStatusDUE", ratioStatusDUE);
		result.addObject("ratioStatusACCEPTED", ratioStatusACCEPTED);
		result.addObject("ratioStatusCANCELLED", ratioStatusCANCELLED);
		result.addObject("ratioCancelledTrips", ratioCancelledTrips);
		result.addObject("ratioTripsWithAudit", ratioTripsWithAudit);
		result.addObject("ratioRangersWithCV", ratioRangersWithCV);
		result.addObject("ratioRangersWithCVEndorsed", ratioRangersWithCVEndorsed);
		result.addObject("ratioSuspiciousRangers", ratioSuspiciousRangers);
		result.addObject("ratioSuspiciousManagers", ratioSuspiciousManagers);

		result.addObject("tripMostApplications", tripMostApplications);

		result.addObject("legaltextGroupedByNumberOfTripsKeys", legaltextGroupedByNumberOfTrips.keySet());
		result.addObject("legaltextGroupedByNumberOfTripsValues", new ArrayList<Integer>(legaltextGroupedByNumberOfTrips.values()));

		return result;

	}
}
