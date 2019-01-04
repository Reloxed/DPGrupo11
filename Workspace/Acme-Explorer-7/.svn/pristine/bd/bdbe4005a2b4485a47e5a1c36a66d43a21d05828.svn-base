
package services;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Manager;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	// Managed Repository
	@Autowired
	private StageRepository		stageRepository;

	// Supporting services
	@Autowired
	private ManagerService	   managerService;


	// Constructors

	public StageService() {
		super();
	}

	// Simple CRUD methods

	public Stage create() {
		Stage result;
		Manager principal;
		
		
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Stage();
		Assert.notNull(result);

		return result;
	}


	public Collection<Stage> findAll() {
		Collection<Stage> result;
		Manager principal;
		Collection<Trip> trips;
		
		principal = this.managerService.findByPrincipal();
		
		Assert.notNull(principal); 
		
		trips = principal.getTrips();
		
		result = new ArrayList<Stage>();
		for (Trip trip : trips) {
			result.addAll(trip.getStage());
		}

		
		return result;
	}

	public Stage findOne(int stageId) {
		Stage result;
		Manager principal;
		
		principal = this.managerService.findByPrincipal();
		
		Assert.notNull(principal); 
		
		result = this.stageRepository.findOne(stageId);
		
		return result;
	}
	
	
	
	public Stage save(final Stage stage) {
		Stage result;
		Manager principal;
		
		Assert.notNull(stage);
		
		principal = this.managerService.findByPrincipal();
		
		Assert.notNull(principal); 
		
		result = this.stageRepository.save(stage);
		
		return result;
	}
	

	public void delete(final Stage stage) {
		Manager principal;
		
		Assert.notNull(stage);
		Assert.isTrue(stage.getId() != 0);
		
		principal = this.managerService.findByPrincipal();
		
		Assert.notNull(principal); 
		
		this.stageRepository.delete(stage);

	}

	// Other business methods

	

	
	
	
	
	
	
	
	
}
