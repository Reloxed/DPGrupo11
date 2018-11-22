package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Finder;
import domain.FixUpTask;


import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	//Managed repository-----------
	
	@Autowired
	private FinderRepository finderRepository;
	
	
	//Supporting services ----------
	private FixUpTaskService fixUptaskService;
	
	
	
	
	//Simple CRUD methods-------
	
	public Finder create(){
		Finder result;
		result=new Finder();
		result.setFixuptask(new ArrayList<FixUpTask>());
		
		return result;
		
	}
	public Collection<Finder> findAll(){
		return null;
		
	} 
	
	public Finder findOne(int finderId){
		Finder result;
		
		result=this.finderRepository.findOne(finderId);
		
		Assert.notNull(result);
		
		return result;
		
	}
	
	public Finder save(Finder finderId){
		
		
		
		return null;
		
	}
	
	public void  delete(Finder finderId){
		return;
		
	}
	
	
	
	
	//Other business methods--------
	
	
}
