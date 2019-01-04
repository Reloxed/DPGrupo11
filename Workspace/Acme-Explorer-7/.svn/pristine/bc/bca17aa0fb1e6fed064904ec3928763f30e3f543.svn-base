/*
 * RegistrationTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import repositories.TripRepository;
import utilities.AbstractTest;
import domain.Category;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private TripRepository		tripRepository;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindAll1() {
		super.authenticate("ranger1");
		Collection<Category> categories = null;

		try {
			categories = this.categoryService.findAll();
		} catch (final RuntimeException e) {
		}
		Assert.isNull(categories);
		super.authenticate(null);

	}

	@Test
	public void testFindAll2() {
		super.authenticate("administrator1");
		Collection<Category> categories = null;

		try {
			categories = this.categoryService.findAll();
		} catch (final RuntimeException e) {
		}
		Assert.isTrue(categories.size() == 9);
		super.authenticate(null);

	}

	@Test
	public void testSave1() {
		super.authenticate("administrator1");
		Category category, saved;
		Collection<Category> categories;
		Collection<Category> parentCategories;
		Collection<Category> childCategories;
		Collection<Trip> trips;
		Category parent;

		parentCategories = new ArrayList<Category>();
		childCategories = new ArrayList<Category>();

		parent = this.categoryRepository.findOne(1151); // Category 4
		parentCategories.add(parent);

		trips = new ArrayList<Trip>();

		category = new Category();
		category.setName("Sea");
		category.setChildCategories(childCategories);
		category.setParentCategories(parentCategories);
		category.setTrips(trips);

		saved = this.categoryService.save(category);

		categories = this.categoryRepository.findAll();
		Assert.isTrue(categories.contains(saved));
		super.authenticate(null);

	}

	@Test
	public void testSave2() {
		super.authenticate("administrator1");
		Category category, saved;
		Collection<Category> categories;
		Collection<Trip> trips;

		trips = new ArrayList<Trip>();

		category = this.categoryRepository.findOne(1156); // Category9
		category.setName("CHANGED");
		category.setTrips(trips);

		saved = this.categoryService.save(category);
		categories = this.categoryRepository.findAll();
		Assert.isTrue(categories.contains(saved));
		Assert.isTrue(saved.getTrips().size() == 0);
		Assert.isTrue(this.tripRepository.findOne(1204).getCategory().getName().equals("CHANGED")); // Trip6

		super.authenticate(null);

	}

	@Test
	public void testFindChildOfParent() {
		Collection<Category> result;
		result = this.categoryService.findChildByParent(1149);
		Assert.notEmpty(result);
		Assert.isTrue(result.size() == 3);
	}

}
