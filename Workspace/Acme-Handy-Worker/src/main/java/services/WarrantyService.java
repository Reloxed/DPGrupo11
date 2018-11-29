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
	private WarrantyRepository warrantyRepository;

	// Supporting services

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Simple CRUD Methods

	public Warranty create() {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Warranty w = new Warranty();
		w.setIsFinal(false);
		return w;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> warranties;

		warranties = this.warrantyRepository.findAll();

		return warranties;
	}

	public Warranty findOne(int warrantyId) {
		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);

		return result;
	}

	
	public Warranty save(Warranty w){
		Administrator principal;
		Warranty result;
		Assert.notNull(w);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		
		if(w.getId() == 0){
			Assert.isTrue(w.getIsFinal() == false);	
		}

		boolean containsSpam = false;
		String[] spamWords = this.systemConfigurationService
				.findMySystemConfiguration().getSpamWords().split(",");
		String[] title = w.getTitle().split("(��,.-_/!?) ");
		for (String word : spamWords) {
			for (String titleWord : title) {
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}

		containsSpam = false;
		String[] terms = w.getTerms().split("(��,.-_/!?) ");
		for (String word : spamWords) {
			for (String termsWord : terms) {
				if (termsWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}

		containsSpam = false;
		String[] laws = w.getLaws().split("(��,.-_/!?) ");
		for (String word : spamWords) {
			for (String lawsWord : laws) {
				if (lawsWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		
//		Assert.isTrue(principal.getIsSuspicious() == false);
		result = this.warrantyRepository.saveAndFlush(w);
		principal = this.administratorService.save(principal);
		return result;
	}

	public void delete(Warranty w) {
		Administrator principal;
		Assert.notNull(w);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(w.getId() != 0);
		Assert.isTrue(w.getIsFinal() == false);

		this.warrantyRepository.delete(w);
	}
	
	//Other business methods
	
	public Collection<Warranty> findFinalWarranties(){
		Collection<Warranty> result = new ArrayList<>();
		
		for(Warranty w: this.warrantyRepository.findAll()){
			if(w.getIsFinal() == true){
				result.add(w);
			}
		}
		
		return result;
	}
	
}
