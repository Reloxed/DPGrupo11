package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository-----------

	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;

	// Supporting services ----------
	@Autowired
	private UtilityService utilityService;

	@Autowired
	private CustomerService customerService;

	// Constructor ----------------------------------------------------

	public FixUpTaskService() {
		super();
	}

	// Simple CRUD methods-------

	public FixUpTask create() {
		FixUpTask result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = new FixUpTask();
		
		result.setPublishedMoment(new Date(
				System.currentTimeMillis() - 1));
		result.setTicker(this.utilityService.generateTicker());

		result.setApplications(new HashSet<Application>());
		result.setComplaints(new HashSet<Complaint>());
		return result;

	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public FixUpTask findOne(final int fixUpTaskId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;

	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		FixUpTask result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(fixUpTask);
		Assert.notNull(fixUpTask.getEndMoment());
		Assert.notNull(fixUpTask.getStartMoment());
		Assert.isTrue(fixUpTask.getStartMoment().before(
				fixUpTask.getEndMoment()));
		Assert.notNull(fixUpTask.getDescription());
		Assert.notNull(fixUpTask.getAddress());
		Assert.notNull(fixUpTask.getCategory());

		Assert.isTrue(fixUpTask.getWarranty().getIsFinal());

		if (fixUpTask.getId() != 0) {
			Assert.isTrue(fixUpTask.getTicker().equals(
					this.findOne(fixUpTask.getId()).getTicker()));
		}

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(fixUpTask.getAddress());
		atributosAComprobar.add(fixUpTask.getDescription());

		result = this.fixUpTaskRepository.saveAndFlush(fixUpTask);
		
		final boolean containsSpam = this.utilityService
				.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		principal.getFixUpTasks().add(result);

		return result;

	}

	public void delete(final FixUpTask fixUpTask) {
		Customer principal;

		Assert.notNull(fixUpTask);
		Assert.notNull(fixUpTask.getId() != 0);

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getFixUpTasks().contains(fixUpTask));

		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		this.fixUpTaskRepository.delete(fixUpTask);
		principal.getFixUpTasks().remove(fixUpTask);
		this.customerService.save(principal);
	}

	// Other business methods--------

	public Double[] findFixUpTaskNumberOperation() {

		final Double[] res = this.fixUpTaskRepository
				.findFixUpTaskNumberOperations();
		return res;
	}

	public Double[] findApplicationsNumberOperations() {
		final Double[] res = this.fixUpTaskRepository
				.findApplicationsNumberOperations();
		return res;
	}

	public Double[] findMaxPricesNumberOperations() {
		final Double[] res = this.fixUpTaskRepository
				.findMaxPricesNumberOperations();
		return res;
	}

	public Double[] findComplaintsNumberOperations() {

		final Double[] res = this.fixUpTaskRepository
				.findComplaintsNumberOperations();
		return res;
	}

	public Double ratioFixUpTaskWithComplaints() {

		final Double res = this.fixUpTaskRepository
				.ratioFixUpTaskWithComplaints();
		return res;
	}
	

	public Collection<FixUpTask> FixUpTaskByCustomer(final int customerId) {
		final Collection<FixUpTask> res = this.fixUpTaskRepository
				.FixUpTaskByCustomer(customerId);
		return res;

	}

	public int creatorFixUpTask(final int fixUpTaskId) {

		Collection<Customer> customers;
		Collection<FixUpTask> tasks;
		int customerId = 0;
		customers = this.customerService.findAll();

		for (final Customer c : customers) {

			tasks = c.getFixUpTasks();
			for (final FixUpTask t : tasks)
				if (fixUpTaskId == t.getId()) {
					customerId = c.getId();
					break;
				}
			if (customerId != 0)
				break;
		}

		return customerId;

	}

}
