package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	//Managed repository
	
	@Autowired
	private TutorialRepository tr;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Tutorial create(){
		return new Tutorial();
	}
	
	public Collection<Tutorial> findAll(){
		Collection<Tutorial> tutorials;
		
		tutorials = this.tr.findAll();
		Assert.notNull(tutorials);
		
		return tutorials;
	}
	
//	public Collection<Tutorial> find(){
//		
//	}
	
	public Tutorial findOne(int tutorialId){
		Tutorial result;
		
		Assert.isTrue(tutorialId != 0);
		result = this.tr.findOne(tutorialId);
		
		return result;
	}
	
	public Tutorial save(Tutorial t){
		Assert.notNull(t);
		
		Tutorial result;
		result = this.tr.save(t);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		
		return result;
	}
	
	public void delete(Tutorial t){
		Assert.notNull(t);
		Assert.notNull(this.tr.findOne(t.getId()));
		this.tr.delete(t);
	}
	
	//Other business methods
	
}
