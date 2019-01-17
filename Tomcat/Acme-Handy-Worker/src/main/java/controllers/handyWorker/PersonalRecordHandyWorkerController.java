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

import services.PersonalRecordService;

import controllers.AbstractController;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	
	//Services
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	// Constructors

		public PersonalRecordHandyWorkerController() {
			super();
		}

		// Listing

		// Creation ---------------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			final ModelAndView result;
			final PersonalRecord pr;

			pr = this.personalRecordService.create();

			result = this.createEditModelAndView(pr);

			return result;

		}
		// Edition
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int prId) {
			ModelAndView result;
			PersonalRecord pr;

			pr = this.personalRecordService.findOne(prId);
			Assert.notNull(pr);
			result = this.createEditModelAndView(pr);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final PersonalRecord pr, final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors())
				result = this.createEditModelAndView(pr);
			else
				try {
					this.personalRecordService.save(pr);
					result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(pr, "pr.commit.error");
				}

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(final PersonalRecord pr, final BindingResult binding) {
			ModelAndView result;

			try {
				this.personalRecordService.delete(pr);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(pr, "pr.commit.error");
			}

			return result;
		}

		//Ancillary methods
		protected ModelAndView createEditModelAndView(final PersonalRecord pr) {
			ModelAndView result;

			result = this.createEditModelAndView(pr, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final PersonalRecord pr, final String messageCode) {
			final ModelAndView result;

			result = new ModelAndView("personalRecord/edit");
			result.addObject("personalRecord", pr);


			result.addObject("message", messageCode);

			return result;

		}

	
}
