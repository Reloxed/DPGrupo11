
package controllers.administator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.AdministratorService;
import services.CategoryService;
import domain.Administrator;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController{

	// Services
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AdministratorService administratorService;

	// Constructor

	public CategoryAdministratorController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);
		return result;
	}

	// listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Locale locale) {
		ModelAndView result;
		Collection<Category> categories;
		Administrator principal;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";
		
		language = locale.getLanguage();
		principal = this.administratorService.findByPrincipal();

		categories = this.categoryService.findAll();
		
		result = new ModelAndView("category/list");
		
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		result.addObject("categories", categories);
		result.addObject("principal", principal);
		result.addObject("requestUri", "category/administrator/list.do");
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int categoryId,
			final Locale locale) {
		final ModelAndView result;
		Category category;
		String language;
		String español;
		String english;
		español = "es";
		english = "en";

		category = this.categoryService.findOne(categoryId);

		language = locale.getLanguage();
		result = new ModelAndView("category/display");

		result.addObject("category", category);
		result.addObject("language", language);
		result.addObject("español", español);
		result.addObject("english", english);
		result.addObject("requestUri", "category/administrator/display.do");

		return result;

	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		final ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);

		Category parentCategory = category.getParentCategory();
		Assert.notNull(parentCategory);

		result = this.createEditModelAndView(category);
		result.addObject("category", category);
		result.addObject("parentCategory", parentCategory);
		return result;

	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category,
						"category.commit.error");
			}

		return result;
	}

	// Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Category category) {
		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,
					"category.commit.error");
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category,
			final String message) {
		ModelAndView result;
		Administrator principal;

		Collection<Category> parents = new ArrayList<Category>();

		parents = this.categoryService.findAll();

		/*
		 * for(Category c:parents){ allParents.add(c.getParentCategory()); }
		 */
		principal = this.administratorService.findByPrincipal();

		result = new ModelAndView("category/edit");
		result.addObject("parents", parents);
		result.addObject("category", category);
		result.addObject("principal", principal);
		result.addObject("message", message);
		return result;
	}

}