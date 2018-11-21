package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationRecordRepository;

@Service
@Transactional
public class EducationRecordService {

	// Managed Repository
	
	@Autowired
	private EducationRecordRepository educationRecordRepository;
		
		
	// Supporting Services

		
		
	// Simple CRUD Methods
	
	
	
	// Other business methods

}
