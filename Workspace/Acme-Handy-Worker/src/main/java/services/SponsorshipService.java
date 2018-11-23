
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository -----------------------------

	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Sponsorship create(){
		return new Sponsorship();
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findAll();

		return sponsorships;
	}

	//	public Collection<Sponsorship> find(){
	//		
	//	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;
		
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		
		return result;
	}

	public Sponsorship save(final Sponsorship s) {
		Assert.notNull(s);
		Sponsorship result;

		result = this.sponsorshipRepository.save(s);
		
		return result;
	}

	public void delete(final Sponsorship s) {
		Assert.notNull(s);
		Assert.notNull(this.sponsorshipRepository.findOne(s.getId()));
		this.sponsorshipRepository.delete(s);
	}

	//Other business methods

}
