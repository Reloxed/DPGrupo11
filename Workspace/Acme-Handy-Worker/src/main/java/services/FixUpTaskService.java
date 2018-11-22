package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import domain.FixUpTask;

import repositories.FixUpTaskRepository;



@Service
@Transactional
public class FixUpTaskService {

	//Managed repository-----------
	
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;
	
	
	//Supporting services ----------
	
	
	//Constructor ----------------------------------------------------
	public FixUpTaskService() {
		super();
	}
	
	
	//Simple CRUD methods-------
	//teniendo en cuenta las reglas de negocio
	
	public FixUpTask create(){
		return null;
		
	}
	public Collection<FixUpTask> findAll(){
		return null;
		
	} 
	
	public FixUpTask findOne(int fixUpTaskId){
		return null;
		
	}
	
	public FixUpTask save(FixUpTask fixUpTask){
		return null;
		
	}
	
	public void  delete(FixUpTask fixUpTask){
		return;
		
	}
	
	
	
	
	
	//Other business methods--------
	//resitricciones de datos y restricciones de acceso
	
	
	
}
