package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tonema;

@Repository
public interface TonemaRepository extends JpaRepository<Tonema, Integer> {

	@Query("select x from Customer c join c.fixUpTasks f join f.tonemas x where c.id=?1")
	Collection<Tonema> findTonemaByPrincial(int customerId);
	
	@Query("select x from FixUpTask f join f.tonemas x where f.id=?1 and x.isFinal=true")
	Collection<Tonema> findTonemaFinalByFix(int fixUpTaskId);
	
	@Query("select avg(f.tonemas.size),sqrt(sum(f.tonemas.size*f.tonemas.size)/count(f.tonemas.size)-(avg(f.tonemas.size)*avg(f.tonemas.size))) from FixUpTask f join f.tonemas x where x.isFinal=true group by 'f'")
	Double[] avgstdOfTonema();
	
	@Query("select count(x) / (select count(x) from Tonema x)*1.0 from Tonema x where x.isFinal=true")
	Double ratioOfPublishedTonema();
	
	@Query("select count(x) / (select count(x) from Tonema x)*1.0 from Tonema x where x.isFinal=false")
	Double ratioOfUnpublishedTonema();
}
