package domain;



import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	private String systemName;
	private Map<String,String> welcomeMessage;
	private String banner;
	private double VAT;
	private String listCreditCardMakes;
	private String countryCode;
	private Integer timeResultsCached;
	private Integer maxResults;
	private String spamWords;
	private String positiveWords;
	private String negativeWords;

	
	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}
	
	@NotNull
	@ElementCollection
	public Map<String, String> getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(Map<String, String> welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@Digits(fraction = 2, integer = 3)
	@Range(min = 0, max = 1)
	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double vAT) {
		this.VAT = vAT;
	}

	@NotBlank
	public String getListCreditCardMakes() {
		return this.listCreditCardMakes;
	}

	public void setListCreditCardMakes(final String listCreditCardMakes) {
		this.listCreditCardMakes = listCreditCardMakes;
	}

	@Pattern(regexp = "^\\+[0-9]{3}$")
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

	public void setTimeResultsCached(final Integer timeResultsCached) {
		this.timeResultsCached = timeResultsCached;
	}

	@Range(min = 0, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final Integer maxResults) {
		this.maxResults = maxResults;
	}

	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	public String getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	public String getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

}