package domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class SponsorShip {
	
	//Atributos

	private String banner;
	private String targetPage;
	private CreditCard creditCard;
	private Tutorial tutorial;
	
	
	//Metodos
	
	
	@NotBlank
	@URL
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	@NotBlank
	@URL
	public String getTargetPage() {
		return targetPage;
	}
	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	public Tutorial getTutorial() {
		return tutorial;
	}
	
	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}
	
	
}
