package controllers.administator;

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

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyAdministratorController extends AbstractController{

	//Services
	@Autowired
	private WarrantyService warrantyService;

	//Constructors

	public WarrantyAdministratorController(){
		super();
	}

	//Listing------------------------------------------------------------------------

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;
		final Collection<Warranty> warranties;

		warranties = this.warrantyService.findAll();

		result = new ModelAndView("warranty/list");
		result.addObject("warranties", warranties);
		result.addObject("requestURI", "warranty/administrator/list.do");

		return result;
	}

	//Display------------------------------------------------------------------------

	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int warrantyId){
		final ModelAndView result;
		final Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);

		result = new ModelAndView("warranty/display");
		result.addObject("warranty", warranty);
		result.addObject("requestURI", "warranty/administrator/display.do");

		return result;
	}

	//Create------------------------------------------------------------------------

	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}


	//Edition------------------------------------------------------------------------

	@RequestMapping(value="/edit", method =RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId){
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);

		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params = "saveFinal")
	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding){
		ModelAndView result;

		if(binding.hasErrors())
			result = this.createEditModelAndView(warranty);
		else
			try{
				this.warrantyService.save(warranty);
				result = new ModelAndView("redirect:list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(warranty, "warranty.commit.error");
			}
		return result;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Warranty warranty, final BindingResult binding){
		ModelAndView result;

		try{
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		}catch(final Throwable oops){
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}
		return result;
	}



	//Ancillary methods------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Warranty warranty){
		ModelAndView result;

		result = this.createEditModelAndView(warranty,null);

		return result;

	}
	protected ModelAndView createEditModelAndView(final Warranty warranty, final String message){
		ModelAndView result;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);
		result.addObject("message", message);

		return result;

	}

}
