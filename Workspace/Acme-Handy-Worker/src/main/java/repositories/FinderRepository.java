package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Finder;



@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
	
	@Query ("select t from Trip t where (t.startDate BETWEEN ?1 AND ?2) AND (t.price BETWEEN ?3 AND ?4) AND (t.title LIKE ?5 OR t.description LIKE ?5 OR t.ticker LIKE ?5)")
	Collection<Trip> searchTrips (Date dateMin, Date dateMax, Double priceMin, Double priceMax, String keyword);
	/*40. The results of a finder are cached for one hour by default. The administrator should be able
	to configure that period at will in order to adjust the performance of the system. The minimum time’s one hour and the maximum time’s 24 hours.
	41. The maximum number of results that a finder returns is 10 by default. The administrator
should be able to change this parameter in order to adjust the performance of the system.
The absolute maximum is 100 results.
	*/
}
