
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t where t.publicationDate < CURRENT_DATE")
	Collection<Trip> findAllPublishedTrips();

	@Query("select t from Trip t where t.publicationDate < CURRENT_DATE AND t.cancellationReason IS NULL")
	Collection<Trip> findAllAuditableTrips();

	@Query("select t from Trip t where t.startDate > CURRENT_DATE AND t.cancellationReason IS NULL")
	Collection<Trip> findAllFutureNoCancelledTrips();

	@Query("select t from Trip t where (t.publicationDate < CURRENT_DATE and t.startDate > CURRENT_DATE and t.cancellationReason IS NULL)")
	Collection<Trip> findAllPendingTrips();

	@Query("select t from Trip t where (t.endDate < CURRENT_DATE AND t.cancellationReason IS NULL)")
	Collection<Trip> findAllOverAndNoCancelledTrips();

	@Query("select t from Trip t join t.applications a where a.status='ACCEPTED' and a.applicant.id=?1")
	Collection<Trip> findPaidTripsByExplorerId(int explorerId);

	@Query("select t from Trip t where t.ticker LIKE ?1 or t.title LIKE ?1 or t.description LIKE ?1")
	Collection<Trip> findBySingleKey(String singleKey);

	@Query("select t from Trip t where (t.category.id = ?1 and t.publicationDate < CURRENT_DATE)")
	Collection<Trip> findByCategory(int categoryId);

	@Query("select t from Trip t, Tag g where g member of t.tags and g.id = ?1")
	Collection<Trip> findByTagId(int tagId);

	@Query("select avg(m.trips.size) from Manager m")
	Double averageTripsPerManager();

	@Query("select min(m.trips.size) from Manager m")
	Integer minTripsPerManager();

	@Query("select max(m.trips.size) from Manager m")
	Integer maxTripsPerManager();

	@Query("select sqrt(sum(m.trips.size * m.trips.size) / count(m.trips.size) - (avg(m.trips.size) * avg(m.trips.size))) from Manager m")
	Double stdDeviationTripsPerManager();

	@Query("select avg(t.price) from Trip t")
	Double averagePricePerTrip();

	@Query("select min(t.price) from Trip t")
	Integer minPricePerTrip();

	@Query("select max(t.price) from Trip t")
	Integer maxPricePerTrip();

	@Query("select sqrt(sum(t.price * t.price) / count(t.price) - (avg(t.price) * avg(t.price))) from Trip t")
	Double stdDeviationPricePerTrip();

	@Query("select avg(r.trips.size) from Ranger r")
	Double averageTripsPerRanger();

	@Query("select min(r.trips.size) from Ranger r")
	Integer minTripsPerRanger();

	@Query("select max(r.trips.size) from Ranger r")
	Integer maxTripsPerRanger();

	@Query("select sqrt(sum(r.trips.size * r.trips.size) / count(r.trips.size) - (avg(r.trips.size) * avg(r.trips.size))) from Ranger r")
	Double stdDeviationTripsPerRanger();

	@Query("select 1.0*count(t)/(select count(n) from Trip n) from Trip t where t.cancellationReason is not null")
	Double ratioCancelledTrips();

	@Query("select t from Trip t where t.applications.size >= 1.1*(select avg(b.applications.size) from Trip b) order by t.applications.size")
	Collection<Trip> tripsMostApplications();

	@Query("select 1.0*count(t)/(select count(b) from Trip b) from Trip t where t.audits.size > 0")
	Double ratioTripsWithAudit();

}
