package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	// C/1
	@Query("select max(c.fixUpTasks.size), min(c.fixUpTasks.size), avg(c.fixUpTasks.size), sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size) / count(c.fixUpTasks.size) -(avg(c.fixUpTasks.size) * avg(c.fixUpTasks.size))) from Customer c")
	Double[] findFixUpTaskNumberOperations();

	// C/2
	@Query("select max(f.applications.size), min(f.applications.size), avg(f.applications.size),sqrt(sum(f.applications.size * f.applications.size) / count(f.applications.size) -(avg(f.applications.size) * avg(f.applications.size))) from FixUpTask f")
	Double[] findApplicationsNumberOperations();

	// C/3
	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice),sqrt(sum(f.maxPrice*f.maxPrice) / count(f.maxPrice) - (avg(f.maxPrice)* avg(f.maxPrice))) from FixUpTask f")
	Double[] findMaxPricesNumberOperations();

	// C/4
	@Query("select avg(a.offeredPrice), max(a.offeredPrice), min(a.offeredPrice),sqrt(sum(a.offeredPrice*a.offeredPrice)/count(a.offeredPrice)-(avg(a.offeredPrice)*avg(a.offeredPrice))) from Application a group by 'a'")
	Double[] findMaxPricesApplicationNumberOperations();

	// B/1
	@Query("select max(f.complaints.size), min(f.complaints.size), avg(f.complaints.size), sqrt(sum(f.complaints.size * f.complaints.size) / count(f.complaints.size) - (avg(f.complaints.size) * avg(f.complaints.size))) from FixUpTask f")
	Double[] findComplaintsNumberOperations();

	// B/3
	@Query("select count(f) /(select count(f) from FixUpTask f where f.complaints is not empty)*1.0 from FixUpTask f")
	Double ratioFixUpTaskWithComplaints();

	@Query("select f from Customer c join c.fixUpTasks f where c.id=?1")
	Collection<FixUpTask> FixUpTaskByCustomer(int customerId);
	
	@Query("select f from Customer c join c.fixUpTasks f where c.userAccount.isBanned = true group by f.id")
	List<FixUpTask> findBannedCustomers();

}
