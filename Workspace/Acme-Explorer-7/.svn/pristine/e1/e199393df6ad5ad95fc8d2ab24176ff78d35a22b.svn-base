
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
	
	@Query("select r.CV from Ranger r where r.id = ?1")
	Curriculum prueba(int rangerId);
	
	
	@Query("select c from Curriculum c where c.ranger.id = ?1")
	Curriculum findCurriculumByPrincipal(int rangerId);

	@Query("select t.ranger.CV from Trip t where t.id = ?1")
	Curriculum findCurriculumByTrip(int tripId);
}
