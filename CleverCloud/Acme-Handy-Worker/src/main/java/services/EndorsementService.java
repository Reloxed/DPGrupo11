
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Endorsement;
import domain.Endorser;
import domain.HandyWorker;

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
	private UtilityService			utilityService;


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

		return result;

	}
	public Collection<Endorsement> findAll() {
		Collection<Endorsement> result;
		result = this.endorsementRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Endorsement findOne(final int endorsementId) {
		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);

		return result;

	}

	public Endorsement save(final Endorsement endorsement) {
		Endorsement result;
		Endorser principal;

		principal = this.endorserService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(endorsement.getSender());
		Assert.isTrue(endorsement.getSender().getId() == principal.getId());
		Assert.notNull(endorsement.getRecipient());
		Assert.notNull(endorsement.getComments());

		if (endorsement.getId() == 0)
			endorsement.setPublishedMoment(new Date(System.currentTimeMillis() - 1));
		else {
			Assert.isTrue(endorsement.getRecipient().equals(this.findOne(endorsement.getId()).getRecipient()));
			Assert.isTrue(endorsement.getPublishedMoment().equals(this.findOne(endorsement.getId()).getPublishedMoment()));
		}

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(endorsement.getComments());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		result = this.endorsementRepository.save(endorsement);

		return result;

	}

	public void delete(final Endorsement endorsement) {
		Endorser principal;

		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);

		principal = this.endorserService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(endorsement.getSender().equals(principal));

		this.endorsementRepository.delete(endorsement);

	}

	//Other business methods--------

	public Collection<HandyWorker> possibleHwRecipients(final int customerId) {
		Collection<HandyWorker> result;

		result = this.endorsementRepository.possibleHwRecipients(customerId);

		return result;
	}

}
