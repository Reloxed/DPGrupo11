package controllers.customer;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import services.FixUpTaskService;
import services.TonemaService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Tonema;

@Controller
@RequestMapping("/tonema")
public class TonemaCustomerController extends AbstractController{
	
	// Services
	
	@Autowired
	private TonemaService tonemaService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	// Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Locale locale, @RequestParam(required = false) Integer fixUpTaskId) {
		ModelAndView result;
		Collection<Tonema> tonemas;
		String language;
		
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DAY_OF_MONTH, -30);
		Date onePreviousMonth = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -30);
		Date twoPreviousMonth = c.getTime();
		
		language = locale.getLanguage();
		
		if(fixUpTaskId==null) {
			tonemas = this.tonemaService.findTonemaByPrincipal();
		} else {
			tonemas = this.tonemaService.findTonemaFinalByFix(fixUpTaskId);
		}
		
		result = new ModelAndView("tonema/list");
		result.addObject("tonemas", tonemas);
		result.addObject("onePreviousMonth", onePreviousMonth);
		result.addObject("twoPreviousMonth", twoPreviousMonth);
		result.addObject("language", language);
		result.addObject("requestURI", "tonema/list.do");
		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tonemaId) {
		final ModelAndView result;

		final Tonema tonema;

		tonema = this.tonemaService.findOne(tonemaId);
		result = new ModelAndView("tonema/display");
		result.addObject("tonema", tonema);
		result.addObject("requestURI", "tonema/display.do");

		return result;
	}
	
	// Create
	
	@RequestMapping(value="/create", method=RequestMethod.GET, params="fixUpTaskId")
	public ModelAndView create(@RequestParam int fixUpTaskId){
		ModelAndView result;
		Tonema tonema;
		
		tonema = this.tonemaService.create();
		FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		tonema.setFixUpTask(fixUpTask);
		
		result = this.createEditModelAndView(tonema);
		
		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tonemaId) {
		ModelAndView result;
		Tonema tonema;

		tonema = this.tonemaService.findOne(tonemaId);
		Assert.notNull(tonema);

		result = this.createEditModelAndView(tonema);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@Valid final Tonema tonema,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tonema);
		else
			try {
				tonema.setIsFinal(true);
				this.tonemaService.save(tonema);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tonema,
						"tonema.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tonema tonema,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tonema);
		else
			try {
				this.tonemaService.save(tonema);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tonema,
						"tonema.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Tonema tonema) {
		ModelAndView result;
		try {
			this.tonemaService.delete(tonema);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tonema, "tonema.commit.error");
		}
		return result;
	}

	// Ancillary
	// methods--------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Tonema tonema) {
		ModelAndView result;

		result = this.createEditModelAndView(tonema, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Tonema tonema,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("tonema/edit");
		result.addObject("tonema", tonema);
		result.addObject("message", message);

		return result;
	}

}
