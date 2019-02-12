
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.FixUpTask;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c.childCategories from Category c where c.id = ?1")
	Collection<Category> findChildCategories(int categoryId);

	@Query("select c from Category c join c.name cn where (KEY(cn)='English' and cn='CATEGORY')")
	Category findRoot();
	
	@Query("select f from FixUpTask f where f.category.id= ?1")
	Collection<FixUpTask> CategoryInFixUpTask(int categoryId);
}
