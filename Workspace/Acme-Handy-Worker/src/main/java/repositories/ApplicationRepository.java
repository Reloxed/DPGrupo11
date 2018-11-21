
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from HandyWorker h join h.applications a where h.id=?1")
	Collection<Application> findAllApplicationsByHandyWorker(int handyWorkerId);

	@Query("select a from FixUpTask f join f.applications a where f.id=?1 order by a.status")
	Collection<Application> findAllApplicationsByFixUpTask(int fixUptaskId);

	@Query("select (sum(case when a.status='PENDING' then 1.0 else 0 end)/count(*)) from Application a")
	Double ratioPendingApplications();

	@Query("select (sum(case when a.status='ACCEPTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double ratioAcceptedApplications();

	@Query("select (sum(case when a.status='REJECTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double ratioRejectedApplications();

	@Query("select count(a)/(select count(a) from Application a where a.fixUpTask.endMoment < CURRENT_DATE and a.status='PENDING')*1.0 from Application a where a.status='PENDING'")
	Double ratioPendingApplicationsElapsedPeriod();

	@Query("select avg(a.offeredPrice), max(a.offeredPrice), min(a.offeredPrice), sqrt(sum(a.offeredPrice*a.offeredPrice)/count(a.offeredPrice)-(avg(a.offeredPrice)*avg(a.offeredPrice))) from Application a group by 'a'")
	Double[] findDataApplicationsOfferedPrice();
}
