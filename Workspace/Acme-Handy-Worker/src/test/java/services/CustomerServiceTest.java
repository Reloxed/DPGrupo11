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
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CustomerServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------

		@Autowired
		private CustomerService customerService;	
		
		// Tests ------------------------------------------------------------------
		
		@Test
		public void testFindAllCustomers() {
			Collection<Customer> customers;
			String username = "customer1";
			super.authenticate(username);
			customers = customerService.findAll();
			Assert.notNull(customers);
			Assert.notEmpty(customers);
			super.unauthenticate();	
		}

		@Test
		public void testFindOneCustomer() {
			Customer customer;
			String username = "customer1";
			super.authenticate(username);
			customer = customerService.findOne(2304);
			Assert.notNull(customer);
			super.unauthenticate();	
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testNotFindOneCustomer() {
			Customer customer;
			String username = "customer1";
			super.authenticate(username);
			customer = customerService.findOne(11811);
			Assert.notNull(customer);
			super.unauthenticate();	
		}
		
		@Test
		public void testSaveCustomer() {
			Customer customer, saved;
			Collection<Customer> customers;
			String username = "customer1";
			
			super.authenticate(username);
			
			customer = customerService.findOne(2304);
			Assert.notNull(customer);
			customer.setMiddleName("Vientos");
			customer.setAddress("Calle mi casa 28");
			saved = customerService.save(customer);
			customers = customerService.findAll();
			Assert.isTrue(customers.contains(saved));
			super.unauthenticate();	
		}
		
		@Test(expected=IllegalArgumentException.class) 
		public void testNotSaveCustomer_1() {
			Customer customer, saved;
			Collection<Customer> customers;
			
			// Wrong customer
			String username = "customer2";
			
			super.authenticate(username);
			
			customer = customerService.findOne(2304);
			Assert.notNull(customer);
			customer.setMiddleName("Vientos");
			customer.setAddress("Calle mi casa 28");
			saved = customerService.save(customer);
			customers = customerService.findAll();
			Assert.isTrue(customers.contains(saved));
			super.unauthenticate();	
		}
		
		@Test(expected=IllegalArgumentException.class) 
		public void testNotSaveCustomer_2() {
			Customer customer, saved;
			Collection<Customer> customers;
			
			// Wrong type of actor
			String username = "sponsor1";
			
			super.authenticate(username);
			
			customer = customerService.findOne(2304);
			Assert.notNull(customer);
			customer.setMiddleName("Vientos");
			customer.setAddress("Calle mi casa 28");
			saved = customerService.save(customer);
			customers = customerService.findAll();
			Assert.isTrue(customers.contains(saved));
			super.unauthenticate();	
		}
		
		@Test
		public void testNotSaveCustomer_3() {
			Customer customer, saved;
			Collection<Customer> customers;
			String username = "customer2";
			
			super.authenticate(username);
			
			// Want to change isSuspicious
			customer = customerService.findOne(2306);
			Assert.notNull(customer);
			customer.setMiddleName("Vientos");
			customer.setAddress("Calle mi casa 28");
			customer.setIsSuspicious(false);
			saved = customerService.save(customer);
			customers = customerService.findAll();
			Assert.isTrue(customers.contains(saved));
			super.unauthenticate();	
		}

}
