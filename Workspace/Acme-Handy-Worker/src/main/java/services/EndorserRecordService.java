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
	
	//añadir curriculum?
	
	
	
	
	//Simple CRUD methods-------
	public EndorserRecord create(){
		EndorserRecord result;
		HandyWorker principal;
		//mirar si solo puede crearlo o colgarlo un handyworker
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result= new EndorserRecord();
		Assert.notNull(result);
		
		return result;
		
	}
	public Collection<EndorserRecord> findAll(){
		Collection <EndorserRecord> result;
		result=this.endorserRecordRepository.findAll();
		return result;
		
	} 
	
	public EndorserRecord findOne(final int endorserRecordId){
		EndorserRecord result;
		result=this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.isNull(result);//quito el not null por q puede que no haya endorserRecord?
		return result;
		
	}
	
	public EndorserRecord save(EndorserRecord endorserRecord){
		//quien puede guardar un record solo handy?
		Assert.notNull(endorserRecord);
		EndorserRecord result;
		HandyWorker principal;
		Collection<EndorserRecord> endorsersRecord;
		
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());
		
		endorsersRecord=principal.getCurriculum().getEndorserRecords();
		result=this.endorserRecordRepository.save(endorserRecord);
		Assert.notNull(result);
		//tengo que poner una condición con el formato del telefono
		//email?etc
		endorsersRecord.add(result);
		
		return result;
		
		
		
	}
	
	public void  delete(EndorserRecord endorserRecord){
		//quien puede eliminarlo
		
		
		return;
		
	}
	
	
	
	
	//Other business methods--------
	
	
	
	
}
