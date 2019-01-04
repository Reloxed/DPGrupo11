
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed Repository
	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services
	@Autowired
	private FolderService			folderService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public SponsorService() {
		super();
	}

	// Simple CRUD methods
	public Sponsor create() {
		Administrator principal;
		Sponsor result;
		List<Folder> folders;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Sponsor();

		result.setIsSuspicious(false);
		result.setIsBanned(false);

		folders = this.folderService.createSystemFolders();

		result.setFolders(folders);
		result.setSentMessages(new ArrayList<Message>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setSponsorships(new ArrayList<Sponsorship>());
		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor saved;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
		} else {
			Sponsor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.sponsorRepository.save(sponsor);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		return result;
	}
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Sponsor> findSponsorsBySuspicious() {
		Collection<Sponsor> result;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.sponsorRepository.findSponsorsBySuspicious();
		Assert.notNull(result);
		return result;

	}

}
