
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	//Managed repository-----------

	@Autowired
	private FinderRepository			finderRepository;

	//Supporting services ----------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;


	//Constructor -----------------------

	public FinderService() {
		super();
	}

	//Simple CRUD methods-------

	public Finder create() {
		Finder result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Finder();

		result.setFixuptask(new ArrayList<FixUpTask>());

		return result;

	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;

	}

	public Finder save(final Finder finder) {
		Finder result;
		HandyWorker principal;
		Date currentMoment;

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() == 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date(System.currentTimeMillis() - 1);

		finder.setSearchMoment(currentMoment);

		if (finder.getStartMoment() != null && finder.getEndMoment() != null)
			Assert.isTrue(finder.getStartMoment().before(finder.getEndMoment()));
		else if (finder.getPriceHigh() != null && finder.getPriceLow() != null)
			Assert.isTrue(finder.getPriceHigh() >= finder.getPriceLow());

		if (principal.getFinder() != null)
			this.delete(principal.getFinder());

		result = this.finderRepository.save(finder);
		Assert.notNull(result);

		return result;

	}

	public void delete(final Finder finder) {
		HandyWorker principal;

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal.getFinder().equals(finder));

		this.finderRepository.delete(finder);

		principal.setFinder(null);

	}

	//Other business methods--------

	public Finder findByPrincipal() {
		Finder finder;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		finder = principal.getFinder();

		return finder;
	}

	//expiración de la busqueda cuando termina tiempo caché
	public void deleteExpireFinders() {
		HandyWorker principal;
		Collection<Finder> collFind;
		Date maxLivedMoment = new Date();
		int timeChachedFind;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		timeChachedFind = this.systemConfigurationService.findMySystemConfiguration().getTimeResultsCached();
		maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

		collFind = this.findAll();

		for (final Finder finder : collFind)
			if (finder.getSearchMoment().before(maxLivedMoment)) {
				finder.setFixuptask(null);
				this.save(finder);
			}
	}

	
	public Finder resultadosFinder (Finder finder) {
		Set<FixUpTask> setFix = new HashSet<>();
		Collection <FixUpTask> collFix = this.fixUpTaskService.findAll();
		int maxResults = this.systemConfigurationService.findMySystemConfiguration().getMaxResults();
		
		for(FixUpTask fixUpTask : collFix) {
			if(finder.getPriceLow() != null && finder.getPriceHigh() != null 
				&& finder.getPriceLow()<= fixUpTask.getMaxPrice() && finder.getPriceHigh()>= fixUpTask.getMaxPrice()){
				setFix.add(fixUpTask);
			if (finder.getWarranty() != null && finder.getWarranty().equals(fixUpTask.getWarranty()))
				setFix.add(fixUpTask);
			if (finder.getCategory() != null && finder.getCategory().equals(fixUpTask.getCategory()))
				setFix.add(fixUpTask);
			if (finder.getStartMoment() != null && finder.getEndMoment() != null && finder.getStartMoment().before(fixUpTask.getStartMoment()) && finder.getEndMoment().after(fixUpTask.getEndMoment()))
				setFix.add(fixUpTask);
			if (finder.getKeyWord() != null
				&& (fixUpTask.getTicker().toLowerCase().contains(finder.getKeyWord().toLowerCase()) || fixUpTask.getAddress().toLowerCase().contains(finder.getKeyWord().toLowerCase()) || fixUpTask.getDescription().toLowerCase()
					.contains(finder.getKeyWord().toLowerCase())))
				setFix.add(fixUpTask);
		}
		
		List<FixUpTask> aux = new ArrayList<>();
		aux.addAll(setFix);
		List<FixUpTask> result = new ArrayList<>();
		if(aux.size()>100){
			result.addAll(aux.subList(0, maxResults));
			finder.setFixuptask(result);
		} else {
			finder.setFixuptask(setFix);
		}
				
		return finder;
	}
}
