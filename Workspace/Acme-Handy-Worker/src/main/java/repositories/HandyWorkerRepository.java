package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.HandyWorker;


@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {
	//C/10
	@Query("select h from HandyWorker h where h.applications.size/ (select avg(h1.applications.size) from HandyWorker h1)>=1.1 order by h.applications.size")
	Collection<HandyWorker> findMoreApplicationsThanAvg();
	//B/5
	@Query("select h from HandyWorker h join h.applications a join a.fixUpTask f group by h order by count(f.complaints.size) desc")
	Collection<HandyWorker> findTopComplaintsHandyWorkers();
}
