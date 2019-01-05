
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
	
	@Query("select c from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints c where h.id=?1 group by c.id")
	Collection<Complaint> findComplaintsByHandyWorkerId(int handyWorkerId);
	
}
