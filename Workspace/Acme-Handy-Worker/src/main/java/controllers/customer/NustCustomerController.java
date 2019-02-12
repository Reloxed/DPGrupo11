package controllers.customer;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NustService;

import controllers.AbstractController;

import domain.Customer;
import domain.FixUpTask;
import domain.Nust;


@Controller
@RequestMapping("/nust/customer")
public class NustCustomerController extends AbstractController {

	// Services used

	@Autowired
	private NustService nustService;
	
	@Autowired
	private CustomerService customerService;
	
	
	// Create

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Nust nust;
			Customer principal;
			Collection<FixUpTask> fixUpTasks;
			
			principal=this.customerService.findByPrincipal();
			Assert.notNull(principal);
			
			fixUpTasks= principal.getFixUpTasks();

			nust = this.nustService.create();
			result = this.createEditModelAndView(nust);
			result.addObject("fixUpTasks",fixUpTasks );

			return result;
		}

		// Delete

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@Valid Nust nust) {
			ModelAndView result;

			try {
				this.nustService.delete(nust);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(nust,
						"nust.commit.error");
			}

			return result;
		}

		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int nustId) {
			ModelAndView result;
			Nust nust;

			nust = this.nustService.findOne(nustId);
			Assert.notNull(nust);

			result = this.createEditModelAndView(nust);

			return result;
		}
	

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
		public ModelAndView saveDraft(@Valid final Nust nust, final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				final List<ObjectError> errors = binding.getAllErrors();
				for (final ObjectError e : errors)
					System.out.println(e.toString());
				result = this.createEditModelAndView(nust);
			} else
				try {
					this.nustService.save(nust,false);
					result = new ModelAndView("redirect:../list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(nust, "nust.commit.error");
				}
			return result;
		}
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
		public ModelAndView saveFinal(@Valid final Nust nust, final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				final List<ObjectError> errors = binding.getAllErrors();
				for (final ObjectError e : errors)
					System.out.println(e.toString());
				result = this.createEditModelAndView(nust);
			} else
				try {
					this.nustService.save(nust,true);
					result = new ModelAndView("redirect:../list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(nust, "nust.commit.error");
				}
			return result;
		}
		// Ancillary methods

		protected ModelAndView createEditModelAndView(final Nust nust) {
			ModelAndView result;

			result = this.createEditModelAndView(nust, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Nust nust, final String message) {
			ModelAndView result;
		

			result = new ModelAndView("nust/edit");
			result.addObject("nust", nust);
			result.addObject("message", message);
			

			return result;
		}
		
}
