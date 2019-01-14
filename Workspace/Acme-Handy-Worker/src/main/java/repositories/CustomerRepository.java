
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id=?1")
	Customer findCustomerByUserAccount(int userAccountId);

	@Query("select c from Customer c join c.fixUpTasks fix join fix.applications app join app.creditCard cc where cc.id = ?1 group by c.id")
	Collection<Customer> findByCreditCardId(int creditCardId);

	// C/9
	@Query("select c from Customer c join c.fixUpTasks f where c.fixUpTasks.size >(select avg(c.fixUpTasks.size)*1.1 from Customer c) order by count(f.applications.size) desc")
	List<Customer> customerTenPercentMoreFixUpTasksThanAverage();

	@Query("select c from Customer c join c.fixUpTasks f join f.applications a where a.id=?1")
	Customer findCustomerByApplicationId(int applicationId);

	@Query("select c from Customer c order by c.complaints.size desc")
	List<Customer> findCustomersWithMoreComplaints();
}
