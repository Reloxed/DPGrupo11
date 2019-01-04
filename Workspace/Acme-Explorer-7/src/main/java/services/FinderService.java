
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	// Managed Repository
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services
	@Autowired
	private ExplorerService		explorerService;


	// Constructors
	public FinderService() {
		super();
	}

	// Simple CRUD methods

	/*
	 * public Finder findByPrincipal() {
	 * Finder result;
	 * Explorer principal;
	 * 
	 * principal = this.explorerService.findByPrincipal();
	 * Assert.notNull(principal);
	 * 
	 * result = principal.getFinder();
	 * 
	 * return result;
	 * 
	 * }
	 */

	public Finder create() {
		Finder result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Finder();
		result.setExplorer(principal);
		result.setResults(new ArrayList<Trip>());

		return result;
	}

	public Collection<Finder> findByPrincipal() {
		Collection<Finder> finders;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		finders = principal.getFinders();

		return finders;
	}

	public Finder findOne(final int finderId) {
		Finder result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Finder result;
		Explorer principal;
		Date currentMoment;
		Collection<Finder> finders;

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() == 0);

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date(System.currentTimeMillis() - 1);

		finder.setMoment(currentMoment);

		if (finder.getPriceMin() != null && finder.getPriceMax() != null)
			Assert.isTrue(finder.getPriceMax() > finder.getPriceMin());

		if (finder.getDateMin() != null && finder.getDateMax() != null)
			Assert.isTrue(finder.getDateMax().after(finder.getDateMin()));

		Assert.isTrue(finder.getExplorer().equals(principal));

		result = this.finderRepository.save(finder);
		Assert.notNull(result);

		finders = new ArrayList<Finder>(principal.getFinders());
		finders.add(result);
		principal.setFinders(finders);

		return result;

	}

	// Other business methods

	public Collection<Trip> search(final Finder finder, final int max) {
		Collection<Trip> result;
		Explorer principal;
		Date dateMin;
		Date dateMax;
		Double priceMin;
		Double priceMax;
		String keyword;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		dateMin = finder.getDateMin();
		if (dateMin == null)
			dateMin = new Date(1262304000000L);

		dateMax = finder.getDateMax();
		if (dateMax == null)
			dateMax = new Date(1924948800000L);

		priceMin = finder.getPriceMin();
		if (priceMin == null)
			priceMin = 0.00;

		priceMax = finder.getPriceMax();
		if (priceMax == null)
			priceMax = 9999.99;

		keyword = finder.getKeyword();
		if (keyword == null)
			keyword = "";

		keyword = "%" + keyword + "%";

		result = this.finderRepository.searchTrips(dateMin, dateMax, priceMin, priceMax, keyword, new PageRequest(0, max));
		Assert.notNull(result);

		finder.setResults(new ArrayList<Trip>(result));

		return result;
	}

	public void deleteOldFinders(final int cacheTime) {
		Explorer principal;
		Collection<Finder> finders;
		Date currentMoment;
		Date limit;
		Collection<Finder> findersToRemove;
		Collection<Finder> updated;

		principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		currentMoment = new Date();
		limit = DateUtils.addHours(currentMoment, -cacheTime);

		finders = principal.getFinders();
		findersToRemove = new ArrayList<Finder>();
		updated = new ArrayList<Finder>(finders);

		for (final Finder finder : finders)
			if (finder.getMoment().before(limit)) {
				findersToRemove.add(finder);
				this.finderRepository.delete(finder);
			}

		updated.removeAll(findersToRemove);
		principal.setFinders(updated);

	}

}
