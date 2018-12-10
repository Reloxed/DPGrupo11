
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


	// Constructors ------------------------------------

	public CategoryService() {
		super();
	}

	// CRUD methods -----------------------------------

	public Category create() {
		Category result;
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = new Category();
		result.setChildCategories(new ArrayList<Category>());

		return result;
	}

	public Category save(final Category c) {
		final Administrator admin;
		Category result;
		final Category parent, root;

		admin = this.administratorService.findByPrincipal();
		root = this.findRoot();

		Assert.isTrue(c.getId() != root.getId());
		Assert.notNull(admin);
		Assert.notNull(c.getEnglishName());
		Assert.notNull(c.getSpanishName());
		Assert.notNull(c.getParentCategory());

		result = this.categoryRepository.saveAndFlush(c);

		parent = result.getParentCategory();
		// Si aún no está guardado en la bbdd, actualizamos las categorías hija de su padre
		if (c.getId() == 0)
			this.newChild(parent, result);
		else if (!c.getParentCategory().equals(parent)) {
			this.deleteChild(c.getParentCategory(), c);
			this.newChild(parent, c);
		}
		return result;
	}

	public void delete(final Category c) {
		Administrator admin;
		Category parent, root, aux;
		Collection<Category> childCategories;

		Assert.notNull(c);
		Assert.isTrue(c.getId() != 0);

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		root = this.findRoot();
		Assert.isTrue(c.getId() != root.getId()); // Comprobamos que no vamos a borrar la categoría raiz

		childCategories = c.getChildCategories();
		aux = null;

		if (!childCategories.isEmpty())
			for (final Category cat : childCategories)
				cat.setParentCategory(aux);

		parent = c.getParentCategory();
		this.deleteChild(parent, c);

		this.categoryRepository.delete(c);
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

	// Other business methods

	public Collection<Category> findChildCategories(final int categoryId) {
		Collection<Category> result;

		result = this.categoryRepository.findChildCategories(categoryId);

		return result;
	}

	public Category findRoot() {
		Category result;

		result = this.categoryRepository.findRoot();

		return result;
	}

	private void newChild(final Category c, final Category child) {
		Collection<Category> childCategories;

		childCategories = c.getChildCategories();
		childCategories.add(child);
		c.setChildCategories(childCategories);
	}

	private void deleteChild(final Category c, final Category child) {
		Collection<Category> childCategories;

		childCategories = c.getChildCategories();
		childCategories.remove(child);
		c.setChildCategories(childCategories);
	}
}
