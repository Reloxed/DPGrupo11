
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
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UtilityService			utilityService;


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

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(socialProfile);

		if (socialProfile.getId() != 0)
			Assert.isTrue(principal.getSocialProfiles().contains(socialProfile));

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(socialProfile.getNick());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		result = this.socialProfileRepository.save(socialProfile);
		Assert.notNull(result);
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

	public Boolean checkifPrincipalIsOwnerBySocialProfileId(final int socialProfileID) {
		Actor principal;
		Boolean res = false;

		principal = this.actorService.findByPrincipal();

		for (final SocialProfile socialProfile : principal.getSocialProfiles())
			if (socialProfile.getId() == socialProfileID)
				res = true;
		return res;
	}

	public Actor findOwnerSocialProfile(final SocialProfile socialProfile) {
		Actor res = null;

		for (final Actor actor : this.actorService.findAll())
			if (actor.getSocialProfiles().contains(socialProfile))
				res = actor;
		return res;
	}

}
