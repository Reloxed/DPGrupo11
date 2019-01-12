package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	// B/2
	@Query("select max(r.notes.size), min(r.notes.size), avg(r.notes.size), sqrt(sum(r.notes.size * r.notes.size) / count(r.notes.size) -(avg(r.notes.size) * avg(r.notes.size))) from Report r")
	Double[] findNotesNumberOperations();

}
