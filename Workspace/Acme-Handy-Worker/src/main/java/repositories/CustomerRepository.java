package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id=?1")
	Customer findCustomerByUserAccount(int userAccountId);
	
	@Query("select cc from Customer c join c.fixUpTasks fix join fix.applications app join app.creditCard cc where c.id =?1 group by cc.id")
	Collection<CreditCard> findCreditCardsByCustomerId (int customerId);
}