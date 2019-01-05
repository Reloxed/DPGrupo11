
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
		Actor principal;

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		UserAccount ua = new UserAccount();
		ua.addAuthority(authority);

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isNull(principal);
			
			return null;
			
		} catch (IllegalArgumentException e) {
			
			result.setUserAccount(ua);
			result.setIsSuspicious(false);
			result.setMessageBoxes(this.messageBoxService.createSystemMessageBoxes());
			result.setSocialProfiles(Collections.<SocialProfile> emptyList());
			result.setSponsorships(Collections.<Sponsorship> emptyList());
			
			return result;
		}
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(int sponsorId) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);
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

	public Sponsor save(Sponsor sponsor) {
		Sponsor saved;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			try {
				Actor principal;
				principal = this.actorService.findByPrincipal();
				Assert.isNull(principal);
			} catch (IllegalArgumentException e) {
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				sponsor.getUserAccount().setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
			}
		} else {
			Sponsor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getUserAccount().getId() == sponsor.getUserAccount().getId());
			Assert.isTrue(principal.getIsSuspicious() == sponsor.getIsSuspicious());
		}

		saved = this.sponsorRepository.saveAndFlush(sponsor);
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
