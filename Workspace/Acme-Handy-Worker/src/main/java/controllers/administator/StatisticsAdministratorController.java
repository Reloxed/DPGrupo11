package controllers.administator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.ReportService;
import services.TonemaService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping(value = "statistics/administrator")
public class StatisticsAdministratorController extends AbstractController {

	// Services

	@Autowired
	private CustomerService customerService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private TonemaService tonemaService;
	
	// Constructor

	public StatisticsAdministratorController() {
		super();

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView res;
		Double[] fixuptasksStatistics = null;
		Double[] applicationsStatistics = null;
		Double[] pricesStatistics = null;
		Double[] complaintsStatistics = null;
		List<Double> statusStatistics = new ArrayList<>();
		Double pendingExpired = null;
		Collection<Customer> customerStatistics = new ArrayList<>();
		Collection<HandyWorker> handyWorkerStatistics = new ArrayList<>();
		Double[] notesStatistics = null;
		Double ratioFixWithComplaints = null;
		List<Customer> customerStatistics2 = new ArrayList<>();
		List<HandyWorker> handyWorkerStatistics2 = new ArrayList<>();
		Double[] avgstdStatistics = null;
		Double publishedTonema = null;
		Double unpublishedTonema = null;
				
		// The average and the standard deviation of the number of Tonema tasks per fixUptasks
		
		avgstdStatistics = this.tonemaService.avgstdOfTonema();
		
		// The ratio of published and unpublished Tonema
		publishedTonema = this.tonemaService.ratioOfPublishedTonema();
		unpublishedTonema = this.tonemaService.ratioOfUnpublishedTonema();

		// The average, the minimum, the maximum, and the standard deviation of
		// the number of fix-up tasks per user
		fixuptasksStatistics = this.fixUpTaskService
				.findFixUpTaskNumberOperation();

		// The average, the minimum, the maximum, and the standard deviation of
		// the number of applications per fix-up task
		applicationsStatistics = this.fixUpTaskService
				.findApplicationsNumberOperations();

		// The average, the minimum, the maximum, and the standard deviation of
		// the maximum price of the fix-up tasks
		pricesStatistics = this.fixUpTaskService
				.findMaxPricesNumberOperations();

		// The minimum, the maximum, the average, and the standard deviation of
		// the number of complaints per fix-up task
		complaintsStatistics = this.fixUpTaskService
				.findComplaintsNumberOperations();

		// The ratio of pending applications
		statusStatistics.add(this.applicationService
				.findRatioPendingApplications());
		// The ratio of accepted applications
		statusStatistics.add(this.applicationService
				.findRatioAcceptedApplications());
		// The ratio of rejected applications
		statusStatistics.add(this.applicationService
				.findRatioRejectedApplications());

		// The ratio of pending applications that cannot change its status
		// because their time period's elapsed.
		pendingExpired = this.applicationService
				.findRatioPendingExpiredApplications();

		// The listing of customers who have published at least 10% more fix-up
		// tasks than the average, ordered by number of applications
		if(!this.applicationService.findAll().isEmpty()){
			customerStatistics = this.customerService
					.topThreeCustomersTenPercentMoraThanAverage();
		}
		
		// The listing of handy workers who have got accepted at least 10% more
		// applications than the average, ordered by number of applications
		handyWorkerStatistics = this.handyWorkerService
				.findMoreApplicationsThanAvg();

		// The minimum, the maximum, the average, and the standard deviation of
		// the number of notes per referee report
		notesStatistics = this.reportService.findNotesNumberOperations();

		// The ratio of fix-up tasks with a complaint
		ratioFixWithComplaints = this.fixUpTaskService
				.ratioFixUpTaskWithComplaints();

		// The top-three customers in terms of complaints
		if(!this.applicationService.findAll().isEmpty()){
			customerStatistics2 = this.customerService
					.findCustomersWithMoreComplaints();
		}
		// The top-three handy workers in terms of complaints
		handyWorkerStatistics2 = this.handyWorkerService
				.findTopComplaintsHandyWorkers();

		res = new ModelAndView("administrator/dashboard");
		res.addObject("fixupstatistics", fixuptasksStatistics);
		res.addObject("applicationsStatistics", applicationsStatistics);
		res.addObject("pricesStatistics", pricesStatistics);
		res.addObject("complaintStatistics", complaintsStatistics);
		res.addObject("statusStatistics", statusStatistics.toArray());
		res.addObject("pendingExpired", pendingExpired);
		res.addObject("customerStatistics", customerStatistics);
		res.addObject("handyWorkerStatistics", handyWorkerStatistics);
		res.addObject("notesStatistics", notesStatistics);
		res.addObject("ratioFixWithComplaints", ratioFixWithComplaints);
		res.addObject("customerStatistics2", customerStatistics2);
		res.addObject("handyWorkerStatistics2", handyWorkerStatistics2);
		
		res.addObject("avgstdStatistics", avgstdStatistics);
		res.addObject("publishedTonema", publishedTonema);
		res.addObject("unpublishedTonema", unpublishedTonema);

		res.addObject("requestURI", "statistics/administrator/display.do");

		return res;
	}

}
