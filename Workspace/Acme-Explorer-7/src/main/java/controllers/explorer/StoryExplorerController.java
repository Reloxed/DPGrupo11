
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.StoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	// Services

	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors

	public StoryExplorerController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Story> stories;

		stories = this.storyService.findByPrincipal();
		result = new ModelAndView("story/list");
		result.addObject("stories", stories);

		return result;
	}

	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Story story;
		Trip trip;

		story = this.storyService.create();

		trip = this.tripService.findOne(tripId);
		story.setTrip(trip);
		result = this.createEditModelAndView(story);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;

		story = this.storyService.findOne(storyId);
		Assert.notNull(story);

		result = this.createEditModelAndView(story);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Story story, final BindingResult binding) {
		ModelAndView result;

		try {
			this.storyService.delete(story);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(story, "story.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Story story) {
		ModelAndView result;

		result = this.createEditModelAndView(story, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String message) {
		final ModelAndView result;
		boolean permission = false;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();

		if (story.getId() == 0)
			for (final Trip t : this.tripService.findAllPassedTripsAcceptedApplicationPrincipal()) {
				if (story.getTrip().getId() == t.getId()) {
					permission = true;
					break;
				}
			}

		else
			for (final Story sto : principal.getStories())
				if (sto.getId() == story.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("story/edit");
		result.addObject("story", story);
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
