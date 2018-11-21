package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsementRepository;
import repositories.EndorserRecordRepository;

@Service
@Transactional
public class EndorserRecordService {

	
	//Managed repository-----------
	
	@Autowired
	private EndorserRecordRepository endorserRecordRepository ;
	
	
	//Supporting services ----------
	
	
	
	
}
