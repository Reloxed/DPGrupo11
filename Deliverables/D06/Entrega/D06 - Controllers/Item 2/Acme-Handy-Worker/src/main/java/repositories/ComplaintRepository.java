

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
	

	@Query("select r.complaints from Referee r where r.id=?1")
	Collection<Complaint> findComplaintsByReferee(int refereeId);

	@Query("select c from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints c where h.id=?1 group by c.id")
	Collection<Complaint> findComplaintsByHandyWorkerId(int handyWorkerId);
	
	@Query("select c.complaints from Customer c where c.id=?1")
	Collection<Complaint> findComplaintsByCustomer(int customerId);
	

}

