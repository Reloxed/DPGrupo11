package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "Endorser")
public abstract class Endorser extends Actor {

}
