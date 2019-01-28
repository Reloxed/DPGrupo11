package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Observation;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Integer>{

	@Query("select avg(f.observations.size),sqrt(sum(f.observations.size * f.observations.size) / count(f.observations.size) -(avg(f.observations.size) * avg(f.observations.size))) from FixUpTask f")
	Double[] operationsObservations();

	@Query("select (sum(case when o.isFinal='1' then 1.0 else 0 end)/count(*)) from Observation o")
	Double ratioObservationsFinalMode();

	@Query("select (sum(case when o.isFinal='0' then 1.0 else 0 end)/count(*)) from Observation o")
	Double ratioObservationsDraftMode();
}
