package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

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
		HandyWorker principal;
		Section res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new Section();

		return res;
	}

	public Collection<Section> findAll() {
		Collection<Section> res;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionRepository.findAll();
		return res;
	}

	public Section findOne(int sectionId) {
		Section res;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionRepository.findOne(sectionId);
		return res;

	}

	public Section save(Section section) {
		Section res;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(section);

		res = this.sectionRepository.saveAndFlush(section);

		return res;
	}

	public void delete(Section section) {
		HandyWorker principal;
		Collection<Section> sections;

		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		sections = this.findSectionsByPrincipal();
		Assert.isTrue(sections.contains(section));

		this.sectionRepository.delete(section);
	}

	// Other business methods ---------------------------------

	public Collection<Section> findSectionsByPrincipal() {
		Collection<Section> res;
		HandyWorker principal;
		Collection<Tutorial> tutorials;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		tutorials = principal.getTutorial();

		res = new ArrayList<Section>();
		for (Tutorial tutorial : tutorials) {
			res.addAll(tutorial.getSections());
		}

		return res;
	}
}
