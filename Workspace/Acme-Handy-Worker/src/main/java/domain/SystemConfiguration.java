package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
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
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeEn() {
		return welcomeEn;
	}

	public void setWelcomeEn(String welcomeEn) {
		this.welcomeEn = welcomeEn;
	}

	@NotBlank
	public String getWelcomeEs() {
		return welcomeEs;
	}

	public void setWelcomeEs(String welcomeEs) {
		this.welcomeEs = welcomeEs;
	}

	@Digits(fraction = 2, integer = 3)
	@Range(min = 0, max = 1)
	public double getVAT() {
		return VAT;
	}

	public void setVAT(double vAT) {
		VAT = vAT;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getListCreditCardMakes() {
		return listCreditCardMakes;
	}

	public void setListCreditCardMakes(Collection<String> listCreditCardMakes) {
		this.listCreditCardMakes = listCreditCardMakes;
	}

	@Pattern(regexp = "\\+[0-9]{3}")
	@NotBlank
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 1, max = 24)
	public int getTimeResultsCached() {
		return timeResultsCached;
	}

	public void setTimeResultsCached(int timeResultsCached) {
		this.timeResultsCached = timeResultsCached;
	}

	@Range(min = 0, max = 100)
	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return spamWords;
	}

	public void setSpamWords(Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection
	public Collection<String> getPositiveWords() {
		return positiveWords;
	}

	public void setPositiveWords(Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@ElementCollection
	public Collection<String> getNegativeWords() {
		return negativeWords;
	}

	public void setNegativeWords(Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

}
