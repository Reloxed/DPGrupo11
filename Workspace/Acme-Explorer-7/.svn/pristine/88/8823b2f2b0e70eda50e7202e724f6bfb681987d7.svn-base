
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.CustomisationService;
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	// Services

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors

	public EndorserRecordRangerController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
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

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "er.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:/curriculum/ranger/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "er.commit.error");
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
		Curriculum curriculum;
		String countryCode;
		boolean permission = false;

		curriculum = this.curriculumService.findCurriculumByPrincipal();
		countryCode = this.customisationService.find().getCountryCode();

		if (curriculum == null)
			permission = false;
		else if (er.getId() == 0)
			permission = true;
		else
			for (final EndorserRecord endRec : curriculum.getEndorserRecord())
				if (er.getId() == endRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", er);
		result.addObject("permission", permission);
		result.addObject("curriculum", curriculum);
		result.addObject("countryCode", countryCode);

		result.addObject("message", messageCode);

		return result;

	}

}
