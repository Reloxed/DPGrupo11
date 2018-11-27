
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test ---------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindAll() {
		super.authenticate("admin1");
		Collection<Category> categories = null;

		categories = this.categoryService.findAll();

		Assert.notNull(categories);

		super.unauthenticate();
	}

	@Test
	public void testCreate() {
		super.authenticate("admin1");
		Category category;

		category = this.categoryService.create();

		Assert.notNull(category);
		Assert.isNull(category.getSpanishName());
		Assert.isNull(category.getEnglishName());
		Assert.isNull(category.getParentCategory());
		Assert.notNull(category.getChildCategories());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("admin1");
		Category category, saved;
		Collection<Category> categories, categoriesUpdated;

		categories = this.categoryService.findAll();
		category = this.categoryService.create();
		category.setSpanishName("hola");
		category.setEnglishName("hello");
		category.setParentCategory(this.categoryService.findRoot());
		Assert.isTrue(!categories.contains(category));

		saved = this.categoryService.save(category);
		categoriesUpdated = this.categoryService.findAll();
		Assert.isTrue(categoriesUpdated.contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testUpdateExistingCategory() {
		super.authenticate("admin1");
		Category category, saved;
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		category = this.categoryService.findOne(2391);
		Assert.isTrue(category.getSpanishName().equals("Reparaciones"));
		category.setSpanishName("Nuevo nombre");
		saved = this.categoryService.save(category);
		Assert.isTrue(categories.contains(saved));
		Assert.isTrue(category.getSpanishName().equals("Nuevo nombre"));

		super.unauthenticate();
	}

	@Test
	public void testRemoveCategory() {
		super.authenticate("admin1");
		Category category;

		category = this.categoryService.findOne(2392);
		Assert.isTrue(this.categoryService.findAll().contains(category));
		this.categoryService.delete(category);
		Assert.isTrue(!this.categoryService.findAll().contains(category));
	}
}
