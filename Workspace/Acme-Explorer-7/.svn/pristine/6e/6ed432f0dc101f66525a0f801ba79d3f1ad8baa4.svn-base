
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.applicant.id = ?1")
	Collection<Application> findByExplorer(int explorerId);

	@Query("select a from Application a where a.trip.manager.id = ?1")
	Collection<Application> findByManagerId(int managerId);

	@Query("select a from Application a where a.status='ACCEPTED' and a.applicant.id=?1 and a.trip.startDate > CURRENT_DATE")
	Collection<Application> findCancellableApplicationsByExplorer(int explorerId);

	@Query("select a from Application a where a.status='ACCEPTED' and a.applicant.id=?1")
	Collection<Application> findAcceptedByExplorer(int explorerId);

	@Query("select avg(t.applications.size) from Trip t")
	Double averageApplicationsPerTrip();

	@Query("select min(t.applications.size) from Trip t")
	Integer minApplicationsPerTrip();

	@Query("select max(t.applications.size) from Trip t")
	Integer maxApplicationsPerTrip();

	@Query("select sqrt(sum(t.applications.size * t.applications.size) / count(t.applications.size) - (avg(t.applications.size) * avg(t.applications.size))) from Trip t")
	Double stdDeviationApplicationsPerTrip();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='PENDING'")
	Double ratioPendingApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='DUE'")
	Double ratioDueApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='ACCEPTED'")
	Double ratioAcceptedApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='CANCELLED'")
	Double ratioCancelledApplications();

}
