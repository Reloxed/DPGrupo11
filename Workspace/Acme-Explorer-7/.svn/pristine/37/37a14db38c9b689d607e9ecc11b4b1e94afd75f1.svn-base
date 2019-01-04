
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	// Services

	@Autowired
	private AuditService	auditService;


	// Constructors

	public AuditController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		final ModelAndView result;

		final Audit audit;

		audit = this.auditService.findOne(auditId);

		result = new ModelAndView("audit/display");
		result.addObject("audit", audit);

		return result;

	}
}
