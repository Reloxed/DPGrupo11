package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {
	
	//Managed repository
	
	@Autowired
	private SocialProfileRepository spr;
	
	//Supporting services
	
	//Simple CRUD Methods
	
	public SocialProfile create(){
		return new SocialProfile();
	}
	
	public Collection<SocialProfile> findAll(){
		Collection<SocialProfile> socialProfiles;
		
		socialProfiles = this.spr.findAll();
		
		return socialProfiles;
	}
	
//	public Collection<SocialProfile> find(){
//		return null;
//	}
	
	public SocialProfile findOne(int socialProfileId){
		SocialProfile result;
		result = this.spr.findOne(socialProfileId);
		return result;
	}
	
	public SocialProfile save(SocialProfile socialProfile){
		Assert.notNull(socialProfile);
		SocialProfile result;
		result = this.spr.save(socialProfile);
		return result;
	}
	
	public void delete(SocialProfile socialProfile){
		Assert.notNull(socialProfile);
		Assert.notNull(this.spr.findOne(socialProfile.getId()));
		this.spr.delete(socialProfile);
	}
	
	//Other business methods
	
	
	
}
