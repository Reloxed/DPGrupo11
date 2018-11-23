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
	private TutorialRepository tutorialRepository;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Tutorial create(){
		return new Tutorial();
	}
	
	public Collection<Tutorial> findAll(){
		Collection<Tutorial> tutorials;
		
		tutorials = this.tutorialRepository.findAll();
		
		return tutorials;
	}
	
//	public Collection<Tutorial> find(){
//		
//	}
	
	public Tutorial findOne(int tutorialId){
		Tutorial result;
		
		result = this.tutorialRepository.findOne(tutorialId);
		
		return result;
	}
	
	public Tutorial save(Tutorial t){
		Assert.notNull(t);
		
		Tutorial result;
		result = this.tutorialRepository.save(t);
		
		return result;
	}
	
	public void delete(Tutorial t){
		Assert.notNull(t);
		Assert.notNull(this.tutorialRepository.findOne(t.getId()));
		this.tutorialRepository.delete(t);
	}
	
	//Other business methods
	
}
