package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	@Autowired
	private SystemConfigurationService systemConfigurationService;

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

		boolean containsSpam = false;
		String[] spamWords = this.systemConfigurationService
				.findMySystemConfiguration().getSpamWords().split(",");
		String[] text = section.getText().split("(¿¡,.-_/!?) ");
		for (String word : spamWords) {
			for (String titleWord : text) {
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		if (!containsSpam) {
			String[] title = section.getTitle().split("(¿¡,.-_/!?) ");
			for (String word : spamWords) {
				for (String titleWord : title) {
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}

		}

		System.out.println("¿Contiene spam? " + containsSpam);

		res = this.sectionRepository.save(section);
		Assert.notNull(res);
		this.sectionRepository.flush();
		return res;
	}

	public void delete(Section section) {
		HandyWorker principal;
		Collection<Section> sections;
		Tutorial tutorial;

		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		tutorial = this.tutorialService
				.findTutorialBySectionId(section.getId());
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getSections().contains(section));

		sections = tutorial.getSections();
		Assert.isTrue(sections.contains(section));

		sections.remove(section);

		this.sectionRepository.delete(section);

		tutorial.setSections(sections);
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
