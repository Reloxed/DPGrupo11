
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
	public void testFindAll1() {
		super.authenticate("administrator1");
		Collection<Category> categories = null;

		categories = this.categoryService.findAll();

		Assert.notNull(categories);

		super.unauthenticate();

	}
}
