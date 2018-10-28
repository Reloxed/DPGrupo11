package domain;

import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Section extends DomainEntity{
	
	//Atributos

	
	private String tittle;
	private String text;
	private Collection<String> picture;
	
	
	//Metodos
	
	
	@NotBlank
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@URL
	public Collection<String> getPicture() {
		return picture;
	}
	public void setPicture(Collection<String> picture) {
		this.picture = picture;
	}
	
	
	
}
