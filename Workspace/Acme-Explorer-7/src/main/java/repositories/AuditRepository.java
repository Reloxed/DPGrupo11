
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select t.audits from Trip t where t.id = ?1")
	List<Audit> findAuditsByTrip(int tripId);

	@Query("select a.audits from Auditor a where a.id=?1")
	List<Audit> findByAuditor(int auditorId);

	@Query("select min(t.audits.size) from Trip t")
	Integer minAuditsPerTrip();

	@Query("select max(t.audits.size) from Trip t")
	Integer maxAuditsPerTrip();

	@Query("select avg(t.audits.size) from Trip t")
	Double averageAuditsPerTrip();

	@Query("select sqrt(sum(t.audits.size * t.audits.size) / count(t.audits.size) - (avg(t.audits.size) * avg(t.audits.size))) from Trip t")
	Double stdDeviationAuditsPerTrip();

}
