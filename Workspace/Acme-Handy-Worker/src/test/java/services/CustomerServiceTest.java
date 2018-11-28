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
import domain.Application;
import domain.CreditCard;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CustomerServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------

		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private CreditCardService creditCardService;	
		
		@Autowired
		private ApplicationService applicationService;	
		
		// Tests ------------------------------------------------------------------
		
		@Test
		public void testCreateCustomer() {
			Customer customer;

			customer = this.customerService.create();
			Assert.notNull(customer);
		}
		
		@Test
		public void testFindAllCustomers() {
			Collection<Customer> customers;
			String username = "customer1";
			super.authenticate(username);
			customers = this.customerService.findAll();
			Assert.notNull(customers);
			Assert.notEmpty(customers);
			super.unauthenticate();	
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testNotFindOneCustomer() {
			Customer customer;
			String username = "customer1";
			super.authenticate(username);
			customer = this.customerService.findOne(11811824);
			Assert.notNull(customer);
			super.unauthenticate();	
		}
		
		@Test
		public void testSaveAndFindOneCustomer() {
			Customer customer = this.customerService.create(); 
			Customer saved;
			String username = "customer1";
			
			super.authenticate(username);
			
			customer.setName("Pedro");
			customer.setSurname("Picapiedra");
			customer.setEmail("pedropicapiedra@yahoo.es");
			customer.setMiddleName("Vientos");
			customer.setAddress("Calle mi casa 28");
			customer.getUserAccount().setUsername("Pedro28");
			customer.getUserAccount().setPassword("adsadd252f");
			saved = this.customerService.save(customer);
			customer = this.customerService.findOne(saved.getId());
			Assert.notNull(customer);
			super.unauthenticate();	
		}
		
		
		@Test(expected=IllegalArgumentException.class) 
		public void testNotSaveCustomer_1() {
			Customer customer1 = this.customerService.create();
			Customer customer2 = this.customerService.create(); 
			Customer saved1, saved2;
			String username1 = "customer1";
			String username2 = "customer2";
			
			super.authenticate(username1);
			
			customer1.setName("Pedro");
			customer1.setSurname("Picapiedra");
			customer1.setEmail("pedropicapiedra@yahoo.es");
			customer1.setMiddleName("Vientos");
			customer1.setAddress("Calle mi casa 28");
			customer1.getUserAccount().setUsername("Pedro28");
			customer1.getUserAccount().setPassword("adsadd252f");
			saved1 = this.customerService.save(customer1);
			int customerId = saved1.getId();
			customer1 = this.customerService.findOne(saved1.getId());
			
			super.unauthenticate();	
			
			// Wrong customer			
			super.authenticate(username2);
			
			customer2 = this.customerService.findOne(customerId);
			Assert.notNull(customer2);
			customer2.setMiddleName("Vientos");
			customer2.setAddress("Calle mi casa 28");
			saved2 = this.customerService.save(customer2);
			Assert.notNull(saved2);
			super.unauthenticate();	
		}
	
		@Test(expected=IllegalArgumentException.class) 
		public void testNotSaveCustomer_2() {
			Customer customer1 = this.customerService.create();
			Customer customer2 = this.customerService.create(); 
			Customer saved1, saved2;
			String username1 = "customer1";
			String username2 = "sponsor1";
			
			super.authenticate(username1);
			
			customer1.setName("Pedro");
			customer1.setSurname("Picapiedra");
			customer1.setEmail("pedropicapiedra@yahoo.es");
			customer1.setMiddleName("Vientos");
			customer1.setAddress("Calle mi casa 28");
			customer1.getUserAccount().setUsername("Pedro28");
			customer1.getUserAccount().setPassword("adsadd252f");
			saved1 = this.customerService.save(customer1);
			int customerId = saved1.getId();
			customer1 = this.customerService.findOne(saved1.getId());
			
			super.unauthenticate();	
			
			// Wrong type of actor			
			super.authenticate(username2);
			
			customer2 = this.customerService.findOne(customerId);
			Assert.notNull(customer2);
			customer2.setMiddleName("Vientos");
			customer2.setAddress("Calle mi casa 28");
			saved2 = this.customerService.save(customer2);
			Assert.notNull(saved2);
			super.unauthenticate();	
		}
//		
//		@Test
//		public void testNotSaveCustomer_3() {
//			Customer customer, saved;
//			Collection<Customer> customers;
//			String username = "customer1";
//			
//			super.authenticate(username);
//			
//			// Want to change isSuspicious
//			customer = this.customerService.findOne(2304);
//			Assert.notNull(customer);
//			customer.setMiddleName("Vientos");
//			customer.setAddress("Calle mi casa 28");
//			customer.setIsSuspicious(true);
//			saved = this.customerService.save(customer);
//			customers = this.customerService.findAll();
//			Assert.isTrue(customers.contains(saved));
//			super.unauthenticate();	
//		}
		
		@Test
		public void testFindersById() {
			Customer customer, customerByCC, customerByApp;
			Collection<CreditCard> collCC;
			Collection<Application> collApp;
			CreditCard creditCard;
			Application app;
			String username = "customer1";
			
			super.authenticate(username);
			
			// Testing findCreditCardsByCustomerId and findByCreditCardId
			customer = this.customerService.findOne(2335);
			Assert.notNull(customer);
			collCC = this.customerService.findCreditCardsByCustomerId(customer.getId());
			Assert.notEmpty(collCC);
			creditCard = collCC.iterator().next();
			Assert.notNull(creditCard);
			customerByCC = this.customerService.findByCreditCardId(creditCard.getId());
			Assert.notNull(customerByCC);
			
			// Testing findCreditCardsByCustomerId and findByCreditCardId
			collApp = this.applicationService.findAll();
			Assert.notEmpty(collApp);
			app = collApp.iterator().next();
			Assert.notNull(app);
			customerByApp = this.customerService.findCustomerByApplicationId(app.getId());
			Assert.notNull(customerByCC);
			super.unauthenticate();	
		}
		
		@Test
		public void testTopThreeCustomersTenPercentMoraThanAverage(){
			Collection<Customer> collCC;
			collCC = this.customerService.topThreeCustomersTenPercentMoraThanAverage();
			Assert.notEmpty(collCC);
		}

}
