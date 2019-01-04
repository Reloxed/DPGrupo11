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

import services.EducationRecordService;

import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordHandyWorkerController extends AbstractController  {

	

	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors

	public EducationRecordHandyWorkerController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final EducationRecord er;

		er = this.educationRecordService.create();

		result = this.createEditModelAndView(er);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord er;

		er = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(er);
		result = this.createEditModelAndView(er);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		final ModelAndView result;


		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);


		result.addObject("message", messageCode);

		return result;

	}

	
}
