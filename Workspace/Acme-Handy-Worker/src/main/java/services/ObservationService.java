package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ObservationRepository;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.Observation;

@Service
@Transactional
public class ObservationService {

	//Repository -----------------------------------------------
	
	@Autowired
	private ObservationRepository observationRepository;
	
	//Services -----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UtilityService utilityService;
	
	//Constructor --------------------------------------------------
	
	public ObservationService(){
		super();
	}
	
	//CRUD Methods -------------------------------------------
	
	public Observation create(){
		Observation result;
		Date publishedMoment;
		Actor principal;
		String ticker;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Customer);
		
		boolean isFinal = false;
		publishedMoment = new Date(System.currentTimeMillis()-1);
		ticker = this.utilityService.generateTicker();
		
		result = new Observation();
		
		result.setPublishedMoment(publishedMoment);
		result.setIsFinal(isFinal);
		result.setTicker(ticker);
		
		
		return result;
		
	}
	
	public Observation save(final Observation observation, boolean finalMode){
		
		Observation result,saved;
		Actor principal;
		String ticker;
		Date publishedMoment;
		String body;
		String picture;
		FixUpTask fixUpTask;
		boolean isFinal;
		
//		if(finalMode == true){
//			Assert.isTrue(observation.getId() != 0);
//		}else{
//			Assert.isTrue(observation.getId() == 0);
//		}
		
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(principal instanceof Customer);
		
		ticker = observation.getTicker();
		Assert.notNull(ticker);
		
		publishedMoment = new Date(System.currentTimeMillis()-1);
		Assert.notNull(publishedMoment);
		
		body = observation.getBody();
		Assert.notNull(body);
		
		picture = observation.getPicture();
		
		fixUpTask = observation.getFixUpTask();
		Assert.notNull(fixUpTask);
		
		isFinal = finalMode;
		
		observation.setPublishedMoment(publishedMoment);
		observation.setIsFinal(isFinal);
		
		
		Assert.notNull(observation);
		
		saved = this.observationRepository.save(observation);
		
		fixUpTask.getObservations().add(saved);
		
		Assert.notNull(saved);
		Assert.isTrue(saved.getId()!= 0);
		
		return saved;
	}
	
	public void delete(final Observation observation){
		
		Assert.isTrue(observation.getId() != 0);
		
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Customer);
		
		this.observationRepository.delete(observation);
		
		observation.getFixUpTask().getObservations().remove(observation);
		
	}
	
	public Observation findOne(final int observationId){
		Observation result;
		
		result = this.observationRepository.findOne(observationId);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Observation> findAll(){
		Collection<Observation> result;
		
		result = this.observationRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	//Other business methods ---------------------------------------------
	
	public Double[] operationsObservation(){
		Double[]result;
		
		result = this.observationRepository.operationsObservations();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double ratioObservationsFinalMode(){
		Double result;
		
		result = this.observationRepository.ratioObservationsFinalMode();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double ratioObservationsDraftMode(){
		Double result;
		
		result = this.observationRepository.ratioObservationsDraftMode();
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Observation> getFinalObservations(){
		Collection<Observation> result;
		
		result = this.observationRepository.findFinalObservations();
		Assert.notNull(result);
		
		return result;
	}
 }
