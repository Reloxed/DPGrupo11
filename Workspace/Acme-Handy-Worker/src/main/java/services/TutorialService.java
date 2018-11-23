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

	//Managed repository
	
	@Autowired
	private TutorialRepository tutorialRepository;
	
	//Supporting services
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Simple CRUD Methods
	
	public Tutorial create(){
		Tutorial result;
		
		result = new Tutorial();
		
		Section s = new Section();
		Collection<Section> sections = Collections.<Section> emptyList();
		sections.add(s);
		result.setSections(sections);
		
		return result;
	}
	
	public Collection<Tutorial> findAll(){
		Collection<Tutorial> tutorials;
		
		tutorials = this.tutorialRepository.findAll();
		
		return tutorials;
	}
	
//	public Collection<Tutorial> find(){
//		
//	}
	
	public Tutorial findOne(int tutorialId){
		Tutorial result;
		
		result = this.tutorialRepository.findOne(tutorialId);
		
		return result;
	}
	
	public Tutorial save(Tutorial t){
		Assert.notNull(t);
		Tutorial result;
		HandyWorker principal;
		Collection<Tutorial> tutorials;
		Collection<Sponsorship> sponsorships;
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		tutorials = Collections.<Tutorial> emptyList();
		tutorials.addAll(principal.getTutorial());
		tutorials.add(t);
		principal.setTutorial(tutorials);
		
		sponsorships = Collections.<Sponsorship> emptyList();
		sponsorships.addAll(this.sponsorshipService.findAll());
		t.setSponsorships(sponsorships);
		
		result = this.tutorialRepository.save(t);
		
		return result;
	}
	
	public void delete(Tutorial t){
		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);
		
		HandyWorker principal;
		Collection<Tutorial> tutorials;
		Collection<Sponsorship> sponsorships;
		
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		tutorials = Collections.<Tutorial> emptyList();
		tutorials.addAll(principal.getTutorial());
		tutorials.remove(t);
		principal.setTutorial(tutorials);
		
		this.tutorialRepository.delete(t);
	}
	
	//Other business methods
	
}
