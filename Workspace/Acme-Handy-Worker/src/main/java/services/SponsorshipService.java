
package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository -----------------------------

	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting services
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private TutorialService tutorialService;
	
	//Simple CRUD Methods
	
	public Sponsorship create(){
		Sponsorship result;
		Sponsor principal;
		
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = new Sponsorship();
		Assert.notNull(result);
		result.setSponsor(principal);
		return result;
	}
	
	public Collection<Sponsorship> findByPrincipal(){
		Collection<Sponsorship> result;
		Sponsor principal;
		
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = principal.getSponsorships();
		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findAll();

		return sponsorships;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;
		
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);
		
		return result;
	}

	public Sponsorship save(final Sponsorship s) {
		Assert.notNull(s);
		Sponsorship result;
		Sponsor principal;
		Collection<Sponsorship> sponsorships;
		
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.sponsorshipRepository.save(s);
		Assert.notNull(result);
		
		sponsorships = Collections.<Sponsorship> emptyList();
		sponsorships.addAll(principal.getSponsorships());
		sponsorships.add(s);
		principal.setSponsorships(sponsorships);
		
		for(Tutorial t: this.tutorialService.findAll() ){
			Collection<Sponsorship> aux;
			aux = Collections.<Sponsorship> emptyList();
			aux.addAll(t.getSponsorships());
			aux.add(s);
			t.setSponsorships(aux);
		}
		
		return result;
	}

	public void delete(final Sponsorship s) {
		Assert.notNull(s);
		Assert.isTrue(s.getId() != 0);
		Sponsor principal;
		Collection<Sponsorship> sponsorships;
		
		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);
		
		sponsorships = Collections.<Sponsorship> emptyList();
		sponsorships.addAll(principal.getSponsorships());
		sponsorships.remove(s);
		principal.setSponsorships(sponsorships);
		
		for(Tutorial t: this.tutorialService.findAll() ){
			Collection<Sponsorship> aux;
			aux = Collections.<Sponsorship> emptyList();
			aux.addAll(t.getSponsorships());
			aux.remove(s);
			t.setSponsorships(aux);
		}
		
		this.sponsorshipRepository.delete(s);
	}

	//Other business methods

}
