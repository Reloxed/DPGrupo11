package controllers.handyWorker;

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

import services.NoteService;
import services.ReportService;
import controllers.AbstractController;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/note/handyWorker")
public class NoteHandyWorkerController extends AbstractController{

	// Services

	@Autowired
	private NoteService noteService;

	@Autowired
	private ReportService reportService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		final ModelAndView result;

		Note note;

		note = this.noteService.findOne(noteId);

		result = new ModelAndView("note/display");
		result.addObject("note", note);

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "reportId")
	public ModelAndView create(@RequestParam int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		Report report = this.reportService.findOne(reportId);
		note.setReport(report);

		result = this.createEditModelAndView(note);
		result.addObject("report", report);
		return result;
	}

	// Edition--------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "noteId")
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);

		result = this.createEditModelAndView(note);
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				Note noter = this.noteService.save(note);
				result = new ModelAndView("redirect:display.do?noteId=" + noter.getId());
				Report report = this.reportService.findOne(noter.getReport().getId());
				report.getNotes().add(noter);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note,
						"report.commit.error");
			}
		return result;
	}
	
	// Ancillary Methods
	
	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Note note,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", message);
		result.addObject("requestUri", "note/handyWorker/edit.do");

		return result;
	}

}