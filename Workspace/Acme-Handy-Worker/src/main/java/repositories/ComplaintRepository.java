
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
}
