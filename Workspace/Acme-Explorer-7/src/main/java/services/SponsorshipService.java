
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	// Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services
	@Autowired
	private SponsorService			sponsorService;


	// Constructors

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Sponsorship> findByPrincipal() {
		Collection<Sponsorship> result;
		Sponsor principal;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getSponsorships();

		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);

		Assert.notNull(result);

		return result;
	}

	public Sponsorship create() {
		Sponsorship result;
		Sponsor principal;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Sponsorship();
		Assert.notNull(result);
		result.setSponsor(principal);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship result;
		Sponsor principal;
		Trip trip;
		Collection<Sponsorship> sponsorships;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		trip = sponsorship.getTrip();

		result = this.sponsorshipRepository.save(sponsorship);
		Assert.notNull(result);

		sponsorships = new ArrayList<Sponsorship>(trip.getSponsorships());
		sponsorships.add(result);
		trip.setSponsorships(sponsorships);

		sponsorships = new ArrayList<Sponsorship>(principal.getSponsorships());
		sponsorships.add(result);
		principal.setSponsorships(sponsorships);

		return result;

	}

	public void delete(final Sponsorship sponsorship) {
		Sponsor principal;
		Trip trip;
		Collection<Sponsorship> sponsorships;

		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		trip = sponsorship.getTrip();

		sponsorships = new ArrayList<Sponsorship>(trip.getSponsorships());
		sponsorships.remove(sponsorship);
		trip.setSponsorships(sponsorships);

		sponsorships = new ArrayList<Sponsorship>(principal.getSponsorships());
		sponsorships.remove(sponsorship);
		principal.setSponsorships(sponsorships);

		this.sponsorshipRepository.delete(sponsorship);

	}

	// Other business methods

	public Sponsorship getRandomSponsorship(final Trip trip) {
		Random random;
		final Sponsorship result;
		List<Sponsorship> sponsorships;

		random = new Random();

		sponsorships = new ArrayList<Sponsorship>(trip.getSponsorships());
		if (sponsorships.size() > 0) {
			int randomPos;
			randomPos = random.nextInt(sponsorships.size());
			result = sponsorships.get(randomPos);
		} else
			result = null;
		return result;

	}

}
