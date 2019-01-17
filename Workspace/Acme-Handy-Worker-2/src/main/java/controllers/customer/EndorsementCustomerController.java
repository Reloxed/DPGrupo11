
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.EndorsementService;
import services.EndorserService;
import controllers.AbstractController;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorsement/customer")
public class EndorsementCustomerController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private EndorserService		endorserService;

	@Autowired
	private CustomerService		customerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Customer principal;
		Collection<Endorsement> endorsementsSended;

		principal = this.customerService.findByPrincipal();
		endorsementsSended = this.endorserService.findEndorsementsSendedByEndorser(principal.getId());

		result = new ModelAndView("endorsement/list");
		result.addObject("endorsements", endorsementsSended);
		result.addObject("requestURI", "endorsement/customer/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.create();

		result = this.createEditModelAndView(endorsement);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(endorsementId);
		Assert.notNull(endorsement);
		result = this.createEditModelAndView(endorsement);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorsementId) {
		final ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(endorsementId);

		result = new ModelAndView("endorsement/display");
		result.addObject("endorsement", endorsement);
		result.addObject("requestUri", "endorsement/customer/display.do");

		return result;
	}

	//Ancillary methods------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;

	}
	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String message) {
		ModelAndView result;
		Customer principal;
		Collection<HandyWorker> recipients;

		principal = this.customerService.findByPrincipal();

		recipients = this.endorsementService.possibleHwRecipients(principal.getId());

		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("recipients", recipients);

		return result;

	}
}
