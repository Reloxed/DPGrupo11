package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("select c from Customer c join c.fixUpTasks fix join fix.applications ap join ap.creditCard cc where cc.id = ?1 group by c.id")
	Customer findByCreditCardId(int creditCardId);

}