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

	@Autowired
	private TutorialService tutorialService;

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

	public Section findOne(int sectionId) {
		Section res;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = this.sectionRepository.findOne(sectionId);

		return res;

	}

	public Section save(Section section) {
		HandyWorker principal;
		Tutorial tutorial;
		Section[] tutorialSections;

		Assert.notNull(section);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(section.getTitle());
		Assert.notNull(section.getText());

		tutorial = this.tutorialService
				.findTutorialBySectionId(section.getId());
		tutorialSections = (Section[]) tutorial.getSections().toArray();
		int size = tutorialSections.length;

		Assert.isTrue(section.getNumber() == tutorialSections[size - 1]
				.getNumber() + 1);

		return this.sectionRepository.save(section);
	}

	public void delete(Section section) {
		HandyWorker principal;

		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();

		Assert.notNull(principal);

		this.sectionRepository.delete(section);
	}
}
