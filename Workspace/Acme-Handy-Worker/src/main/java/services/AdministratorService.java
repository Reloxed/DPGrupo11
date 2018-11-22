
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageBox;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services -------------------------
	@Autowired
	private MessageBoxService		messageBoxService;


	// Simple CRUDs methods

	public Administrator create() {
		Administrator principal;
		Administrator result;
		final List<MessageBox> messageBoxes;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result = new Administrator();
		/*
		 * messageBoxes = this.messageBoxService.createSystemMessageBoxs();
		 * 
		 * result.setIsSuspicious(false);
		 * result.setIsBanned(false);
		 * result.setMessageBoxes(messageBoxes);
		 * result.setSentMessages(Collections.<Message> emptyList());
		 * result.setReceivedMessages(Collections.<Message> emptyList());
		 * result.setSocialProfiles(Collections.<SocialProfile> emptyList());
		 */
		return result;

	}

	public Administrator findOne(final int administratorId) {

		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;

	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findAdministratorByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Administrator findAdministratorByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Administrator result;

		result = this.administratorRepository.findAdministratorByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}
}
