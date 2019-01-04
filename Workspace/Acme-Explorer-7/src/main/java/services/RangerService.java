
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed Repository
	@Autowired
	private RangerRepository		rangerRepository;

	// Supporting services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FolderService			folderService;


	// Constructors

	public RangerService() {
		super();
	}
	//Auxiliar methods

	// Simple CRUD methods
	public Ranger create() {

		Ranger result;
		List<Folder> folders;

		result = new Ranger();

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

	public Ranger save(final Ranger ranger) {
		Ranger saved;
		Assert.notNull(ranger);

		if (ranger.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			ranger.getUserAccount().setPassword(passwordEncoder.encodePassword(ranger.getUserAccount().getPassword(), null));
		} else {
			Ranger principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.rangerRepository.save(ranger);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}

	public Ranger findOne(final int rangerId) {
		Ranger result;
		result = this.rangerRepository.findOne(rangerId);
		return result;
	}

	public Collection<Ranger> findAll() {
		Collection<Ranger> result;
		result = this.rangerRepository.findAll();
		return result;
	}

	// Other business methods
	public Ranger findByPrincipal() {
		Ranger result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Ranger result;
		result = this.rangerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Ranger> findRangersBySuspicious() {
		Collection<Ranger> result;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.rangerRepository.findRangersBySuspicious();
		Assert.notNull(result);
		return result;

	}

	public Double ratioRangersWithRegisteredCurriculum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.rangerRepository.ratioRangersWithRegisteredCurriculum();
		if (result == null)
			result = 0.0;

		return result;
	}

	public Double ratioRangersWithEndorsedCurriculum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.rangerRepository.ratioRangersWithEndorsedCurriculum();

		if (result == null)
			result = 0.0;
		return result;
	}

	public Double ratioSuspiciousRangers() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.rangerRepository.ratioSuspiciousRangers();
		if (result == null)
			result = 0.0;

		return result;
	}
}
