package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PeticionRevision;
import es.uji.ei1027.sape.dao.EmpresaDao;
import es.uji.ei1027.sape.dao.PersonaContactoDao;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dao.PeticionRevisionDao;
import es.uji.ei1027.sape.dao.dto.OfertaProyectoDTODao;
import es.uji.ei1027.sape.dao.dto.PeticionRevisionDTODao;
import es.uji.ei1027.sape.domain.OffersSelection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
@RequestMapping("/offers")
public class OfferController
{
	private EmpresaDao empresaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PersonaContactoDao personaContactoDao;
	private PeticionRevisionDao peticionRevisionDao;
	private OfertaProyectoDTODao ofertaProyectoDTODao;
	private PeticionRevisionDTODao peticionRevisionDTODao;
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
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
			        model.addAttribute("offers", ofertaProyectoDTODao.getAllAccepted());
			        model.addAttribute("type", "aceptadas");
			        return "admins/offers/list";
			    //break;
				case EMPRESA:
					model.addAttribute( "offers", ofertaProyectoDao.getAllFromCompany(user.getId()) );
			        return "companies/offers/list";
			    //break;
			    default: //case ALUMNO:
			        model.addAttribute( "offers", ofertaProyectoDTODao.getAllChoosable(user.getId()) );
			        model.addAttribute("offersSelection", new OffersSelection());
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
			model.addAttribute("offers", ofertaProyectoDTODao.getAllPending());
	        model.addAttribute("type", "pendientes");
			return "admins/offers/list";
		}
		return "error/401";
	}
	@RequestMapping("/cancelRequests")
	public String listCancelRequests(HttpSession session, Model model)
	{
		if (Utils.isAdmin(session))
		{
			model.addAttribute("offers", ofertaProyectoDTODao.getAllCancelRequested());
	        model.addAttribute("type", "a cancelar");
			return "admins/offers/list";
		}
		return "error/401";
	}
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
		Utils.debugLog("Offers ADD");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
	        model.addAttribute("offer", new OfertaProyecto());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
	        return "companies/offers/edit";
		}
		return "error/401";
    }
    @RequestMapping("/{id}/accept")
    public String accept(@PathVariable("id") int id, HttpSession session)
    {
    	if (Utils.isAdmin(session))
    	{
    		ofertaProyectoDao.update(id, new String[] {"id_EstadoOferta"}, EstadoOferta.ACEPTADA.getID());
    		return "redirect:../pending";
    	}
    	return "error/401";
    }
    @RequestMapping(value="/{id}/petition", method=RequestMethod.POST)
    public String createPetition(@PathVariable("id") int id, @ModelAttribute("petition") PeticionRevision petition, HttpSession session)
    {
    	Usuario user = Utils.getUser(session);
    	if (user.esSuperAdmin())
    	{
    		petition.setFecha(Utils.now());
    		petition.setIDOfertaProyect(id);
    		petition.setIDAdmin(user.getId());
    		ofertaProyectoDao.update(id, new String[] {"id_EstadoOferta"}, EstadoOferta.PENDIENTE_REVISION.getID());
    		peticionRevisionDao.create(petition);
    		return "redirect:../pending";
    	}
    	return "error/401";
    }
    @RequestMapping("/{id}/reject")
    public String reject(@PathVariable("id") int id, HttpSession session)
    {
    	if (Utils.isAdmin(session))
    	{
    		ofertaProyectoDao.update(id, new String[] {"id_EstadoOferta"}, EstadoOferta.RECHAZADA.getID());
    		return "redirect:../pending";
    	}
    	return "error/401";
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("offer") OfertaProyecto offer, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("Offers CREATE");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			int totalProjects = empresaDao.get(user.getId()).getProyectosTotal() + 1;
			String now = Utils.now();
			offer.setFechaAlta(now);
			offer.setFechaUltimoCambio(now);
			offer.setEstado(EstadoOferta.INTRODUCIDA);
			offer.setNumero(totalProjects);
			ofertaProyectoDao.create(offer);
			Utils.debugLog("proyectosTotal = " + totalProjects);
			empresaDao.update(user.getId(), new String[] {"proyectosTotal"}, totalProjects);
	        return "redirect:offers";
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
				model.addAttribute("isSuperAdmin", true);
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
		        model.addAttribute("target", "/" + id + "/update");
		        model.addAttribute("action", "Actualizar");
		        return "companies/offers/edit";
			//break;
			default: return "error/401";
		}
   }
   @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
   public String update(@PathVariable int id, @ModelAttribute("offer") OfertaProyecto offer, HttpSession session, BindingResult bindingResult)
   {
		Utils.debugLog("Offers UPDATE[" + id + "]");
		if (Utils.isCompany(session))
		{
	        //if(bindingResult.hasErrors()) return "offers/update";
			ofertaProyectoDao.update(id, new String[] {"titulo", "objetivo", "id_Itinerario", "id_PersonaContacto", "fechaUltimoCambio"},
									 offer.getTitulo(), offer.getObjetivo(), offer.getItinerario().getID(), offer.getIdPersonaContacto(), Utils.nowDate());
	        return "redirect:../../offers";
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
