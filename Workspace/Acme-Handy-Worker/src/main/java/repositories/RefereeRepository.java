package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r where r.userAccount.id=?1")
	Referee findRefereeByUserAccount(int userAccountId);

	@Query("select r from Referee r where r.isSuspicious = true")
	Collection<Referee> findRefereeBySuspicious();

}
