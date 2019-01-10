
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;
import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {

	@Query("select e from Endorser e where e.userAccount.id=?1")
	Endorser findEndorserByUserAccount(int userAccountId);

	@Query("select e from Endorsement e where e.sender.id=?1 or e.recipient.id=?1")
	Collection<Endorsement> findEndorsementsByEndorser(int endorserId);

	@Query("select e from Endorsement e where e.sender.id=?1")
	Collection<Endorsement> findEndorsementsSendedByEndorser(int endorserId);

	@Query("select e from Endorsement e where e.recipient.id=?1")
	Collection<Endorsement> findEndorsementsReceivedByEndorser(int endorserId);

}
