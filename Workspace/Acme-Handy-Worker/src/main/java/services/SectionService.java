
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
	private SectionRepository			sectionRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private TutorialService				tutorialService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public SectionService() {
		super();
	}

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

		res = this.sectionRepository.findAll();
		return res;
	}

	public Section findOne(final int sectionId) {
		Section res;

		res = this.sectionRepository.findOne(sectionId);
		return res;
	}

	public Section save(final Section section) {
		Section res;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(section);

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] text = section.getText().split("(��,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : text)
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
			final String[] title = section.getTitle().split("(��,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : title)
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

		res = this.sectionRepository.save(section);
		Assert.notNull(res);
		this.sectionRepository.flush();
		return res;
	}

	public void delete(final Section section) {
		HandyWorker principal;
		Collection<Section> sections;
		Tutorial tutorial;

		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		tutorial = this.tutorialService.findTutorialBySectionId(section.getId());
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
		for (final Tutorial tutorial : tutorials)
			res.addAll(tutorial.getSections());

		return res;
	}
}
