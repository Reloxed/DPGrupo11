
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomisationRepository;
import domain.Administrator;
import domain.Customisation;

@Service
@Transactional
public class CustomisationService {

	// Managed Repository
	@Autowired
	private CustomisationRepository	customisationRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public CustomisationService() {
		super();
	}

	// Simple CRUD methods

	public Customisation create() {
		Customisation result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Customisation();
		Assert.notNull(result);

		return result;
	}

	public Customisation find() {
		Customisation result;

		result = this.customisationRepository.findAll().get(0);
		Assert.notNull(result);

		return result;

	}

	public Customisation save(final Customisation customisation) {
		Customisation result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.customisationRepository.save(customisation);
		Assert.notNull(result);

		return result;

	}

	// Other business methods

}
