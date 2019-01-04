
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Integer> {

	@Query("select r.CV from Ranger r where r.id = ?1")
	Curriculum prueba(int rangerId);

	@Query("select r from Ranger r where r.userAccount.id = ?1")
	Ranger findByUserAccountId(int userAccountId);

	@Query("select r from Ranger r where r.isSuspicious = true")
	Collection<Ranger> findRangersBySuspicious();

	@Query("select 1.0*count(r)/(select count(n) from Ranger n) from Ranger r where r.CV is not null")
	Double ratioRangersWithRegisteredCurriculum();

	@Query("select 1.0*count(r)/(select count(b) from Ranger b where b.CV is not null) from Ranger r where r.CV.endorserRecord.size > 0")
	Double ratioRangersWithEndorsedCurriculum();

	@Query("select 1.0*count(r)/(select count(b) from Ranger b) from Ranger r where r.isSuspicious = true")
	Double ratioSuspiciousRangers();

}
