package controllers.creditcard;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import services.CustomerService;
import services.SponsorService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.CreditCard;
import domain.Customer;
import domain.Sponsor;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/creditcard")
public class CreditCardController extends AbstractController {

	// Services

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Constructor

	public CreditCardController() {
		super();
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();
		res = this.createEditModelAndView(creditCard);

		return res;
	}

	protected ModelAndView createEditModelAndView(CreditCard creditCard) {
		ModelAndView res;

		res = createEditModelAndView(creditCard, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(CreditCard creditCard,
			String messageCode) {
		ModelAndView res;
		SystemConfiguration systemConfiguration;
		String[] makes;
		Actor principal;

		systemConfiguration = this.systemConfigurationService
				.findMySystemConfiguration();
		makes = systemConfiguration.getListCreditCardMakes().split(",");

		principal = this.actorService.findByPrincipal();

		res = new ModelAndView("creditcard/edit");
		res.addObject("creditCard", creditCard);
		res.addObject("makes", makes);
		res.addObject("principal", principal);
		res.addObject("message", messageCode);

		return res;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView res = null;
		Actor principal;

		if (binding.hasErrors()) {
			res = createEditModelAndView(creditCard);
		} else {
			try {
				CreditCard saved;
				saved = this.creditCardService.save(creditCard);
				principal = this.actorService.findByPrincipal();
				if (principal instanceof Customer) {
					Customer customerPrincipal;
					Collection<CreditCard> creditCards;
					customerPrincipal = this.customerService.findByPrincipal();
					creditCards = customerPrincipal.getCreditCards();
					creditCards.add(saved);
					customerPrincipal.setCreditCards(creditCards);
					this.customerService.save(customerPrincipal);
					res = new ModelAndView(
							"redirect:/actor/display.do?actorID="
									+ principal.getId());
				} else if (principal instanceof Sponsor) {
					Sponsor sponsorPrincipal;
					Collection<CreditCard> creditCards;
					sponsorPrincipal = this.sponsorService.findByPrincipal();
					creditCards = sponsorPrincipal.getCreditCards();
					creditCards.add(creditCard);
					sponsorPrincipal.setCreditCards(creditCards);
					new ModelAndView("redirect:/actor/display.do?actorID="
							+ principal.getId());
				}
			} catch (Throwable oops) {
				res = createEditModelAndView(creditCard,
						"creditcard.commit.error");
			}
		}
		return res;
	}
}
