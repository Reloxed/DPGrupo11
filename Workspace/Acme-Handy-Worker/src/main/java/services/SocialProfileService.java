package services;

import java.util.Collection;
import java.util.Collections;

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
	
	@Autowired
	private ActorService actorService;
	
	//Simple CRUD Methods
	
	public SocialProfile create(){
		SocialProfile result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		result = new SocialProfile();
		Assert.notNull(result);
		return result;
	}
	
	public Collection<SocialProfile> findAll(){
		Collection<SocialProfile> socialProfiles;
		
		socialProfiles = this.socialProfileRepository.findAll();
		
		return socialProfiles;
	}
	
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
		Actor principal;
		SocialProfile result;
		Collection<SocialProfile> socialProfilesUpdated;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(socialProfile);
		
		if(socialProfile.getId() != 0){
			Assert.isTrue(principal.getSocialProfiles().contains(socialProfile));
		} else {
			socialProfilesUpdated = Collections.<SocialProfile> emptyList();
			socialProfilesUpdated.addAll(principal.getSocialProfiles());
			socialProfilesUpdated.add(socialProfile);
			principal.setSocialProfiles(socialProfilesUpdated);
		}
		
		result = this.socialProfileRepository.save(socialProfile);
		Assert.notNull(result);
		
		return result;
	}
	
	public void delete(SocialProfile socialProfile){
		Actor principal;
		Collection<SocialProfile> socialProfilesUpdated;
		
		Assert.notNull(socialProfile);
		Assert.isTrue(socialProfile.getId() != 0);
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		socialProfilesUpdated = Collections.<SocialProfile> emptyList();
		socialProfilesUpdated.addAll(principal.getSocialProfiles());
		socialProfilesUpdated.remove(socialProfile);
		principal.setSocialProfiles(socialProfilesUpdated);
		
		this.socialProfileRepository.delete(socialProfile);
		
	}
	
	//Other business methods
	
	
	
}
