package controllers.phase;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Phase;

@Controller
@RequestMapping("/phase")
public class PhaseController extends AbstractController {

	// Services

	@Autowired
	private PhaseService phaseService;

	// Constructors
	public PhaseController() {
		super();
	}

	// Lists

	@RequestMapping(value = "/handy-worker,customer/list", params = "fixuptaskID")
	public ModelAndView list(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Collection<Phase> phases;
		HandyWorker creator;

		phases = this.phaseService.findPhasesFixUpTask(fixuptaskID);
		creator = this.phaseService.creator(phases.iterator().next().getId());

		res = new ModelAndView("phase/list");
		res.addObject("requestURI", "phase/handy-worker,customer/list.do");
		res.addObject("phases", phases);
		res.addObject("creator", creator);

		return res;
	}
}
