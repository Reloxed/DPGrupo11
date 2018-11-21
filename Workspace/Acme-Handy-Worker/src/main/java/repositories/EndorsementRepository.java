package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Endorsement;


@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {
/*
 *48. An actor who is authenticated as a customer must be able to:
1. Manage his or her endorsements, which includes listing them, showing them, creating an endorsement about a handy worker who has been involved in any of his or
her fix-up tasks, updating them, and deleting them.
49. An actor who is authenticated as a handy worker must be able to: 
 *
 *Administrator:
 *The system must analyse the comments in the endorsements and compute the number of positive
words (p) and the number of negative words (n). The score must be computed as p– n normalised to range -1.00 up to +1.00 using a linear homothetic transformation.
2. Manage the lists of positive and negative words that the system uses to compute
the scores, which includes listing, showing, creating, updating, and deleting them.
 * */
}

