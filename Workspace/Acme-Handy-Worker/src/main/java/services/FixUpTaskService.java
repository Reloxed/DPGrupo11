
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
	private FixUpTaskRepository			fixUpTaskRepository;

	//Supporting services ----------
	@Autowired
	private UtilityService				utilityService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//Constructor ----------------------------------------------------

	public FixUpTaskService() {
		super();
	}

	//Simple CRUD methods-------

	public FixUpTask create() {
		FixUpTask result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = new FixUpTask();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));

		result.setApplications(new HashSet<Application>());
		result.setComplaints(new HashSet<Complaint>());

		return result;

	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();

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
		double realPrice;

		Assert.isTrue(fixUpTask.getId() == 0);
		Assert.isTrue(fixUpTask.getStartMoment().before(fixUpTask.getEndMoment()));
		Assert.notNull(fixUpTask.getEndMoment());
		Assert.notNull(fixUpTask.getStartMoment());
		Assert.notNull(fixUpTask.getTicker());
		Assert.notNull(fixUpTask.getPublishedMoment());
		Assert.notNull(fixUpTask.getDescription());
		Assert.notNull(fixUpTask.getAddress());
		Assert.notNull(fixUpTask.getCategory());
		Assert.notNull(fixUpTask.getWarranty());

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		realPrice = (this.systemConfigurationService.findMySystemConfiguration().getVAT() / 100) * fixUpTask.getMaxPrice() + fixUpTask.getMaxPrice();
		fixUpTask.setMaxPrice(realPrice);

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] description = fixUpTask.getDescription().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : description)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		if (!containsSpam) {
			final String[] address = fixUpTask.getAddress().split("(¿¡,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : address)
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}
		}

		result = this.fixUpTaskRepository.saveAndFlush(fixUpTask);

		principal.getFixUpTasks().add(result);

		return result;

	}

	public void delete(final FixUpTask fixUpTask) {
		Customer principal;

		Assert.notNull(fixUpTask);
		Assert.notNull(fixUpTask.getId() != 0);

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(fixUpTask.getApplications().isEmpty());// no se puede
		// eliminar una
		// chapuza si
		// tiene
		// solicitudes

		this.fixUpTaskRepository.delete(fixUpTask);

	}

	// Other business methods--------

	public Double[] findApplicationsNumberOperations() {
		final Double[] res = this.fixUpTaskRepository.findApplicationsNumberOperations();
		return res;
	}
	public Double[] findMaxPricesNumberOperations() {
		final Double[] res = this.fixUpTaskRepository.findMaxPricesNumberOperations();
		return res;

	}
	public Double[] findComplaintsNumberOperations() {

		final Double[] res = this.fixUpTaskRepository.findComplaintsNumberOperations();
		return res;

	}
	public Double ratioFixUpTaskWithComplaints() {

		final Double res = this.fixUpTaskRepository.ratioFixUpTaskWithComplaints();
		return res;

	}

}
