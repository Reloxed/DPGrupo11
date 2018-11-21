package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HandyWorkerRepository;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository-----------
	
	@Autowired
	private HandyWorkerRepository  handyWorkerRepository;
	
	//Supporting services ----------
	
	
	
}
