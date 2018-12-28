
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Administrator;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository

	@Autowired
	private WarrantyRepository			warrantyRepository;

	// Supporting services

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public WarrantyService() {
		super();
	}

	// Simple CRUD Methods

	public Warranty create() {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		final Warranty w = new Warranty();
		w.setIsFinal(false);
		return w;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Warranty findOne(final int warrantyId) {
		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);
		Assert.notNull(result);

		return result;
	}

	public Warranty save(final Warranty w) {
		Administrator principal;
		Warranty result;
		boolean containsSpam;
		Assert.notNull(w);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] title = w.getTitle().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : title)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}

		final String[] terms = w.getTerms().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String termsWord : terms)
				if (termsWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}

		final String[] laws = w.getLaws().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String lawsWord : laws)
				if (lawsWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		
		if (w.getId() == 0){
			Assert.isTrue(w.getIsFinal() == false);
			result = this.warrantyRepository.save(w);
			this.warrantyRepository.flush();
		} else {
			Assert.isTrue(this.warrantyRepository.findOne(w.getId()).getIsFinal()== false);
			result = this.warrantyRepository.save(w);
			this.warrantyRepository.flush();
		}		

		return result;
	}

	public void delete(final Warranty w) {
		Administrator principal;
		Assert.notNull(w);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(w.getId() != 0);
		Assert.isTrue(w.getIsFinal() == false);

		this.warrantyRepository.delete(w);
	}

	//Other business methods

	public Collection<Warranty> findFinalWarranties() {
		final Collection<Warranty> result = new ArrayList<>();

		for (final Warranty w : this.warrantyRepository.findAll())
			if (w.getIsFinal() == true)
				result.add(w);

		return result;
	}

}
