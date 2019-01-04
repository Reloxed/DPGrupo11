
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Trip;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select t from Trip t where (t.publicationDate < CURRENT_DATE) AND (t.startDate BETWEEN ?1 AND ?2) AND (t.price BETWEEN ?3 AND ?4) AND (t.title LIKE ?5 OR t.description LIKE ?5 OR t.ticker LIKE ?5)")
	List<Trip> searchTrips(Date dateMin, Date dateMax, Double priceMin, Double priceMax, String keyword, Pageable pageable);

}
