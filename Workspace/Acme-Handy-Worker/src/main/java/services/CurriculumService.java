package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Application;
import domain.Curriculum;
import domain.HandyWorker;

@Service
@Transactional
public class CurriculumService {
	
	// Managed Repository
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	// Supporting Services

	@Autowired
	private HandyWorkerService handyWorkerService;
	
	// Simple CRUD Methods
	
	public Curriculum create() {
		final Curriculum result;
		HandyWorker applicant;

		applicant = this.handyWorkerService.findByPrincipal();
		Assert.notNull(applicant);

		result = new Application();
		Assert.notNull(result);

		return result;
	}
	
	// Other business methods
}

