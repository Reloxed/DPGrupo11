
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Application;
import domain.EmergencyContact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.Message;
import domain.SocialIdentity;
import domain.Story;
import domain.SurvivalClass;

@Service
@Transactional
public class ExplorerService {

	// Managed Repository
	@Autowired
	private ExplorerRepository		explorerRepository;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private AdministratorService	administratorService;


	// Supporting services

	// Constructors

	public ExplorerService() {
		super();
	}

	// Simple CRUD methods
	public Explorer create() {
		Explorer result;
		List<Folder> folders;

		result = new Explorer();

		result.setIsSuspicious(false);
		result.setIsBanned(false);

		folders = this.folderService.createSystemFolders();

		result.setFolders(folders);
		result.setSentMessages(new ArrayList<Message>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setApplications(new ArrayList<Application>());
		result.setStories(new ArrayList<Story>());
		result.setFinders(new ArrayList<Finder>());
		result.setSurvivalClasses(new ArrayList<SurvivalClass>());
		result.setEmergencyContacts(new ArrayList<EmergencyContact>());

		return result;
	}

	public Explorer save(final Explorer explorer) {
		Explorer saved;
		Assert.notNull(explorer);

		if (explorer.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			explorer.getUserAccount().setPassword(passwordEncoder.encodePassword(explorer.getUserAccount().getPassword(), null));
		} else {
			Explorer principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.explorerRepository.save(explorer);
		saved.getUserAccount().setIsBanned(saved.getIsBanned());

		return saved;
	}
	public Explorer findOne(final int explorerId) {
		Explorer result;
		result = this.explorerRepository.findOne(explorerId);
		return result;
	}

	public Explorer findByPrincipal() {
		Explorer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Explorer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Explorer result;
		result = this.explorerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	// Other business methods
	public Collection<Explorer> findExplorersBySuspicious() {
		Collection<Explorer> result;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.explorerRepository.findExplorersBySuspicious();
		Assert.notNull(result);
		return result;

	}

	public Boolean canExplorerEnrolSurvivalClass(final SurvivalClass survivalClass) {
		Explorer principal;
		Date currentMoment;
		Boolean isTripStarted;
		Boolean isTripCancelled;
		Boolean isTripPaid;
		Boolean hasExplorerEnrolledSurvivalClass;
		Boolean result;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date(System.currentTimeMillis() - 1);

		isTripStarted = survivalClass.getTrip().getStartDate().before(currentMoment);
		isTripCancelled = survivalClass.getTrip().getCancellationReason() != null;
		isTripPaid = false;

		for (final Application application : principal.getApplications())
			if (application.getTrip().equals(survivalClass.getTrip()) && application.getStatus().equals("ACCEPTED")) {
				isTripPaid = true;
				break;
			}

		hasExplorerEnrolledSurvivalClass = survivalClass.getExplorers().contains(principal);

		result = !isTripStarted && !isTripCancelled && isTripPaid && !hasExplorerEnrolledSurvivalClass;

		return result;
	}

	public Boolean canExplorerUnregisterSurvivalClass(final SurvivalClass survivalClass) {
		Explorer principal;
		Date currentMoment;
		Boolean isTripStarted;
		Boolean hasExplorerEnrolledSurvivalClass;
		Boolean result;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date(System.currentTimeMillis() - 1);

		isTripStarted = survivalClass.getTrip().getStartDate().before(currentMoment);

		hasExplorerEnrolledSurvivalClass = survivalClass.getExplorers().contains(principal);

		result = !isTripStarted && hasExplorerEnrolledSurvivalClass;

		return result;
	}

}
