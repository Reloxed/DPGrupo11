package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{
	
	@Query("select s from Sponsor s join s.sponsorships ss join ss.creditCard cc where cc.id = ?1 group by s.id")
	Sponsor findByCreditCardId(int creditCardId);

}
