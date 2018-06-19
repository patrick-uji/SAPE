package es.uji.ei1027.sape.controller;
import java.util.List;
import java.util.ArrayList;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.dao.AlumnoDao;
import es.uji.ei1027.sape.dao.EmpresaDao;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.domain.VisibleOffer;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dto.OfertaProyectoDTO;
import org.springframework.stereotype.Controller;
import es.uji.ei1027.sape.model.PeticionRevision;
import es.uji.ei1027.sape.domain.OffersSelection;
import es.uji.ei1027.sape.dao.PersonaContactoDao;
import es.uji.ei1027.sape.dao.PeticionRevisionDao;
import es.uji.ei1027.sape.dao.dto.AsignacionDTODao;
import org.springframework.validation.BindingResult;
import es.uji.ei1027.sape.validation.OfferValidator;
import es.uji.ei1027.sape.validation.PetitionValidator;
import es.uji.ei1027.sape.dao.dto.OfertaProyectoDTODao;
import es.uji.ei1027.sape.domain.VisibleOffersContainer;
import es.uji.ei1027.sape.dao.dto.PeticionRevisionDTODao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
@RequestMapping("/offers")
public class OfferController
{
	private AlumnoDao alumnoDao;
	private EmpresaDao empresaDao;
	private AsignacionDTODao asignacionDTODao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PersonaContactoDao personaContactoDao;
	private PeticionRevisionDao peticionRevisionDao;
	private OfertaProyectoDTODao ofertaProyectoDTODao;
	private PeticionRevisionDTODao peticionRevisionDTODao;
	@Autowired
	public void setAlumnoDao(AlumnoDao alumnoDao)
	{
		this.alumnoDao = alumnoDao;
	}
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	@Autowired
	public void setAsignacionDTODao(AsignacionDTODao asignacionDTODao)
	{
		this.asignacionDTODao = asignacionDTODao;
	}
	@Autowired
	public void setPersonaContactoDao(PersonaContactoDao personaContactoDao)
	{
		this.personaContactoDao = personaContactoDao;
	}
    @Autowired
    public void setPersonaContactoDao(OfertaProyectoDao ofertaProyectoDao)
    {
    	this.ofertaProyectoDao = ofertaProyectoDao;
    }
    @Autowired
    public void setPeticionRevisionDao(PeticionRevisionDao peticionRevisionDao)
	{
		this.peticionRevisionDao = peticionRevisionDao;
	}
    @Autowired
    public void setOfertaProyectoDTODao(OfertaProyectoDTODao ofertaProyectoDTODao)
	{
		this.ofertaProyectoDTODao = ofertaProyectoDTODao;
	}
    @Autowired
    public void setPeticionRevisionDTODao(PeticionRevisionDTODao peticionRevisionDTODao)
    {
    	this.peticionRevisionDTODao = peticionRevisionDTODao;
    }
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("Offers LIST");
		Usuario user = Utils.getUser(session);
		if (user != null)
		{
			switch (user.getTipo())
			{
				case BTC:
				case CCD:
					VisibleOffer newVisibleOffer;
					List<VisibleOffer> visibleOffers = new ArrayList<VisibleOffer>();
					List<OfertaProyectoDTO> offers = ofertaProyectoDTODao.getAllAccepted();
					VisibleOffersContainer visiblesContainer = new VisibleOffersContainer();
					for (OfertaProyectoDTO currOffer : offers)
					{
						newVisibleOffer = new VisibleOffer();
						newVisibleOffer.setId(currOffer.getId());
						switch (currOffer.getEstado())
						{
							case ACEPTADA:
								newVisibleOffer.setIgnore(false);
								newVisibleOffer.setVisible(false);
							break;
							case VISIBLE:
								newVisibleOffer.setIgnore(false);
								newVisibleOffer.setVisible(true);
							break;
							default: //case ASIGNADA
								newVisibleOffer.setIgnore(true);
								newVisibleOffer.setVisible(true);
							break;
						}
						visibleOffers.add(newVisibleOffer);
					}
					visiblesContainer.setVisibleOffers(visibleOffers);
					model.addAttribute("visiblesContainer", visiblesContainer);
			        model.addAttribute("offers", offers);
			        return "admins/offers/listAccepted";
			    //break;
				case EMPRESA:
					model.addAttribute( "offers", ofertaProyectoDao.getAllFromCompany(user.getId()) );
			        return "companies/offers/list";
			    //break;
			    default: //case ALUMNO:
			    	int userID = user.getId();
			    	Alumno student = alumnoDao.get(userID);
			    	boolean hasAssignment = asignacionDTODao.getActiveFromStudent(userID) != null;
			    	if (!hasAssignment)
			    	{
				        model.addAttribute( "offers", ofertaProyectoDTODao.getAllChoosable(userID, student.getItinerario()) );
				        model.addAttribute("offersSelection", new OffersSelection());
			    	}
					model.addAttribute("hasAssignment", hasAssignment);
			        return "students/offers/list";
				//break;
			}
		}
		return "error/401";
    }
	@RequestMapping("/pending")
	public String listPending(HttpSession session, Model model)
	{
		if (Utils.isAdmin(session))
		{
			model.addAttribute("cancelRequestedOffers", ofertaProyectoDTODao.getAllCancelRequested());
			model.addAttribute("pendingOffers", ofertaProyectoDTODao.getAllPending());
			return "admins/offers/list";
		}
		return "error/401";
	}
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
		Utils.debugLog("Offers ADD");
		Usuario user = Utils.getUser(session);
		if (Utils.isCompany(user))
		{
			model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
	        model.addAttribute("offer", new OfertaProyecto());
	        Utils.setupCreateModel(model);
	        return "companies/offers/edit";
		}
		return "error/401";
    }
    @RequestMapping(value="/visibility", method=RequestMethod.POST)
    public String visibility(@ModelAttribute("visiblesContainer") VisibleOffersContainer visiblesContainer, HttpSession session)
    {
 		Utils.debugLog("Offers VISIBILITY");
 		if (Utils.isSuperAdmin(session))
 		{
 			List<Integer> hiddenIDs = new ArrayList<Integer>();
 			List<Integer> visibleIDs = new ArrayList<Integer>();
 			for (VisibleOffer currOffer : visiblesContainer.getVisibleOffers())
			{
				if (!currOffer.getIgnore())
				{
					if (currOffer.getVisible())
					{
						visibleIDs.add(currOffer.getId());
					}
					else
					{
						hiddenIDs.add(currOffer.getId());
					}
				}
			}
 			if (visibleIDs.size() != 0)
 			{
 	 			ofertaProyectoDao.updateAll(Utils.listToArray(visibleIDs), OfertaProyectoDao.OFFER_STATUS_FIELD, EstadoOferta.VISIBLE.getID());
 			}
 			if (hiddenIDs.size() != 0)
 			{
 	 			ofertaProyectoDao.updateAll(Utils.listToArray(hiddenIDs), OfertaProyectoDao.OFFER_STATUS_FIELD, EstadoOferta.ACEPTADA.getID());
 			}
 			return "redirect:../offers";
 		}
 		return "error/401";
    }
    @RequestMapping("/{id}/accept")
    public String accept(@PathVariable("id") int id, HttpSession session)
    {
    	if (Utils.isAdmin(session))
    	{
    		setOfferStatus(id, EstadoOferta.ACEPTADA);
    		return "redirect:../pending";
    	}
    	return "error/401";
    }
    private void setOfferStatus(int id, EstadoOferta status)
    {
    	ofertaProyectoDao.update(id, OfertaProyectoDao.OFFER_STATUS_FIELD, status.getID());
    }
    @RequestMapping(value="/{id}/petition", method=RequestMethod.POST)
    public String createPetition(@PathVariable("id") int id, @ModelAttribute("petition") PeticionRevision petition, HttpSession session, Model model, BindingResult bindingResult)
    {
    	Usuario user = Utils.getUser(session);
    	if (Utils.isSuperAdmin(user))
    	{
    		if (Utils.validate(new PetitionValidator(), petition, bindingResult))
    		{
        		petition.setFecha(Utils.now());
        		petition.setIDOfertaProyect(id);
        		petition.setIDAdmin(user.getId());
        		setOfferStatus(id, EstadoOferta.PENDIENTE_REVISION);
        		peticionRevisionDao.create(petition);
        		return "redirect:../pending";
    		}
    		else
    		{
    			model.addAttribute("petitions", peticionRevisionDTODao.getAllFromOffer(id));
		        model.addAttribute("offer", ofertaProyectoDTODao.get(id));
		        return "admins/offers/view";
    		}
    	}
    	return "error/401";
    }
    @RequestMapping("/{id}/reject")
    public String reject(@PathVariable("id") int id, HttpSession session)
    {
    	if (Utils.isAdmin(session))
    	{
    		setOfferStatus(id, EstadoOferta.RECHAZADA);
    		return "redirect:../pending";
    	}
    	return "error/401";
    }
	@RequestMapping("/{id}/show")
	public String show(@PathVariable("id") int id, HttpSession session)
	{
		if (Utils.isSuperAdmin(session))
		{
    		setOfferStatus(id, EstadoOferta.VISIBLE);
    		return "redirect:../../offers";
		}
		return "error/401";
	}
	@RequestMapping("/{id}/hide")
	public String hide(@PathVariable("id") int id, HttpSession session)
	{
		if (Utils.isSuperAdmin(session))
		{
    		setOfferStatus(id, EstadoOferta.ACEPTADA);
    		return "redirect:../../offers";
		}
		return "error/401";
	}
	@RequestMapping("/{id}/cancel")
	public String cancel(@PathVariable("id") int id, HttpSession session)
	{
		Usuario user = Utils.getUser(session);
		switch (user.getTipo())
		{
			case BTC:
	    		setOfferStatus(id, EstadoOferta.ANULADA);
				return "redirect:../../offers";
			//break;
			case EMPRESA:
				OfertaProyecto offer = ofertaProyectoDao.get(id);
		    	ofertaProyectoDao.update(id, new String[] {"id_EstadoOferta", "id_EstadoOfertaPreAnulacion"},
		    							 EstadoOferta.PENDIENTE_ANULACION.getID(), offer.getEstado().getID());
				return "redirect:../../offers";
			//break;
			default: return "error/401";
		}
	}
	@RequestMapping("/{id}/restore")
	public String restore(@PathVariable("id") int id, HttpSession session)
	{
		if (Utils.isSuperAdmin(session))
		{
			OfertaProyecto offer = ofertaProyectoDao.get(id);
			setOfferStatus(id, offer.getEstadoPreAnulacion());
    		return "redirect:../../offers";
		}
		return "error/401";
	}
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("offer") OfertaProyecto offer, HttpSession session, Model model, BindingResult bindingResult)
    {
		Utils.debugLog("Offers CREATE");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			if (Utils.validate(new OfferValidator(), offer, bindingResult))
			{
				int totalProjects = empresaDao.get(user.getId()).getProyectosTotal() + 1;
				String now = Utils.now();
				offer.setFechaAlta(now);
				offer.setFechaUltimoCambio(now);
				offer.setEstado(EstadoOferta.INTRODUCIDA);
				offer.setNumero(totalProjects);
				ofertaProyectoDao.create(offer);
				empresaDao.update(user.getId(), new String[] {"proyectosTotal"}, totalProjects);
		        return "redirect:offers";
			}
			else
			{
				model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
		        Utils.setupCreateModel(model);
		        return "companies/offers/edit";
			}
		}
		return "error/401";
   }
   @RequestMapping("/{id}")
   public String read(@PathVariable int id, HttpSession session, Model model)
   {
		Utils.debugLog("Offers READ");
		Usuario user = Utils.getUser(session);
		switch (user.getTipo())
		{
			case BTC:
	    		model.addAttribute("petition", new PeticionRevision());
			case CCD:
				model.addAttribute("petitions", peticionRevisionDTODao.getAllFromOffer(id));
		        model.addAttribute("offer", ofertaProyectoDTODao.get(id));
		        return "admins/offers/view";
			//break;
			case EMPRESA:
				//TODO: Check if he owns the resource
				model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
				model.addAttribute("petitions", peticionRevisionDTODao.getAllFromOffer(id));
		        model.addAttribute("offer", ofertaProyectoDao.get(id));
				model.addAttribute("statuses", EstadoOferta.values());
				Utils.setupUpdateModel(model, id);
		        return "companies/offers/edit";
			//break;
			default: //case ALUMNO
		        model.addAttribute("offer", ofertaProyectoDTODao.get(id));
				return "students/offers/view";
			//break;
		}
   }
   @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
   public String update(@PathVariable int id, @ModelAttribute("offer") OfertaProyecto offer, HttpSession session, Model model, BindingResult bindingResult)
   {
		Utils.debugLog("Offers UPDATE[" + id + "]");
		Usuario user = Utils.getUser(session);
		if (Utils.isCompany(user))
		{
			if (Utils.validate(new OfferValidator(), offer, bindingResult))
			{
				ofertaProyectoDao.update(id, new String[] {"titulo", "objetivo", "id_Itinerario", "id_PersonaContacto", "fechaUltimoCambio"},
										 offer.getTitulo(), offer.getObjetivo(), offer.getItinerario().getID(), offer.getIdPersonaContacto(), Utils.nowDate());
		        return "redirect:../../offers";
			}
			else
			{
				model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
				Utils.setupUpdateModel(model, id);
		        return "companies/offers/edit";
			}
		}
		return "error/401";
   }
   @RequestMapping("/{id}/delete")
   public String delete(@PathVariable int id, HttpSession session)
   {
		Utils.debugLog("Offers DELETE[" + id + "]");
        ofertaProyectoDao.delete(id);
        return "redirect:../../offers";
   }
   public String deleteAll(HttpSession session)
   {
		Utils.debugLog("Offers DELETEALL");
        ofertaProyectoDao.deleteAll();
        return "redirect:../../offers";
   }
}
