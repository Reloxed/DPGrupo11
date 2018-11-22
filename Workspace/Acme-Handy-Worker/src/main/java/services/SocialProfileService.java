package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {
	
	//Managed repository
	
	@Autowired
	private SocialProfileRepository socialProfileRepository;
	
	//Supporting services
	
	private ActorService actorService;
	
	//Simple CRUD Methods
	
	public SocialProfile create(){
		SocialProfile result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = new SocialProfile();
		return result;
	}
	
	public Collection<SocialProfile> findAll(){
		Collection<SocialProfile> socialProfiles;
		
		socialProfiles = this.socialProfileRepository.findAll();
		
		return socialProfiles;
	}
	
//	public Collection<SocialProfile> find(){
//		return null;
//	}
	
	public SocialProfile findOne(int socialProfileId){
		SocialProfile result;
		result = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(result);
		return result;
	}
	
	public Collection<SocialProfile> findByPrincipal(){
		Collection<SocialProfile> result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = principal.getSocialProfiles();
		return result;
	}
	
	public SocialProfile save(SocialProfile socialProfile){
		Assert.notNull(socialProfile);
		SocialProfile result;
		result = this.socialProfileRepository.save(socialProfile);
		return result;
	}
	
	public void delete(SocialProfile socialProfile){
		Assert.notNull(socialProfile);
		Assert.notNull(this.socialProfileRepository.findOne(socialProfile.getId()));
		this.socialProfileRepository.delete(socialProfile);
	}
	
	//Other business methods
	
	
	
}
