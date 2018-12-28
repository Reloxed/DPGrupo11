
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
		result.setSearchMoment(new Date(System.currentTimeMillis() - 1));

		return result;

	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();

		return result;

	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;

	}

	public Finder save(final Finder finderId) {
		Finder result;
		HandyWorker principal;
		Date currentMoment;

		Assert.notNull(finderId);
		Assert.isTrue(finderId.getId() == 0);

		principal = this.handyWorkerService.findByPrincipal();

		Assert.notNull(principal);

		currentMoment = new Date(System.currentTimeMillis() - 1);

		finderId.setSearchMoment(currentMoment);

		if (finderId.getStartMoment() != null && finderId.getEndMoment() != null)
			Assert.isTrue(finderId.getStartMoment().before(finderId.getEndMoment()));
		else if (finderId.getPriceHigh() != null && finderId.getPriceLow() != null)
			Assert.isTrue(finderId.getPriceHigh() >= finderId.getPriceLow());

		result = this.finderRepository.save(finderId);
		Assert.notNull(result);

		return result;

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
