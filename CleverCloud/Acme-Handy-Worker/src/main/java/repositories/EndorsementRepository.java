
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;
import domain.HandyWorker;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select a.applicant from Customer c join c.fixUpTasks f join f.applications a where a.status = 'ACCEPTED' and c.id=?1")
	Collection<HandyWorker> possibleHwRecipients(int customerId);

}
