package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	//Managed repository
	
	@Autowired
	private SystemConfigurationRepository scr;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public SystemConfiguration create(){
		return new SystemConfiguration();
	}
	
	public Collection<SystemConfiguration> findAll(){
		Collection<SystemConfiguration> systemConfigurations;
		
		systemConfigurations = this.scr.findAll();
		Assert.notNull(systemConfigurations);
		
		return systemConfigurations;
	}
	
//	public Collection<SystemConfiguration> find(){
//		
//	}
	
	public SystemConfiguration findOne(int systemConfigurationId){
		SystemConfiguration result;
		
		Assert.isTrue(systemConfigurationId != 0);
		result = this.scr.findOne(systemConfigurationId);
		
		return result;
	}
	
	public SystemConfiguration save(SystemConfiguration sc){
		Assert.notNull(sc);
		
		SystemConfiguration result;
		result = this.scr.save(sc);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		
		return result;
	}
	
	public void delete(SystemConfiguration sc){
		Assert.notNull(sc);
		Assert.notNull(this.scr.findOne(sc.getId()));
		this.scr.delete(sc);
	}

	//Other business methods

}
