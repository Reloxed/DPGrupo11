
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select m from Manager m where m.isSuspicious = true")
	Collection<Manager> findManagersBySuspicious();

	@Query("select 1.0*count(m)/(select count(n) from Manager n) from Manager m where m.isSuspicious = true")
	Double ratioSuspiciousManagers();

}
