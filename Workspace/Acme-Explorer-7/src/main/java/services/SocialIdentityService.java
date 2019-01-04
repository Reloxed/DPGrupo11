
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed Repository
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;


	// Constructors

	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods

	public SocialIdentity create() {
		SocialIdentity result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new SocialIdentity();
		Assert.notNull(result);

		return result;
	}

	public SocialIdentity findOne(final int socialIdentityId) {
		SocialIdentity result;

		result = this.socialIdentityRepository.findOne(socialIdentityId);
		Assert.notNull(result);
		return result;

	}

	public Collection<SocialIdentity> findByPrincipal() {
		Collection<SocialIdentity> result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getSocialIdentities();

		Assert.notNull(result);

		return result;
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		SocialIdentity result;
		Actor principal;
		List<SocialIdentity> updated;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.socialIdentityRepository.save(socialIdentity);
		Assert.notNull(result);

		if (socialIdentity.getId() == 0) {
			updated = new ArrayList<SocialIdentity>(principal.getSocialIdentities());
			updated.add(result);
			principal.setSocialIdentities(updated);
		}
		return result;

	}

	public void delete(final SocialIdentity socialIdentity) {
		Actor principal;
		List<SocialIdentity> updated;

		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		updated = new ArrayList<SocialIdentity>(principal.getSocialIdentities());
		updated.remove(socialIdentity);
		principal.setSocialIdentities(updated);

		this.socialIdentityRepository.delete(socialIdentity);

	}

	// Other business methods

}
