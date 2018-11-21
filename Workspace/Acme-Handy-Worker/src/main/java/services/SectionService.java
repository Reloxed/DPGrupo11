package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed repository ------------------------------------

	@Autowired
	private SectionRepository sectionRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------------------

	public Section create() {
		return new Section();
	}

	public Collection<Section> findAll() {
		return sectionRepository.findAll();
	}

	public Section save(Section s) {
		// Assert.isTrue(s.getNumber() >= 0);
		return sectionRepository.save(s);
	}

	// TODO
	public void delete(Section s) {
		sectionRepository.delete(s);
	}

	// TODO
	// Other business methods -------------------------------
}
