
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageBox;
import domain.SocialProfile;

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
		final Collection<MessageBox> messageBoxes;
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		final List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		final UserAccount ua = new UserAccount();
		ua.setAuthorities(authorities);

		result = new Administrator();

		messageBoxes = this.messageBoxService.createSystemMessageBoxes();

		result.setIsSuspicious(false);
		ua.setIsBanned(false);
		result.setMessageBoxes(messageBoxes);
		result.setSocialProfiles(Collections.<SocialProfile> emptyList());

		return result;
	}

	public Administrator save(final Administrator admin) {
		Administrator result, principal;
		Assert.notNull(admin);
		Md5PasswordEncoder encoder;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		encoder = new Md5PasswordEncoder();

		if (admin.getId() == 0)
			admin.getUserAccount().setPassword(encoder.encodePassword(admin.getUserAccount().getPassword(), null));

		result = this.administratorRepository.save(admin);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;

	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

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
