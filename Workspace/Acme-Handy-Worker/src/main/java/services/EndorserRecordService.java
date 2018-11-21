package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.EndorserRecord;
import domain.HandyWorker;
import repositories.EndorserRecordRepository;

@Service
@Transactional
public class EndorserRecordService {

	
	//Managed repository-----------
	
	@Autowired
	private EndorserRecordRepository endorserRecordRepository ;
	
	
	//Supporting services ----------
	@Autowired
	private HandyWorkerService handyWorkerService;
	

	
	
	
	
	//Simple CRUD methods-------
	public EndorserRecord create(){
		EndorserRecord result;
		HandyWorker principal;
		
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result= new EndonserRecord();
		Assert.notNull(result);
		result.set
		
		
		return null;
		
	}
	public Collection<EndorserRecord> findAll(){
		return null;
		
	} 
	
	public EndorserRecord findOne(int endorserRecordId){
		return null;
		
	}
	
	public EndorserRecord save(EndorserRecord endorserRecord){
		return null;
		
	}
	
	public void  delete(EndorserRecord endorserRecord){
		return;
		
	}
	
	
	
	
	//Other business methods--------
	
	
	
	
}
