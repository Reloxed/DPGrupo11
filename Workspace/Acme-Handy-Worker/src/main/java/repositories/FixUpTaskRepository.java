package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {
	// C/2
	@Query("select max(f.applications.size), min(f.applications.size), avg(f.applications.size),sqrt(sum(f.applications.size * f.applications.size) / count(f.applications.size) -(avg(f.applications.size) * avg(f.applications.size))) from FixUpTask f")
	Double[] findApplicationsNumberOperations();

	// C/3
	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice),sqrt(sum(f.maxPrice*f.maxPrice) / count(f.maxPrice) - (avg(f.maxPrice)* avg(f.maxPrice))) from FixUpTask f")
	Double[] findMaxPricesNumberOperations();

	// B/1
	@Query("select max(f.complaints.size), min(f.complaints.size), avg(f.complaints.size), sqrt(sum(f.complaints.size * f.complaints.size) / count(f.complaints.size) - (avg(f.complaints.size) * avg(f.complaints.size))) from FixUpTask f")
	Double[] findComplaintsNumberOperations();

	// B/3
	@Query("select count(f) /(select count(f) from FixUpTask f where f.complaints is not empty)*1.0 from FixUpTask f")
	Double ratioFixUpTaskWithComplaints();
}
