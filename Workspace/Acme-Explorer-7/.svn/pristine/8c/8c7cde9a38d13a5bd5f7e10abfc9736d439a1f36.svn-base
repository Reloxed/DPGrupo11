
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.Folder;
import domain.Message;
import domain.Note;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	// Managed Repository
	@Autowired
	private AuditorRepository		auditorRepository;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private AdministratorService	administratorService;


	// Supporting services
	// Constructors

	public AuditorService() {
		super();
	}

	// Simple CRUD methods
	public Auditor create() {
		Administrator principal;
		Auditor result;
		List<Folder> folders;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Auditor();

		result.setIsSuspicious(false);
		result.setIsBanned(false);

		folders = this.folderService.createSystemFolders();

		result.setFolders(folders);
		result.setSentMessages(new ArrayList<Message>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setNotes(new ArrayList<Note>());
		result.setAudits(new ArrayList<Audit>());
		return result;
	}

	public Auditor save(final Auditor auditor) {
		Auditor saved;
		Assert.notNull(auditor);

		if (auditor.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			auditor.getUserAccount().setPassword(passwordEncoder.encodePassword(auditor.getUserAccount().getPassword(), null));
		} else {
			Auditor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.auditorRepository.save(auditor);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}

	public void delete(final Auditor auditor) {
		Administrator principal;
		Boolean cantDelete;
		Assert.isTrue(auditor.getId() != 0);
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		cantDelete = false;
		for (final Audit audit : auditor.getAudits())
			if (!audit.getIsDraft()) {
				cantDelete = true;
				break;
			}
		Assert.isTrue(auditor.getNotes().size() == 0);
		Assert.isTrue(!cantDelete);
		this.auditorRepository.delete(auditor);
		for (final Audit audit : auditor.getAudits())
			if (audit.getIsDraft())
				this.auditService.delete(audit);
		for (final SocialIdentity si : auditor.getSocialIdentities())
			this.socialIdentityService.delete(si);

	}
	public Auditor findOne(final int auditorId) {
		Auditor result;
		result = this.auditorRepository.findOne(auditorId);
		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	//Other business methods
	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}
	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Auditor result;
		result = this.auditorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Auditor> findAuditorsBySuspicious() {
		Collection<Auditor> result;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.auditorRepository.findAuditorsBySuspicious();
		Assert.notNull(result);
		return result;

	}
}
