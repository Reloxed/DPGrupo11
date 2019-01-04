
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	@Query("select lt from LegalText lt where lt.isDraft = 'FALSE'")
	Collection<LegalText> findAllFinalMode();

}
