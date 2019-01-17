package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.NoteService;

import controllers.AbstractController;


import domain.HandyWorker;
import domain.Note;

@Controller
@RequestMapping("/note/handyWorker")
public class NoteHandyWorkerController extends AbstractController {

	//Services
	
	@Autowired
	private NoteService noteService;

	@Autowired
	private HandyWorkerService handyWorkerService;
	//Constructor
	
	public NoteHandyWorkerController() {
		super();
	}
	
	//create
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Note note;
		
		
		
		note=this.noteService.create();
		
		result=this.createEditModelAndView(note);
		
		return result;
		
	}
	
	//save
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST,params="save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}

		return result;
	}

	
	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId){
		final ModelAndView result;	
		Note note;
		
		note=this.noteService.findOne(noteId);
		
		result=new ModelAndView("note/display");
		result.addObject("note", note);
		result.addObject("requestUri", "note/handyWorker/display.do");
		
		return result;
	
		
	}
	
	//Ancillary methods
	
	 
	
	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		final ModelAndView result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}
}
