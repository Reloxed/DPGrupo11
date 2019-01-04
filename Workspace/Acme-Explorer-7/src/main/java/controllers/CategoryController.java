
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.TripService;
import domain.Category;
import domain.Trip;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	// Services

	@Autowired
	CategoryService	categoryService;

	@Autowired
	TripService		tripService;


	// Constructors

	public CategoryController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Category currentCategory;

		currentCategory = this.categoryService.findRootCategory();

		result = new ModelAndView("category/tree");
		result.addObject("currentCategory", currentCategory);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"categoryId"
	})
	public ModelAndView list(@RequestParam final int categoryId) {
		final ModelAndView result;
		Category currentCategory;
		final Collection<Trip> tripsInCategory;

		currentCategory = this.categoryService.findOne(categoryId);
		tripsInCategory = this.tripService.findByCategory(currentCategory.getId());

		result = new ModelAndView("category/tree");
		result.addObject("currentCategory", currentCategory);
		result.addObject("tripsInCategory", tripsInCategory);

		return result;

	}

}
