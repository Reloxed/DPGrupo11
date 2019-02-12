package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Nust;


@Repository
public interface NustRepository extends JpaRepository<Nust, Integer>  {

	@Query("select count(n)*1.0/(select count(f) from FixUpTask f) from Nust n where n.isFinal=true")
	Double avgNustsPublishedperFixUpTask();
	
	@Query("select sqrt(sum(f.nusts.size*f.nusts.size)/count(f.nusts.size)-(avg(f.nusts.size)*avg(f.nusts.size))) from FixUpTask f join f.nusts n where n.isFinal=true group by 'f'")
	Double devStandardNustPublished();
	
	@Query("select (sum(case when n.isFinal='1' then 1.0 else 0 end)/count(*)) from Nust n")
	Double ratioNustsFinalMode();
	
	@Query("select (sum(case when n.isFinal='0' then 1.0 else 0 end)/count(*)) from Nust n")
	Double ratioNustsDraftMode();

	@Query("select n from Nust n where n.isFinal='1'")
	Collection<Nust> nustsPublished();
	
}
