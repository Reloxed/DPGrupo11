package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Money;

@Repository
public interface MoneyRepository extends JpaRepository<Money, Integer>{

}
