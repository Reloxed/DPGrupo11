package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RefereeRepository;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository ------------------------------------

	@Autowired
	private RefereeRepository refereeRepository;

	// TODO
	// Supporting services -----------------------------------

	// Simple CRUD methods -----------------------------------

	public Referee create() {
		return new Referee();
	}

	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(int refereeId) {
		return this.refereeRepository.findOne(refereeId);
	}

	public Referee save(Referee r) {
		return this.refereeRepository.save(r);
	}

	// TODO
	public void delete(Referee r) {
		this.refereeRepository.delete(r);
	}

	// TODO
	// Other business methods -------------------------------
}
