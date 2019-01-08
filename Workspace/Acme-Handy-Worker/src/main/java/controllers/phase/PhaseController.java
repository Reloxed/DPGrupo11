package controllers.phase;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import controllers.AbstractController;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Controller
@RequestMapping("/phase/handy-worker")
public class PhaseController extends AbstractController {

	// Services

	@Autowired
	private PhaseService phaseService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private ApplicationService applicationService;

	// Constructors
	public PhaseController() {
		super();
	}

	// Lists

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = "fixuptaskID")
	public ModelAndView list(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Collection<Phase> phases;
		HandyWorker creator;

		phases = this.phaseService.findPhasesFixUpTask(fixuptaskID);
		creator = this.phaseService.creator(phases.iterator().next().getId());

		res = new ModelAndView("phase/list");
		res.addObject("requestURI", "phase/handy-worker/list.do");
		res.addObject("phases", phases);
		res.addObject("creator", creator);
		res.addObject("fixuptaskID", fixuptaskID);

		return res;
	}

	// Creating a phase

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "fixuptaskID")
	public ModelAndView create(@RequestParam int fixuptaskID) {
		ModelAndView res;
		Phase phase;
		FixUpTask fixUpTask;

		phase = this.phaseService.create();

		fixUpTask = this.fixUpTaskService.findOne(fixuptaskID);
		phase.setFixUpTask(fixUpTask);
		res = this.createEditModelAndView(phase);

		return res;
	}

	protected ModelAndView createEditModelAndView(Phase phase) {
		ModelAndView res;

		res = createEditModelAndView(phase, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Phase phase,
			String messageCode) {
		ModelAndView res;
		HandyWorker principal;
		FixUpTask fixUpTask;
		Collection<Application> applicationsFixUpTask;
		boolean editable = false;

		fixUpTask = phase.getFixUpTask();
		applicationsFixUpTask = this.applicationService
				.findAllApplicationsByFixUpTask(fixUpTask.getId());
		principal = this.handyWorkerService.findByPrincipal();

		for (Application a : applicationsFixUpTask) {
			if (a.getApplicant() == principal
					&& a.getStatus().contains("ACCEPTED")) {
				editable = true;
			}
		}

		res = new ModelAndView("phase/edit");
		res.addObject("phase", phase);
		res.addObject("message", messageCode);
		res.addObject("editable", editable);

		return res;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Phase phase, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createEditModelAndView(phase);
		} else {
			try {
				this.phaseService.save(phase);
				res = new ModelAndView("redirect:list.do?fixuptaskID="
						+ phase.getFixUpTask().getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(phase, "phase.commit.error");
			}
		}
		return res;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int phaseID) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOne(phaseID);

		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do?fixuptaskID="
					+ phase.getFixUpTask().getId());
		} catch (Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}
}
