
package services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import repositories.PersonalRecordRepository;


import domain.PersonalRecord;
import domain.Ranger;



@Service
@Transactional
public class PersonalRecordService {

	// Managed Repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services
	@Autowired
	private RangerService rangerService;
	

	
	// Constructors

	public PersonalRecordService() {
		super();
	}

	
	// Simple CRUD methods

	
	public PersonalRecord findByPrincipal() {
		PersonalRecord result;
		Ranger principal;
		
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		
		result = principal.getCV().getPersonalRecord();
	
		Assert.notNull(result);
		
		return result;
	}
	
	
	public PersonalRecord create() {
		PersonalRecord result;
		Ranger principal;
		
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = new PersonalRecord();
		Assert.notNull(result);

		return result;
	}
	
	
	public PersonalRecord save(PersonalRecord personalRecord) {
		PersonalRecord result;
		Ranger principal;
		
		
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		
		
		result = this.personalRecordRepository.save(personalRecord);
		Assert.notNull(result);
		
		return result;
	
	}
	
	
	public void delete(PersonalRecord personalRecord) {
		Ranger principal;
		
		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);
		
		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		

	
		this.personalRecordRepository.delete(personalRecord);
	
	}
	
	
	

	// Other business methods
	
 
	
}
