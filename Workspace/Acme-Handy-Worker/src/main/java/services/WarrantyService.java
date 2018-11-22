package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Managed repository
	
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public Warranty create(){
		return new Warranty();
	}
	
	public Collection<Warranty> findAll(){
		Collection<Warranty> warranties;
		
		warranties = this.warrantyRepository.findAll();
		
		return warranties;
	}
	
//	public Collection<Warranty> find(){
//		
//	}
	
	public Warranty findOne(int warrantyId){
		Warranty result;
		
		result = this.warrantyRepository.findOne(warrantyId);
		
		return result;
	}
	
	public Warranty save(Warranty w){
		Assert.notNull(w);
		
		Warranty result;
		result = this.warrantyRepository.save(w);
		
		return result;
	}
	
	public void delete(Warranty w){
		Assert.notNull(w);
		Assert.notNull(this.warrantyRepository.findOne(w.getId()));
		this.warrantyRepository.delete(w);
	}
	
	//Other business methods
	
}
