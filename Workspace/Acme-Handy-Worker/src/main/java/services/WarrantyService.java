package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private UtilityService	utilityService;


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
		Assert.notNull(w);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);	

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(w.getTitle());
		atributosAComprobar.add(w.getTerms());
		if (w.getLaws() != null)
			atributosAComprobar.add(w.getLaws());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}	
		
		if (w.getId() == 0){
			result = this.warrantyRepository.save(w);
			this.warrantyRepository.flush();
		} else {
			Assert.isTrue(!this.warrantyRepository.findOne(w.getId())
					.getIsFinal());
			result = this.warrantyRepository.save(w);
			this.warrantyRepository.flush();
		}
		

		


		return result;
	}

	public void delete(final Warranty w) {
		Assert.notNull(w);
		Assert.isTrue(w.getId() != 0);
		Assert.isTrue(!w.getIsFinal());

		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		this.warrantyRepository.delete(w);
	}

	// Other business methods

	public Collection<Warranty> findFinalWarranties() {
		final Collection<Warranty> result = new ArrayList<>();

		for (final Warranty w : this.warrantyRepository.findAll())
			if (w.getIsFinal() == true)
				result.add(w);

		return result;
	}

}
