package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Administrator;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	// Managed repository-----------

	@Autowired
	private FinderRepository finderRepository;

	// Supporting services ----------

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private SystemConfigurationService systemConfigurationService;


	
	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;




	// Constructor -----------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods-------

	public Finder create() {
		Finder result;

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
		HandyWorker principalHW;
		Administrator principalA;
		Date currentMoment;

		Assert.notNull(finder);

		if (finder.getId() != 0) {
			try {
				principalHW = this.handyWorkerService.findByPrincipal();
				Assert.notNull(principalHW);
			} catch (Throwable oops) {
				principalA = this.administratorService.findByPrincipal();
				Assert.notNull(principalA);
			}

		}

		currentMoment = new Date(System.currentTimeMillis() - 1);

		finder.setSearchMoment(currentMoment);

		if (finder.getStartMoment() != null && finder.getEndMoment() != null)
			Assert.isTrue(finder.getStartMoment().before(finder.getEndMoment()));
		else if (finder.getPriceHigh() != 0.0)
			Assert.isTrue(finder.getPriceHigh() >= finder.getPriceLow());

		result = this.finderRepository.save(finder);

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

	// Other business methods--------

	public Finder findByPrincipal() {
		Finder finder;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		finder = principal.getFinder();

		return finder;
	}

	public Finder createAndInit() {
		Finder finder;

		finder = this.create();
		finder = this.save(finder);

		return finder;
	}


	//expiración de la busqueda cuando termina tiempo caché
	public void deleteExpiredFinders() {
		Collection<Finder> collFind;
		Date maxLivedMoment = new Date();
		int timeChachedFind;
		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		timeChachedFind = this.systemConfigurationService
				.findMySystemConfiguration().getTimeResultsCached();
		maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

		collFind = this.findAll();

		for (final Finder finder : collFind)
			if (finder.getSearchMoment().before(maxLivedMoment)) {
				finder.setFixuptask(new ArrayList<FixUpTask>());
			}
	}

	public Finder resultadosFinder(final Finder finder) {
		final Collection<FixUpTask> setFix = new ArrayList<>();
		final List<FixUpTask> aux = new ArrayList<>();
		final Collection<FixUpTask> collFix = this.fixUpTaskService.findAll();
		final int maxResults = this.systemConfigurationService
				.findMySystemConfiguration().getMaxResults();
		int times = 0;

		if (finder.getPriceHigh() > 0.0 || finder.getPriceLow() > 0.0) {
			Assert.isTrue(finder.getPriceHigh() >= finder.getPriceLow(), "Paco");
			times++;
		}
		if (finder.getWarranty() != null)
			times++;
		if (finder.getCategory() != null)
			times++;
		if (finder.getStartMoment() != null || finder.getEndMoment() != null) {
			Assert.notNull(finder.getStartMoment(), "finder.interval");
			Assert.notNull(finder.getEndMoment(), "finder.interval");
			Assert.isTrue(
					finder.getStartMoment().before(finder.getEndMoment()),
					"finder.moment");
			times++;
		}
		if (finder.getKeyWord() != null)
			times++;

		for (final FixUpTask fixUpTask : collFix) {
			if (finder.getPriceHigh() > 0.0
					&& finder.getPriceLow() <= fixUpTask.getMaxPrice()
					&& finder.getPriceHigh() >= fixUpTask.getMaxPrice()) {
				setFix.add(fixUpTask);
			}
			if (finder.getWarranty() != null
					&& finder.getWarranty().equals(fixUpTask.getWarranty()))
				setFix.add(fixUpTask);
			if (finder.getCategory() != null
					&& finder.getCategory().equals(fixUpTask.getCategory()))
				setFix.add(fixUpTask);
			if (finder.getStartMoment() != null
					&& finder.getEndMoment() != null
					&& finder.getStartMoment().before(
							fixUpTask.getStartMoment())
					&& finder.getEndMoment().after(fixUpTask.getEndMoment()))
				setFix.add(fixUpTask);
			if (finder.getKeyWord() != null
					&& (fixUpTask.getTicker().toLowerCase()
							.contains(finder.getKeyWord().toLowerCase())
							|| fixUpTask
									.getAddress()
									.toLowerCase()
									.contains(finder.getKeyWord().toLowerCase()) || fixUpTask
							.getDescription().toLowerCase()
							.contains(finder.getKeyWord().toLowerCase())))
				setFix.add(fixUpTask);
		}

		for (FixUpTask fixUpTask : collFix) {
			if (Collections.frequency(setFix, fixUpTask) == times) {
				aux.add(fixUpTask);
			}
		}

		final List<FixUpTask> result = new ArrayList<>();
		if (aux.size() > 100) {
			result.addAll(aux.subList(0, maxResults));
			finder.setFixuptask(result);
		} else
			finder.setFixuptask(aux);

		finder.setKeyWord(null);
		finder.setPriceHigh(0.0);
		finder.setPriceLow(0.0);
		finder.setStartMoment(null);
		finder.setEndMoment(null);
		finder.setCategory(null);
		finder.setWarranty(null);
		
		this.save(finder);
		
		return finder;
	}


}
