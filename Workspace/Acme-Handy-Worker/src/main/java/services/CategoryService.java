
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository ------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services -----------------------
	@Autowired
	private AdministratorService	administratorService;


	// CRUD methods -----------------------------------

	public Category create() {
		Category result;
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = new Category();

		return result;
	}

	public Category save(final Category c) {
		final Category result;

		Assert.notNull(c.getEnglishName());
		Assert.notNull(c.getSpanishName());
		Assert.notNull(c.getParentCategory());

		result = this.categoryRepository.save(c);

		return result;
	}

	public void delete(final Category c) {
		/*
		 * Si borro una categoría, debo eliminarla de las categorías hijas de su padre y además,
		 * si es padre, debo borrar sus categorias hijas
		 */
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();

		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);

		return result;
	}
}
