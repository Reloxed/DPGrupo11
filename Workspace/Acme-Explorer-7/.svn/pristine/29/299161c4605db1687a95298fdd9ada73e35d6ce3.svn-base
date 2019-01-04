
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Manager;
import domain.Message;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed Repository
	@Autowired
	private ManagerRepository		managerRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FolderService			folderService;


	// Constructors

	public ManagerService() {
		super();
	}

	// Simple CRUD methods
	public Manager create() {
		Administrator principal;
		Manager result;
		List<Folder> folders;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Manager();

		result.setIsSuspicious(false);
		result.setIsBanned(false);

		folders = this.folderService.createSystemFolders();

		result.setFolders(folders);
		result.setSentMessages(new ArrayList<Message>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setTrips(new ArrayList<Trip>());

		return result;
	}

	public Manager save(final Manager manager) {
		Manager saved;
		Assert.notNull(manager);

		if (manager.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(passwordEncoder.encodePassword(manager.getUserAccount().getPassword(), null));
		} else {
			Manager principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.managerRepository.save(manager);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}

	public Manager findOne(final int managerId) {
		Manager result;
		result = this.managerRepository.findOne(managerId);
		return result;
	}

	//Other business methods
	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager result;
		result = this.managerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Manager> findManagersBySuspicious() {
		Collection<Manager> result;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.managerRepository.findManagersBySuspicious();
		Assert.notNull(result);
		return result;

	}

	public Double ratioSuspiciousManagers() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.managerRepository.ratioSuspiciousManagers();
		if (result == null)
			result = 0.0;

		return result;
	}

}
