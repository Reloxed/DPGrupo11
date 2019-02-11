package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tapu;

@Repository
public interface TapuRepository extends JpaRepository<Tapu, Integer> {

	@Query("select avg(f.tapus.size),sqrt(sum(f.tapus.size * f.tapus.size) / count(f.tapus.size) -(avg(f.tapus.size) * avg(f.tapus.size))) from FixUpTask f")
	Double[] operationsTapus();

	@Query("select (sum(case when t.isFinal='1' then 1.0 else 0 end)/count(*)) from Tapu t")
	Double ratioTapusFinalMode();

	@Query("select (sum(case when t.isFinal='0' then 1.0 else 0 end)/count(*)) from Tapu t")
	Double ratioTapusDraftMode();

}
