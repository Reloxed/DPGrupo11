
package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageBox;
import domain.Referee;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService {

	// Managed repository ------------------------------------

	@Autowired
	private RefereeRepository		refereeRepository;

	// Supporting services -----------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageBoxService		messageBoxService;


	// Constructors ------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Referee create() {
		Administrator principal;
		Referee res;
		Collection<MessageBox> messageBoxes;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		res = new Referee();

		res.setIsSuspicious(false);

		messageBoxes = this.messageBoxService.createSystemMessageBoxes();

		res.setMessageBoxes(messageBoxes);
		res.setSocialProfiles(Collections.<SocialProfile> emptyList());

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		final UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		res.setUserAccount(userAccount);
		return res;
	}

	public Collection<Referee> findAll() {
		Collection<Referee> res;
		res = this.refereeRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Referee findOne(final int refereeId) {
		Referee res;
		Assert.isTrue(refereeId != 0);
		res = this.refereeRepository.findOne(refereeId);
		Assert.notNull(res);
		return res;
	}

	public Referee save(final Referee referee) {
		Referee res;

		Assert.notNull(referee);

		if (referee.getId() == 0) {
			Administrator principal;
			principal = this.administratorService.findByPrincipal();
			Assert.notNull(principal);
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			referee.getUserAccount().setPassword(passwordEncoder.encodePassword(referee.getUserAccount().getPassword(), null));
		} else {
			Referee principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
		}

		res = this.refereeRepository.saveAndFlush(referee);

		return res;
	}

	// Other business methods -------------------------------

	public Referee findByPrincipal() {
		Referee res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findRefereeByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Referee findRefereeByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Referee result;

		result = this.refereeRepository.findRefereeByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Referee> findRefereeBySuspicious() {
		Collection<Referee> res;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		res = this.refereeRepository.findRefereeBySuspicious();
		Assert.notNull(res);

		return res;
	}
}
