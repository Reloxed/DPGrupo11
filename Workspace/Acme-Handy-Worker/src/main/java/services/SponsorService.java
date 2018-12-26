
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.MessageBox;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository

	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageBoxService	messageBoxService;


	// Constructors ------------------------------------

	public SponsorService() {
		super();
	}

	// Simple CRUD Methods

	public Sponsor create() {
		Sponsor result = new Sponsor();
		Collection<MessageBox> messageBoxes;
		Actor principal;

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		UserAccount ua = new UserAccount();
		ua.addAuthority(authority);

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isNull(principal);
		} catch (IllegalArgumentException e) {
			result.setUserAccount(ua);

			messageBoxes = this.messageBoxService.createSystemMessageBoxes();

			result.setIsSuspicious(false);
			result.setMessageBoxes(messageBoxes);
			result.setSocialProfiles(Collections.<SocialProfile> emptyList());
			result.setSponsorships(Collections.<Sponsorship> emptyList());
		}
		return result;

	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> sponsors;

		sponsors = this.sponsorRepository.findAll();

		return sponsors;
	}

	public Sponsor findOne(int sponsorId) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);

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

	public Sponsor save(Sponsor s) {
		Sponsor saved;
		Assert.notNull(s);

		if (s.getId() == 0) {
			try {
				Actor principal;
				principal = this.actorService.findByPrincipal();
				Assert.isNull(principal);
			} catch (IllegalArgumentException e) {
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				s.getUserAccount().setPassword(passwordEncoder.encodePassword(s.getUserAccount().getPassword(), null));
			}
		} else {
			Sponsor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount().getId() == s.getUserAccount().getId());
			Assert.isTrue(principal.getIsSuspicious() == s.getIsSuspicious());
		}

		saved = this.sponsorRepository.saveAndFlush(s);
		return saved;
	}

	// Other business methods

	public Collection<Sponsor> findByCreditCardId(int creditCardId) {
		Collection<Sponsor> res;

		res = this.sponsorRepository.findByCreditCardId(creditCardId);
		Assert.notNull(res);

		return res;
	}

	public Collection<CreditCard> findCreditCardsBySponsorId(int sponsorId) {
		Collection<CreditCard> collCC = new ArrayList<>();
		collCC = sponsorRepository.findCreditCardsBySponsorId(sponsorId);
		return collCC;
	}

}
