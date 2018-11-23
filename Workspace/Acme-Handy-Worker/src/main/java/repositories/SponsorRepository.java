package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;
import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{
	
	@Query("select cc from Sponsor s join s.sponsorships ss join ss.creditCard cc where c.id = ?1 group by cc.id")
	Collection<CreditCard> findCreditCardsBySponsorId (int sponsorId);

}
