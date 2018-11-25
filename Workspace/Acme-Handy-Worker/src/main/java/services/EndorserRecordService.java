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
	
	//Constructor ----------------------------------------------------
		public EndorserRecordService() {
			super();
		}
	
	
	
	//Simple CRUD methods-------
	public EndorserRecord create(){
		EndorserRecord result;
		HandyWorker principal;
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
		//Assert.isNull(result);
		return result;
		
	}
	
	public EndorserRecord save(EndorserRecord endorserRecord){
	
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
		/**if(patron){
		 * .save
		 * 		}
		 * else{excepcion
		 * }
		 * 
		 * */
		
		
		endorsersRecord.add(result);
		principal.getCurriculum().setEndorserRecords(endorsersRecord);
		
		
		return result;
		
		
		
	}
	
	public void  delete(EndorserRecord endorserRecord){
		
		HandyWorker principal;
		Collection<EndorserRecord> collectionEndorserRecords;
		
		principal=this.handyWorkerService.findByPrincipal();
		
		Assert.notNull(principal);
		
		collectionEndorserRecords=principal.getCurriculum().getEndorserRecords();
		
		this.endorserRecordRepository.delete(endorserRecord);
		collectionEndorserRecords.remove(endorserRecord);
		
		
	}
	
	
	
	
	//Other business methods--------
	
	
	
	
}
