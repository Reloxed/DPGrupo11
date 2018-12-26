package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CreditCardServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------

	@Autowired
	private CreditCardService creditCardService;
	
	// Tests ------------------------------------------------------------------
	
	@Test
	public void testCreateCreditCardByHandyWorker(){
		CreditCard creditcard;
		String username = "sponsor1";
		super.authenticate(username);
		creditcard = creditCardService.create();
		Assert.notNull(creditcard);
		super.unauthenticate();			
	}
	
	@Test
	public void testCreateCreditCardByCustomer(){
		CreditCard creditcard;
		String username = "customer1";
		super.authenticate(username);
		creditcard = creditCardService.create();
		Assert.notNull(creditcard);
		super.unauthenticate();			
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotCreateCreditCardByCustomer(){
		CreditCard creditcard;
		String username = "handyworker1";
		super.authenticate(username);
		creditcard = creditCardService.create();
		Assert.notNull(creditcard);
		super.unauthenticate();			
	}
	
	@Test
	public void testFindAllCreditCards(){
		Collection<CreditCard> creditCards;
		String username = "customer1";
		super.authenticate(username);
		creditCards = creditCardService.findAll();
		Assert.notNull(creditCards);
		Assert.notEmpty(creditCards);
		super.unauthenticate();	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotFindAllCreditCards(){
		Collection<CreditCard> creditCards;
		String username = "admin1";
		super.authenticate(username);
		creditCards = creditCardService.findAll();
		Assert.notNull(creditCards);
		Assert.notEmpty(creditCards);
		super.unauthenticate();	
	}
	
	@Test
	public void testSaveAndFindOneCreditCard() {
		
		String username = "customer1";		
		CreditCard saved;
		
		super.authenticate(username);
		
		CreditCard creditcard = this.creditCardService.create(); 		
		
		creditcard.setHolderName("Pedro Picapiedra");
		creditcard.setBrandName("VISA");
		creditcard.setNumber("8731648964261256");
		creditcard.setExpirationMonth(12);
		creditcard.setExpirationYear(21);
		creditcard.setCVV(187);
		saved = this.creditCardService.save(creditcard);
		Assert.notNull(saved);
		
		super.unauthenticate();	
	}
	
	@Test
	public void testFindOneCreditCard() {
		String username = "customer1";		
		CreditCard creditCard;
		Collection<CreditCard> collCC;
		
		super.authenticate(username);
		
		collCC = this.creditCardService.findAll();
		creditCard = this.creditCardService.findOne(collCC.iterator().next().getId());
		Assert.notNull(creditCard);
		
		super.unauthenticate();	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotFindOneCreditCard() {
		String username = "customer1";	
		CreditCard creditCard;
		
		super.authenticate(username);
		
		creditCard = this.creditCardService.findOne(-2);
		Assert.notNull(creditCard);
		
		super.unauthenticate();	
	}
}
