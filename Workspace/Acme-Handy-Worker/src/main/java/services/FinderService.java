
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
	private FinderRepository	finderRepository;

	//Supporting services ----------

	@Autowired
	private HandyWorkerService	handyWorkerService;


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
		
		if(principal.getFinder() != null) {
			this.delete(principal.getFinder());
		}

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
	
	//expiración de la busqueda cuanto termina tiempo caché
	public void deleteExpireFinders(final int timeInCache) {
		Finder finder;
		Collection<Finder> finders;
		Collection<Finder> findersDelete;
		Collection<Finder> currentFinders;

		HandyWorker principal;
		Date currentMoment;
		Date expireCache;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date();
		expireCache = DateUtils.addHours(currentMoment, timeInCache);

		finders = new ArrayList<Finder>();
		finder = principal.getFinder();
		finders.add(finder);

		findersDelete = new ArrayList<Finder>();
		currentFinders = new ArrayList<Finder>(finders);

		for (final Finder f : finders)
			if (f.getSearchMoment().before(expireCache)) {
				findersDelete.add(f);
				this.finderRepository.delete(f);
			}

		currentFinders.removeAll(findersDelete);

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

}
