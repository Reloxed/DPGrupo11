package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.XXXX;

@Repository
public interface XXXXRepository extends JpaRepository<XXXX, Integer> {

	@Query("select avg(f.XXXXs.size),sqrt(sum(f.XXXXs.size * f.XXXXs.size) / count(f.XXXXs.size) -(avg(f.XXXXs.size) * avg(f.XXXXs.size))) from FixUpTask f")
	Double[] operationsXXXXs();

	@Query("select (sum(case when x.isFinal='1' then 1.0 else 0 end)/count(*)) from XXXX x")
	Double ratioXXXXsFinalMode();

	@Query("select (sum(case when x.isFinal='0' then 1.0 else 0 end)/count(*)) from XXXX x")
	Double ratioXXXXsDraftMode();

}
