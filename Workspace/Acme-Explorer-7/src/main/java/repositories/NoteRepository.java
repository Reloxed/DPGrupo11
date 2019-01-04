
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.trip.manager.id = ?1")
	public List<Note> findByManager(int managerId);

	@Query("select n from Note n where n.auditor.id = ?1")
	public Collection<Note> findByAuditor(int auditorId);

	@Query("select min(t.notes.size) from Trip t")
	Integer minNotesPerTrip();

	@Query("select max(t.notes.size) from Trip t")
	Integer maxNotesPerTrip();

	@Query("select avg(t.notes.size) from Trip t")
	Double averageNotesPerTrip();

	@Query("select sqrt(sum(t.notes.size * t.notes.size) / count(t.notes.size) - (avg(t.notes.size) * avg(t.notes.size))) from Trip t")
	Double stdDeviationNotesPerTrip();

}
