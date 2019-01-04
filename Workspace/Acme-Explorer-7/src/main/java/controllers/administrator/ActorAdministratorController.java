
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditorService;
import services.ExplorerService;
import services.ManagerService;
import services.RangerService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.Sponsor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private SponsorService	sponsorService;

	@Autowired
	private ActorService	actorService;


	// Constructors

	public ActorAdministratorController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Actor> actors;

		actors = this.actorService.findAllMinusPrincipal();

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);

		return result;

	}

	@RequestMapping(value = "/listSuspicious", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		final ModelAndView result;
		final Collection<Ranger> suspiciousRangers;
		final Collection<Explorer> suspiciousExplorers;
		final Collection<Manager> suspiciousManagers;
		final Collection<Sponsor> suspiciousSponsors;
		final Collection<Auditor> suspiciousAuditors;

		suspiciousRangers = this.rangerService.findRangersBySuspicious();
		suspiciousManagers = this.managerService.findManagersBySuspicious();
		suspiciousExplorers = this.explorerService.findExplorersBySuspicious();
		suspiciousSponsors = this.sponsorService.findSponsorsBySuspicious();
		suspiciousAuditors = this.auditorService.findAuditorsBySuspicious();

		result = new ModelAndView("actor/listSuspiciousActors");
		result.addObject("suspiciousRangers", suspiciousRangers);
		result.addObject("suspiciousManagers", suspiciousManagers);
		result.addObject("suspiciousExplorers", suspiciousExplorers);
		result.addObject("suspiciousSponsors", suspiciousSponsors);
		result.addObject("suspiciousAuditors", suspiciousAuditors);

		return result;

	}

	// Creation ---------------------------------------------------------------

	// Edition
	//Ban Actors
	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "rangerId")
	public ModelAndView banRanger(@RequestParam final int rangerId) {
		final ModelAndView result;

		this.actorService.banSuspiciousActor(rangerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "managerId")
	public ModelAndView banManager(@RequestParam final int managerId) {
		final ModelAndView result;

		this.actorService.banSuspiciousActor(managerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "explorerId")
	public ModelAndView banExplorer(@RequestParam final int explorerId) {
		final ModelAndView result;

		this.actorService.banSuspiciousActor(explorerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "auditorId")
	public ModelAndView banAuditor(@RequestParam final int auditorId) {
		final ModelAndView result;

		this.actorService.banSuspiciousActor(auditorId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET, params = "sponsorId")
	public ModelAndView banSponsor(@RequestParam final int sponsorId) {
		final ModelAndView result;

		this.actorService.banSuspiciousActor(sponsorId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	//Unban actors
	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "rangerId")
	public ModelAndView unBanRanger(@RequestParam final int rangerId) {
		final ModelAndView result;

		this.actorService.unBanBannedActor(rangerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "managerId")
	public ModelAndView unBanManager(@RequestParam final int managerId) {
		final ModelAndView result;

		this.actorService.unBanBannedActor(managerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "explorerId")
	public ModelAndView unBanExplorer(@RequestParam final int explorerId) {
		final ModelAndView result;

		this.actorService.unBanBannedActor(explorerId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "auditorId")
	public ModelAndView unBanAuditor(@RequestParam final int auditorId) {
		final ModelAndView result;

		this.actorService.unBanBannedActor(auditorId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET, params = "sponsorId")
	public ModelAndView unBanSponsor(@RequestParam final int sponsorId) {
		final ModelAndView result;

		this.actorService.unBanBannedActor(sponsorId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
