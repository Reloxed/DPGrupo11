
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	private TutorialRepository			tutorialRepository;

	// Supporting services

	@Autowired
	private SponsorshipService			sponsorshipService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private UtilityService	utilityService;


	// Constructors ------------------------------------

	public TutorialService() {
		super();
	}

	// Simple CRUD Methods

	public Tutorial create() {
		Tutorial result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Tutorial();

		final Collection<Section> sections = new ArrayList<Section>();
		result.setSections(sections);

		final Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		result.setSponsorships(sponsorships);

		return result;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tutorial findOne(final int tutorialId) {
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);

		return result;
	}

	public Tutorial save(final Tutorial t) {
		Assert.notNull(t);
		Tutorial result;
		HandyWorker principal;
		Collection<Tutorial> tutorials;
		Collection<Sponsorship> sponsorships;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		if (t.getId() != 0) {
			Assert.isTrue(principal.getTutorial().contains(t));	
		} else {
			sponsorships = new ArrayList<>();
			sponsorships.addAll(this.sponsorshipService.findAll());
			t.setSponsorships(sponsorships);
		}
			
		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(t.getTitle());
		atributosAComprobar.add(t.getSummary());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}
		
		result = this.tutorialRepository.save(t);
		Assert.notNull(result);
		this.tutorialRepository.flush();
		
		tutorials = new ArrayList<>();
		tutorials.addAll(principal.getTutorial());
		tutorials.add(t);
		principal.setTutorial(tutorials);
		
		return result;
	}

	public void delete(final Tutorial t) {
		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);

		HandyWorker principal;
		Collection<Tutorial> tutorials;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		if (principal.getTutorial().contains(t)) {
			tutorials = new ArrayList<>();
			tutorials.addAll(principal.getTutorial());
			tutorials.remove(t);
			principal.setTutorial(tutorials);
			this.tutorialRepository.delete(t);
		}

	}

	// Other business methods

	public Tutorial findTutorialBySectionId(final int sectionId) {
		Tutorial res;

		res = this.tutorialRepository.findTutorialBySectionId(sectionId);
		Assert.notNull(res);

		return res;
	}
	public Collection<Tutorial> findAllTutorialsByHandyWorker(){
		
		HandyWorker principal;
		Collection<Tutorial> result;
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result=principal.getTutorial();
		
		return result;
	}
}