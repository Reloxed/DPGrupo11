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

import controllers.AbstractController;


import domain.MiscellaneousRecord;


import services.MiscellaneousRecordService;

@Controller
@RequestMapping("/miscellaneousRecord/handyWorker")
public class MiscellaneousRecordHandyWorkerController extends AbstractController{


	// Services


	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors

	public MiscellaneousRecordHandyWorkerController() {
		super();
	}



	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MiscellaneousRecord misc;

		misc = this.miscellaneousRecordService.create();

		result = this.createEditModelAndView(misc);

		return result;

	}


	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscId) {
		ModelAndView result;
		final MiscellaneousRecord misc;

		misc = this.miscellaneousRecordService.findOne(miscId);
		Assert.notNull(misc);
		result = this.createEditModelAndView(misc);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord misc, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(misc);
		else
			try {
				this.miscellaneousRecordService.save(misc);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(misc, "misc.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord misc, final BindingResult binding) {
		ModelAndView result;

		try {
			this.miscellaneousRecordService.delete(misc);
			result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(misc, "misc.commit.error");
		}

		return result;
	}


	//Ancillary methods
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord misc) {
		ModelAndView result;

		result = this.createEditModelAndView(misc, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord misc, final String messageCode) {
		final ModelAndView result;



		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", misc);



		result.addObject("message", messageCode);

		return result;

	}




}
