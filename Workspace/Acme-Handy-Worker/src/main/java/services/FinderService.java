package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRecordRepository;
import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	//Managed repository-----------
	
	@Autowired
	private FinderRepository finderRepository;
	
	
	//Supporting services ----------
	
	
}
