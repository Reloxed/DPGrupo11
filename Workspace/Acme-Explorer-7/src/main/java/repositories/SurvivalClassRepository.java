
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.SurvivalClass;

@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass, Integer> {

	@Query("select t.survivalClasses from Trip t where t.manager.id = ?1")
	Collection<SurvivalClass> findByManager(int managerId);
	
	
	
	
}
