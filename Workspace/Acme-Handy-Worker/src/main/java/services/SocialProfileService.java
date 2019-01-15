package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository

	@Autowired
	private SocialProfileRepository socialProfileRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private RefereeService refereeService;

	// Constructors ------------------------------------

	public SocialProfileService() {
		super();
	}

	// Simple CRUD Methods

	public SocialProfile create() {
		SocialProfile result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new SocialProfile();
		Assert.notNull(result);
		return result;
	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;

		result = this.socialProfileRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SocialProfile findOne(final int socialProfileId) {
		SocialProfile result;

		result = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(result);

		return result;
	}

	public Collection<SocialProfile> findByPrincipal() {
		Collection<SocialProfile> result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getSocialProfiles();
		return result;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Actor principal;
		SocialProfile result;
		Collection<SocialProfile> socialProfilesUpdated;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(socialProfile);

		if (socialProfile.getId() != 0)
			Assert.isTrue(principal.getSocialProfiles().contains(socialProfile));

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(socialProfile.getNick());

		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam) {
			principal.setIsSuspicious(true);
		}

		result = this.socialProfileRepository.save(socialProfile);
		Assert.notNull(result);

		if (principal instanceof Customer) {
			Customer toSave = this.customerService.findOne(principal.getId());
			socialProfilesUpdated = new ArrayList<SocialProfile>();
			socialProfilesUpdated.addAll(toSave.getSocialProfiles());
			socialProfilesUpdated.add(result);
			toSave.setSocialProfiles(socialProfilesUpdated);
			this.customerService.save(toSave);
		} else if (principal instanceof HandyWorker) {
			HandyWorker toSave = this.handyWorkerService.findOne(principal
					.getId());
			socialProfilesUpdated = new ArrayList<SocialProfile>();
			socialProfilesUpdated.addAll(toSave.getSocialProfiles());
			socialProfilesUpdated.add(result);
			toSave.setSocialProfiles(socialProfilesUpdated);
			this.handyWorkerService.save(toSave);
		} else if (principal instanceof Sponsor) {
			Sponsor toSave = this.sponsorService.findOne(principal.getId());
			socialProfilesUpdated = new ArrayList<SocialProfile>();
			socialProfilesUpdated.addAll(toSave.getSocialProfiles());
			socialProfilesUpdated.add(result);
			toSave.setSocialProfiles(socialProfilesUpdated);
			this.sponsorService.save(toSave);
		} else if (principal instanceof Referee) {
			Referee toSave = this.refereeService.findOne(principal.getId());
			socialProfilesUpdated = new ArrayList<SocialProfile>();
			socialProfilesUpdated.addAll(toSave.getSocialProfiles());
			socialProfilesUpdated.add(result);
			toSave.setSocialProfiles(socialProfilesUpdated);
			this.refereeService.save(toSave);
		} else {
			Administrator toSave = this.administratorService.findOne(principal
					.getId());
			socialProfilesUpdated = new ArrayList<SocialProfile>();
			socialProfilesUpdated.addAll(toSave.getSocialProfiles());
			socialProfilesUpdated.add(result);
			toSave.setSocialProfiles(socialProfilesUpdated);
			this.administratorService.save(toSave);
		}

		return result;
	}

	public void delete(final SocialProfile socialProfile) {
		Actor principal;
		Collection<SocialProfile> socialProfilesUpdated;

		Assert.notNull(socialProfile);
		Assert.isTrue(socialProfile.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		socialProfilesUpdated = new ArrayList<SocialProfile>();
		socialProfilesUpdated.addAll(principal.getSocialProfiles());
		Assert.isTrue(socialProfilesUpdated.contains(socialProfile));
		socialProfilesUpdated.remove(socialProfile);
		principal.setSocialProfiles(socialProfilesUpdated);

		this.socialProfileRepository.delete(socialProfile);

	}

	// Other business methods

	public Boolean checkifPrincipalIsOwnerBySocialProfileId(int socialProfileID) {
		Actor principal;
		Boolean res = false;

		principal = this.actorService.findByPrincipal();

		for (SocialProfile socialProfile : principal.getSocialProfiles()) {
			if (socialProfile.getId() == socialProfileID) {
				res = true;
			}
		}
		return res;
	}

	public Actor findOwnerSocialProfile(SocialProfile socialProfile) {
		Actor res = null;

		for (Actor actor : this.actorService.findAll()) {
			if (actor.getSocialProfiles().contains(socialProfile)) {
				res = actor;
			}
		}
		return res;
	}

}
