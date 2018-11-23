package repositories;

import java.util.Collection;
import java.util.Date;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;



@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
	//bidireccional?para poder acceder a las fixuptask, probar query en el test services
	@Query("select f from FixUpTask f where (f.publishedMoment < CURRENT_DATE) AND (f.startMoment BETWEEN ?1 AND ?2) AND (f.maxPrice BETWEEN ?3 AND ?4) AND (f.address LIKE ?5 OR f.description LIKE ?5 OR f.ticker LIKE ?5)")
	Collection<FixUpTask> searchFixUpTasks(Date startMoment, Date endMoment, Double priceLow, Double priceHigh, String keyWord, Pageable pageable);
	//tengo que consultar categoría,garantia etc?
}
