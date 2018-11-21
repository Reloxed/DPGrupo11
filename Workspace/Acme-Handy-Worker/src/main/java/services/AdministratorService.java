
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;
}
