package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FixUpTaskRepository;



@Service
@Transactional
public class FixUpTaskService {

	//Managed repository-----------
	
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;
	
	
	//Supporting services ----------
	
	
	
}
