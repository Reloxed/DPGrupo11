package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;
import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id=?1")
	Customer findCustomerByUserAccount(int userAccountId);
	
	@Query("select cc from Customer c join c.fixUpTasks fix join fix.applications app join app.creditCard cc where c.id =?1 group by cc.id")
	Collection<CreditCard> findCreditCardsByCustomerId (int customerId);
	
	@Query("select c from Customer c join c.fixUpTasks fix join fix.applications app join app.creditCard cc where cc.id = ?1 group by c.id")
	Customer findByCreditCardId(int creditCardId);
	
	@Query("select c from Customer c join c.fixUpTasks f where c.fixUpTasks.size" +
			">(select avg(c.fixUpTasks.size)*1.1 from Customer c) order by count(f.applications.size) desc")
	List<Customer> customerTenPercentMoraThanAverage();
}