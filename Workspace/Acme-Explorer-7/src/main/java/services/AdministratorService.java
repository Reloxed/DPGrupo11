
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private FolderService			folderService;


	// Supporting services

	// Constructors

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods
	public Administrator create() {
		Administrator principal;
		Administrator result;
		List<Folder> folders;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result = new Administrator();

		result.setIsSuspicious(false);
		result.setIsBanned(false);

		folders = this.folderService.createSystemFolders();

		result.setFolders(folders);
		result.setSentMessages(new ArrayList<Message>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator saved;
		Assert.notNull(administrator);

		if (administrator.getId() == 0) {
			Administrator principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			administrator.getUserAccount().setPassword(passwordEncoder.encodePassword(administrator.getUserAccount().getPassword(), null));
		} else {
			Administrator principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.administratorRepository.save(administrator);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	// Other business methods

}
