package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository

	@Autowired

	private TutorialRepository tutorialRepository;

	// Supporting services

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private SystemConfigurationService systemConfigurationService;
	
	// Simple CRUD Methods

	public Tutorial create() {
		Tutorial result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Tutorial();

		Section s = new Section();
		Collection<Section> sections = Collections.<Section> emptyList();
		sections.add(s);
		result.setSections(sections);

		return result;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialRepository.findAll();

		return tutorials;
	}

	public Tutorial findOne(int tutorialId) {
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);

		return result;
	}

	public Tutorial save(Tutorial t) {
		Assert.notNull(t);
		Tutorial result;
		HandyWorker principal;
		Collection<Tutorial> tutorials;
		Collection<Sponsorship> sponsorships;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		if (t.getId() == 0) {
			tutorials = Collections.<Tutorial> emptyList();
			tutorials.addAll(principal.getTutorial());
			tutorials.add(t);
			principal.setTutorial(tutorials);

			sponsorships = Collections.<Sponsorship> emptyList();
			sponsorships.addAll(this.sponsorshipService.findAll());
			t.setSponsorships(sponsorships);
		} else {
			Assert.isTrue(principal.getTutorial().contains(t));
		}
		
		boolean containsSpam = false;
		String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		String[] title = t.getTitle().split(" ");
		for(String word: spamWords){
			for(String titleWord: title){
				if(titleWord.toLowerCase().contains(word.toLowerCase())){
					containsSpam = true;
					break;
				}
			}
			if (containsSpam){
				principal.setIsSuspicious(true);
				break;
			}
		}
		
		containsSpam = false;
		String[] summary = t.getSummary().split(" ");
		for(String word: spamWords){
			for(String summaryWord: summary){
				if(summaryWord.toLowerCase().contains(word.toLowerCase())){
					containsSpam = true;
					break;
				}
			}
			if (containsSpam){
				principal.setIsSuspicious(true);
				break;
			}
		}

		result = this.tutorialRepository.save(t);

		return result;
	}

	public void delete(Tutorial t) {
		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);

		HandyWorker principal;
		Collection<Tutorial> tutorials;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		if (principal.getTutorial().contains(t)) {
			tutorials = Collections.<Tutorial> emptyList();
			tutorials.addAll(principal.getTutorial());
			tutorials.remove(t);
			principal.setTutorial(tutorials);
			this.tutorialRepository.delete(t);
		}

	}

	// Other business methods
	
/*	public Tutorial findTutorialBySectionId(int sectionId) {
		Tutorial res;

		res = this.tutorialRepository.findTutorialBySectionId(sectionId);
		Assert.notNull(res);

		return res;
	}*/
}
