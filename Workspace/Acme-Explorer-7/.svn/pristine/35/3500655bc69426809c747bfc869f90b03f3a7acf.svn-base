
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StoryService;
import domain.Story;

@Controller
@RequestMapping("/story")
public class StoryController extends AbstractController {

	// Services

	@Autowired
	private StoryService	storyService;


	// Constructors

	public StoryController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int storyId) {
		final ModelAndView result;

		final Story story;

		story = this.storyService.findOne(storyId);

		result = new ModelAndView("story/display");
		result.addObject("story", story);

		return result;

	}
}
