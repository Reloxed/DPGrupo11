
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;
import domain.SystemConfiguration;

@Service
@Transactional
public class CategoryService {

	// Managed repository ------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services -----------------------
	
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

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
		result.setName(new HashMap<String,String>());

		return result;
	}

	public Category save(final Category category) {
		final Administrator admin;
		Category result;
		final Category parent, root;
		SystemConfiguration systemConf;
		Set<String> idiomasCategory;

		admin = this.administratorService.findByPrincipal();
		root = this.findRoot();

		Assert.isTrue(category.getId() != root.getId());
		Assert.notNull(admin);
		
		Assert.notNull(category.getName());
		systemConf = systemConfigurationService.findMySystemConfiguration();
		Set<String> idiomasSystemConf = new HashSet<String>(systemConf.getWelcomeMessage().keySet());	
		idiomasCategory = category.getName().keySet();
		Assert.isTrue(idiomasSystemConf.containsAll(idiomasCategory));
		
		Assert.notNull(category.getParentCategory());
		
		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		Collection<String> categoryNames = category.getName().values();
		List<String> names = new ArrayList<>();
		for (String catName : categoryNames) { 
			String[] n = catName.split("(¿¡,.-_/!?) ");
			for(String name : n){
				names.add(name);
			}
		}
		for (final String word : spamWords) {
			for (final String titleWord : names)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				admin.setIsSuspicious(true);
				break;
			}
		}
		
		result = this.categoryRepository.saveAndFlush(category);

		parent = result.getParentCategory();
		// Si aún no está guardado en la bbdd, actualizamos las categorías hija de su padre
		if (category.getId() == 0)
			this.newChild(parent, result);
		else if (!category.getParentCategory().equals(parent)) {
			this.deleteChild(category.getParentCategory(), category);
			this.newChild(parent, category);
		}
		return result;
	}

	public void delete(final Category category) {
		Administrator admin;
		Category parent, root, aux;
		Collection<Category> childCategories;

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		root = this.findRoot();
		Assert.isTrue(category.getId() != root.getId()); // Comprobamos que no vamos a borrar la categoría raiz

		childCategories = category.getChildCategories();
		aux = null;

		if (!childCategories.isEmpty())
			for (final Category cat : childCategories)
				cat.setParentCategory(aux);

		parent = category.getParentCategory();
		this.deleteChild(parent, category);

		this.categoryRepository.delete(category);
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

	private void newChild(final Category category, final Category child) {
		Collection<Category> childCategories;

		childCategories = category.getChildCategories();
		childCategories.add(child);
		category.setChildCategories(childCategories);
	}

	private void deleteChild(final Category category, final Category child) {
		Collection<Category> childCategories;

		childCategories = category.getChildCategories();
		childCategories.remove(child);
		category.setChildCategories(childCategories);
	}
}
