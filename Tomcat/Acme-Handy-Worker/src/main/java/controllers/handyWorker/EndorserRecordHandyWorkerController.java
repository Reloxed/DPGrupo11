
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	// Services

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors

	public EndorserRecordHandyWorkerController() {
		super();
	}

	// Listing

	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final EndorserRecord er;

		er = this.endorserRecordService.create();

		result = this.createEditModelAndView(er);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord er;

		er = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(er);
		result = this.createEditModelAndView(er);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord ed, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ed);
		else
			try {
				this.endorserRecordService.save(ed);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ed, "enr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord ed, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorserRecordService.delete(ed);
			result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ed, "enr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final EndorserRecord er) {
		ModelAndView result;

		result = this.createEditModelAndView(er, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord er, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", er);

		result.addObject("message", messageCode);

		return result;

	}

}
