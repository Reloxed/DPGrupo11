/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/customer/customer")
public class CustomerAdministrationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CustomerAdministrationController() {
		super();
	}

	// Services

	@Autowired
	private CustomerService customerService;

	// Creating a customer

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Customer customer;

		customer = this.customerService.create();
		res = this.createEditModelAndView(customer);
		return res;
	}

	// Editing an customer
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "customerID")
	public ModelAndView edit(@RequestParam int customerID) {
		ModelAndView res;
		Customer toEdit;

		toEdit = this.customerService.findOne(customerID);
		Assert.notNull(toEdit);

		res = this.createEditModelAndView(toEdit);

		return res;
	}

	// Create edit ModelAndView para customers
	// -----------------------------------------------
	protected ModelAndView createEditModelAndView(Customer customer) {
		ModelAndView res;

		res = this.createEditModelAndView(customer, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Customer customer,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("customer/edit");
		res.addObject("customer", customer);
		res.addObject("message", messageCode);
		return res;
	}

	// Saving a customer
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Customer customer, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(customer);
		} else {
			try {
				Customer saved;
				saved = this.customerService.save(customer);
				res = new ModelAndView("redirect:/actor/display.do?actorID="
						+ saved.getId());

			} catch (Throwable oops) {
				res = this.createEditModelAndView(customer,
						"customer.commit.error");
			}
		}
		return res;
	}

}
