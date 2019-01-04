
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EmergencyContactRepository;
import domain.EmergencyContact;
import domain.Explorer;

@Service
@Transactional
public class EmergencyContactService {

	// Managed Repository
	@Autowired
	private EmergencyContactRepository	emergencyContactRepository;

	// Supporting services
	@Autowired
	private ExplorerService				explorerService;


	// Constructors

	public EmergencyContactService() {
		super();
	}

	// Simple CRUD methods

	public Collection<EmergencyContact> findByPrincipal() {
		Collection<EmergencyContact> result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getEmergencyContacts();

		Assert.notNull(result);

		return result;
	}

	public EmergencyContact create() {
		EmergencyContact result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = new EmergencyContact();
		Assert.notNull(result);

		return result;
	}

	public EmergencyContact findOne(final int emergencyContactId) {
		EmergencyContact result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.emergencyContactRepository.findOne(emergencyContactId);
		Assert.notNull(result);

		return result;
	}

	public Collection<EmergencyContact> findAll() {
		Collection<EmergencyContact> result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.emergencyContactRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EmergencyContact save(final EmergencyContact emergencyContact) {
		EmergencyContact result;
		Explorer principal;
		Collection<EmergencyContact> emergencyContacts;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(emergencyContact.getPhone() != null || emergencyContact.getEmail() != null);
		Assert.isTrue(emergencyContact.getPhone() != "" || emergencyContact.getEmail() != "");

		result = this.emergencyContactRepository.save(emergencyContact);
		Assert.notNull(result);

		if (emergencyContact.getId() == 0) {
			emergencyContacts = new ArrayList<EmergencyContact>(principal.getEmergencyContacts());
			emergencyContacts.add(result);
			principal.setEmergencyContacts(emergencyContacts);
		}
		return result;

	}

	public void delete(final EmergencyContact emergencyContact) {
		Explorer principal;
		Collection<EmergencyContact> emergencyContacts;

		Assert.notNull(emergencyContact);
		Assert.isTrue(emergencyContact.getId() != 0);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		emergencyContacts = new ArrayList<EmergencyContact>(principal.getEmergencyContacts());
		emergencyContacts.remove(emergencyContact);
		principal.setEmergencyContacts(emergencyContacts);

		this.emergencyContactRepository.delete(emergencyContact);

	}

	// Other business methods

}
