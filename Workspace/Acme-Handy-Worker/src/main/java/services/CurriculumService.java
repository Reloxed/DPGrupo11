package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CurriculumRepository;

@Service
@Transactional
public class CurriculumService {
	
	// Managed Repository
	
	@Autowired
	private CurriculumRepository curriculumRepository;
	
	// Supporting Services

	
	
	// Simple CRUD Methods
	
	
	
	// Other business methods
}

