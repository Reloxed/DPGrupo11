package repositories;





import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import domain.Finder;




@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
	/* 
	//@Query("select f from FixUpTask f where (f.publishedMoment < CURRENT_DATE) AND (f.startMoment BETWEEN ?1 AND ?2) AND (f.maxPrice BETWEEN ?3 AND ?4) AND (f.address LIKE ?5 OR f.description LIKE ?5 OR f.ticker LIKE ?5) AND(f.warranty.title LIKE ?6)AND(f.category.parentCategory LIKE ?7)")
	Collection<FixUpTask> searchFixUpTasks(Date startMoment, Date endMoment, Double priceLow, Double priceHigh, String keyWord, Pageable pageable,Category category, Warranty warranty);
	*/
}
