package domain;

import java.util.Collection;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class SystemConfiguration extends DomainEntity {

	private String systemName;
	private String banner;
	private String welcomeEn;
	private String welcomeEs;
	private double VAT;
	private Collection<String> listCreditCardMakes;
	private String countryCode;
	private Integer timeResultsCached;
	private Integer maxResults;
	private Collection<String> spamWords;
	private Collection<String> positiveWords;
	private Collection<String> negativeWords;

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeEn() {
		return this.welcomeEn;
	}

	public void setWelcomeEn(final String welcomeEn) {
		this.welcomeEn = welcomeEn;
	}

	@NotBlank
	public String getWelcomeEs() {
		return this.welcomeEs;
	}

	public void setWelcomeEs(final String welcomeEs) {
		this.welcomeEs = welcomeEs;
	}

	@Digits(fraction = 2, integer = 3)
	@Range(min = 0, max = 1)
	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double vAT) {
		this.VAT = vAT;
	}

	@NotEmpty
	public Collection<String> getListCreditCardMakes() {
		return this.listCreditCardMakes;
	}

	public void setListCreditCardMakes(
			final Collection<String> listCreditCardMakes) {
		this.listCreditCardMakes = listCreditCardMakes;
	}

	@Pattern(regexp = "\\+[0-9]{3}")
	@NotBlank
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 1, max = 24)
	public int getTimeResultsCached() {
		return this.timeResultsCached;
	}

	public void setTimeResultsCached(final int timeResultsCached) {
		this.timeResultsCached = timeResultsCached;
	}

	@Range(min = 0, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}

	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

}
