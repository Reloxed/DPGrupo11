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
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository-----------

	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;

	// Supporting services ----------
	@Autowired
	private CurriculumService curriculumService;

	@Autowired
	private CustomerService cusotmerService;

	// Constructor ----------------------------------------------------
	public FixUpTaskService() {
		super();
	}

	// Simple CRUD methods-------

	public FixUpTask create(final Category category, final Warranty warranty) {
		FixUpTask result;
		Date currentMoment;

		result = new FixUpTask();
		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		result.setApplications(new HashSet<Application>());
		result.setCategory(category);
		result.setTicker(this.curriculumService.generateTicker());
		result.setWarranty(warranty);
		// falta quien la crea?

		return result;

	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public FixUpTask findOne(int fixUpTaskId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;

	}

	public FixUpTask save(FixUpTask fixUpTask) {
		FixUpTask result;
		Customer principal;

		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		Assert.isTrue(fixUpTask.getWarranty().getIsFinal());
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(fixUpTask.getStartMoment().before(
				fixUpTask.getEndMoment()));

		principal = this.cusotmerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.fixUpTaskRepository.save(fixUpTask);

		return result;

	}

	public void delete(FixUpTask fixUpTask) {
		Customer principal;

		Assert.notNull(fixUpTask);
		Assert.notNull(fixUpTask.getId() != 0);

		principal = this.cusotmerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(fixUpTask.getApplications().isEmpty());// no se puede
																// eliminar una
																// chapuza si
																// tiene
																// solicitudes

		this.fixUpTaskRepository.delete(fixUpTask);

	}

	// Other business methods--------
	// resitricciones de datos y restricciones de acceso

}
