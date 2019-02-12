package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TonemaRepository;
import domain.Customer;
import domain.Tonema;

@Service
@Transactional
public class TonemaService {
	
	// Managed repository ------------------------------------

	@Autowired
	private TonemaRepository tonemaRepository;

	// Supporting services -----------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UtilityService utilityService;

	// Simple CRUD methods -----------------------------------

	public Tonema create() {
		Customer principal;
		Tonema result;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Tonema();
		result.setTicker(this.utilityService.generateTickerForTonema());

		return result;
	}

	public Collection<Tonema> findAll() {
		Collection<Tonema> result;
		
		result = this.tonemaRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}

	public Tonema findOne(final int tonemaId) {
		Tonema result;
		
		result = this.tonemaRepository.findOne(tonemaId);
		Assert.notNull(result);
		
		return result;
	}

	public Tonema save(final Tonema tonema) {
		Customer principal;
		Tonema result;
		
		Assert.notNull(tonema);
		Assert.notNull(tonema.getBody());
		Assert.notNull(tonema.getFixUpTask());
		
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getFixUpTasks().contains(tonema.getFixUpTask()));

		if(tonema.getId()!=0){
			Assert.isTrue(!this.tonemaRepository.findOne(tonema.getId()).getIsFinal());
		}
		
		if (tonema.getIsFinal()) {
			tonema.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		} 
		
		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(tonema.getBody());

		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam) {
			principal.setIsSuspicious(true);
		}

		result = this.tonemaRepository.save(tonema);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Tonema tonema) {
		Customer principal;
		
		Assert.notNull(tonema);
		Assert.isTrue(tonema.getId() != 0);
		Assert.isTrue(!tonema.getIsFinal());

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		Assert.isTrue(principal.getFixUpTasks().contains(tonema.getFixUpTask()));

		this.tonemaRepository.delete(tonema.getId());

	}


	// Other business methods -------------------------------
	
	public Collection<Tonema> findTonemaByPrincipal() {
		Collection<Tonema> collTonemas;
		Customer principal;
		
		principal = this.customerService.findByPrincipal();
		collTonemas = this.tonemaRepository.findTonemaByPrincial(principal.getId());
		
		return collTonemas;
	}
	
	public Collection<Tonema> findTonemaFinalByFix(int fixUpTaskId) {
		Collection<Tonema> collTonemas;

		collTonemas = this.tonemaRepository.findTonemaFinalByFix(fixUpTaskId);
		
		return collTonemas;
	}

	public Double[] avgstdOfTonema() {
		Double[] result;

		result = this.tonemaRepository.avgstdOfTonema();
		
		return result;
	}
	
	public Double ratioOfPublishedTonema() {
		Double result;

		result = this.tonemaRepository.ratioOfPublishedTonema();
		
		return result;
	}
	
	public Double ratioOfUnpublishedTonema() {
		Double result;

		result = this.tonemaRepository.ratioOfUnpublishedTonema();
		
		return result;
	}
	
//	public Double[] tonemaStatistics() {
//		List<Double> result = new ArrayList<>();
//		
//		result.addAll(this.avgstdOfTonema());
//		result.add(this.ratioOfPublishedTonema());
//		result.add(this.ratioOfUnpublishedTonema());
//		
//		return (Double[]) result.toArray();
//	}
	
}
