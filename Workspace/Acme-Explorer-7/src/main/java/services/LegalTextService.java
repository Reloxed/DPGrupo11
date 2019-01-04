
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.Administrator;
import domain.LegalText;

@Service
@Transactional
public class LegalTextService {

	// Managed Repository
	@Autowired
	private LegalTextRepository		legalTextRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public LegalTextService() {
		super();
	}

	// Simple CRUD methods

	public LegalText create() {
		LegalText result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new LegalText();
		Assert.notNull(result);

		return result;
	}

	// An actor who is authenticated as an administrator must be able to list the legal texts
	public Collection<LegalText> findAll() {
		Collection<LegalText> result;
		//		Administrator principal;

		//		principal = this.administratorService.findByPrincipal();

		//		Assert.notNull(principal); // Checks if the principal is an administrator

		result = this.legalTextRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public Collection<LegalText> findAllFinalMode() {
		Collection<LegalText> result;

		result = this.legalTextRepository.findAllFinalMode();

		Assert.notNull(result);

		return result;
	}

	public LegalText findOne(final int legalTextId) {
		LegalText result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);

		result = this.legalTextRepository.findOne(legalTextId);

		Assert.notNull(result);

		return result;
	}

	// An actor who is authenticated as an administrator must be able to create a legal text and edit it as long as it's saved in draft mode
	public LegalText save(final LegalText lt, final Boolean isDraft) {
		LegalText result;
		Administrator principal;
		Date registerDate;

		Assert.notNull(lt);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an administrator
		if (lt.getId() == 0) {
			registerDate = new Date(System.currentTimeMillis() - 1);
			lt.setRegisterDate(registerDate);
		}

		if (lt.getId() != 0)
			Assert.isTrue(this.legalTextRepository.findOne(lt.getId()).getIsDraft());
		lt.setIsDraft(isDraft);

		result = this.legalTextRepository.save(lt);

		return result;
	}

	// An actor who is authenticated as an administrator must be able to delete a legal text as long as it's is saved in draft mode
	public void delete(final LegalText lt) {
		Administrator principal;

		Assert.notNull(lt);
		Assert.isTrue(lt.getId() != 0);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an administrator

		Assert.isTrue(this.legalTextRepository.findOne(lt.getId()).getIsDraft());

		this.legalTextRepository.delete(lt);

	}

	// Other business methods

}
