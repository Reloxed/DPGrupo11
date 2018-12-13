
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Endorsement;
import domain.Endorser;

@Service
@Transactional
public class EndorsementService {

	//Managed repository-----------

	@Autowired
	private EndorsementRepository	endorsementRepository;

	//Supporting services ----------

	@Autowired
	private EndorserService			endorserService;
	
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public EndorsementService() {
		super();
	}

	//Simple CRUD methods-------
	public Endorsement create() {

		Endorsement result;
		Endorser endorser;

		endorser = this.endorserService.findByPrincipal();
		Assert.notNull(endorser);

		result = new Endorsement();

		result.setPublishedMoment(new Date(System.currentTimeMillis() - 1));

		return result;

	}
	public Collection<Endorsement> findAll() {
		Collection<Endorsement> result;
		result = this.endorsementRepository.findAll();

		return result;

	}

	public Endorsement findOne(final int endorsementId) {
		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);

		return result;

	}

	public Endorsement save(final Endorsement endorsement) {
		Endorsement result;
		Endorser principal;

		Assert.isTrue(endorsement.getSender() != null);
		Assert.isTrue(endorsement.getRecipient() != null);
		Assert.notNull(endorsement.getPublishedMoment());
		Assert.notNull(endorsement.getComments());

		principal = this.endorserService.findByPrincipal();
		Assert.isTrue(endorsement.getSender().getId() == principal.getId());
		Assert.notNull(principal);
		Assert.isTrue(endorsement.getId() == 0);
		
		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] comments = endorsement.getComments().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : comments)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		
		result = this.endorsementRepository.save(endorsement);

		return result;

	}

	public void delete(final Endorsement endorsement) {
		Endorser principal;

		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		principal = this.endorserService.findByPrincipal();
		Assert.notNull(principal);

		this.endorsementRepository.delete(endorsement);

	}

	//Other business methods--------

}
