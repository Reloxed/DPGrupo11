
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
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;
import domain.HandyWorker;
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

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private EndorserService			endorserService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors ------------------------------------

	public AdministratorService() {
		super();
	}

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
		result.setUserAccount(ua);
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

		result = this.administratorRepository.saveAndFlush(admin);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

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

	public Double calculateScore(final Endorser endorser) {
		Double res, positiveValue, negativeValue;
		final Collection<Endorsement> endorsements;

		res = 0.;
		positiveValue = 0.;
		negativeValue = 0.;
		endorsements = this.endorserService.findEndorsementsReceivedByEndorser(endorser.getId());
		for (final Endorsement e : endorsements) {
			final String[] comments = e.getComments().split(" ");
			for (final String word : comments) {
				positiveValue += this.utilityService.getNumberPositiveWords(word);
				negativeValue += this.utilityService.getNumberNegativeWords(word);
			}
		}

		if (positiveValue == 0 && negativeValue == 0)
			res = 1.;
		else if (negativeValue == 0)
			res = 1.;
		else if (positiveValue == 0)
			res = -1.;
		else
			res = (positiveValue - negativeValue) / (positiveValue + negativeValue);
		return res;
	}

	// Req. 50.1 : Launch a process that computes a score for every customer and handy worker.
	public void calculateAllScores() {
		final Collection<HandyWorker> hws;
		Collection<Customer> customers;
		Administrator principal;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		hws = this.handyWorkerService.findAll();
		customers = this.customerService.findAll();

		for (final HandyWorker hw : hws) {
			hw.setScore(this.calculateScore(hw));
			this.handyWorkerService.save(hw);
		}

		for (final Customer c : customers) {
			c.setScore(this.calculateScore(c));
			this.customerService.save(c);
		}
	}
}
