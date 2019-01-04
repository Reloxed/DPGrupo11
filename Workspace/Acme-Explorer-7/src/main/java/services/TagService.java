
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Administrator;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TagService {

	// Managed Repository
	@Autowired
	private TagRepository			tagRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TripService				tripService;


	// Constructors

	public TagService() {
		super();
	}

	// Simple CRUD methods

	public Tag create() {
		Tag result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Tag();
		Assert.notNull(result);

		return result;
	}

	public Tag findOne(final int tagId) {

		Tag result;

		result = this.tagRepository.findOne(tagId);

		Assert.notNull(result);

		return result;
	}

	// An actor who is authenticated as an administrator must be able to list the tags
	public Collection<Tag> findAll() {
		//		Administrator principal;
		Collection<Tag> result;

		//		principal = this.administratorService.findByPrincipal();

		//		Assert.notNull(principal); // Checks if the principal is an administrator

		result = this.tagRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public Collection<Tag> findUsedTags() {
		Collection<Tag> result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.tagRepository.findUsedTags();
		Assert.notNull(result);

		return result;
	}

	// An actor who is authenticated as an administrator must be able to create a new tag and to update a tag as long as it's not
	// been used to tag any trip
	public Tag save(final Tag tag) {
		Tag result;
		Administrator principal;
		Collection<Trip> taggedTrips;

		Assert.notNull(tag);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an administrator

		taggedTrips = this.tripService.findByTag(tag);

		Assert.notNull(taggedTrips);
		Assert.isTrue(taggedTrips.isEmpty());

		result = this.tagRepository.save(tag);
		Assert.notNull(result);

		return result;

	}

	// An actor who is authenticated as an administrator must be able to delete a tag at any time
	public void delete(final Tag tag) {
		Administrator principal;
		Collection<Trip> trips;
		Collection<Tag> tags;
		Collection<Tag> updated;

		Assert.notNull(tag);
		Assert.isTrue(tag.getId() != 0);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal); // Checks if the principal is an administrator

		trips = this.tripService.findByTag(tag);

		for (final Trip trip : trips) {
			tags = trip.getTags();
			updated = new ArrayList<Tag>(tags);
			updated.remove(tag);
			trip.setTags(updated);
		}

		this.tagRepository.delete(tag);
	}

	// Other business methods

}
