package controllers.sponsor;

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

import services.CreditCardService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Sponsorship;


@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController{
	
	//Services
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private CreditCardService creditCardService;

	
	//Constructors 
	
	public SponsorshipSponsorController(){
		
		super();
	}

	//List-----------------------------------------------------------
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		
		sponsorships = this.sponsorshipService.findAll();
		
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI","sponsorship/sponsor/list.do");
		
		return result;
	}
	
	
	//Create-----------------------------------------------------------
	
	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Sponsorship sponsorhip;
		
		
		sponsorhip = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorhip);
		
		return result;
		
		
	}
	
	
	
	//Edit-----------------------------------------------------------
	
	@RequestMapping(value="/edit" , method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId){
		ModelAndView result;
		Sponsorship sponsorship;
		
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		
		result = this.createEditModelAndView(sponsorship);
		
		return result;
	}
	
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid final Sponsorship sponsorship,final BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try{
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:list.do");
				
			}catch(final Throwable oops){
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding){
		ModelAndView result;
		
		try{
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		}catch(final Throwable oops){
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}
		
		return result;
	}
	
	
	//Ancillary methods-----------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship){
		ModelAndView result;
		
		result = this.createEditModelAndView(sponsorship,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message){
		final ModelAndView result;
		CreditCard creditCard;
		
			
		creditCard = sponsorship.getCreditCard();
		
		
		result = new ModelAndView("sponsorship/edit");
		
		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);
		result.addObject("creditCard", creditCard);
		
		return result;
	}
	
	
	
	
	
	
	
}
