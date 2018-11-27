package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {

	@Query("select e from Endorser e where e.userAccount.id=?1")
	Endorser findEndorserByUserAccount(int userAccountId);

}
