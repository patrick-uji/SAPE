package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PeticionRevision;
import es.uji.ei1027.sape.dao.EmpresaDao;
import es.uji.ei1027.sape.dao.EstanciaDao;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dao.PeticionRevisionDao;
import es.uji.ei1027.sape.dao.dto.OfertaProyectoDTODao;
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
	private EstanciaDao estanciaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PeticionRevisionDao peticionRevisionDao;
	private OfertaProyectoDTODao ofertaProyectoDTODao;
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao)
	{
		this.estanciaDao = estanciaDao;
	}
    @Autowired
    public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao)
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
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("Offers LIST");
		Usuario user = Utils.getUser(session);
		if (user != null)
		{
			switch (user.getTipo())
			{
				case ADMIN:
			        model.addAttribute("offers", ofertaProyectoDTODao.getAllAccepted());
			        return "offers/listAccepted";
			    //break;
				case EMPRESA:
					model.addAttribute( "offers", ofertaProyectoDao.getAllFromCompany(user.getId()) );
			        return "offers/list";
			    //break;
			    default: //case ESTUDIANTE:
			        model.addAttribute( "offers", ofertaProyectoDao.getAllChoosable(user.getId()) );
			        model.addAttribute("offersSelection", new OffersSelection());
			        return "offers/listStudent";
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
			return "offers/listPending";
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
			model.addAttribute( "contactPersons", estanciaDao.getAllFromCompany(user.getId()) );
	        model.addAttribute("offer", new OfertaProyecto());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
	        return "offers/edit";
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
    @RequestMapping("/{id}/petition")
    public String petition(@PathVariable("id") int id, HttpSession session, Model model)
    {
    	if (Utils.isAdmin(session))
    	{
    		model.addAttribute("offer", ofertaProyectoDTODao.get(id));
    		model.addAttribute("petition", new PeticionRevision());
	        model.addAttribute("target", "/" + id + "/petition");
    		return "offers/petition";
    	}
    	return "error/401";
    }
    @RequestMapping(value="/{id}/petition", method=RequestMethod.POST)
    public String createPetition(@PathVariable("id") int id, @ModelAttribute("petition") PeticionRevision petition, HttpSession session)
    {
    	if (Utils.isAdmin(session))
    	{
    		petition.setFecha(Utils.now());
    		petition.setIDOfertaProyect(id);
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
			String now = Utils.now();
	    	/*
	        offerDao.addOffer(offer);
	        List<Request> reqList = requestDao.getRequestsBySkillIdAndDate(offer.getSkillId(), offer.getStartDate(), offer.getEndDate() );
	        List<String> dest = new ArrayList<>();
			StringBuilder msg = new StringBuilder();
	        msg.append("There is a new offer!\n\n");
	        msg.append("ID - Start Date - End Date - Offeror - Skill Id - Description\n");
	        msg.append(offer.toString());
	        msg.append("\n\n\n This is an automatic response email.\nPlease, DO NOT ANSWER BACK, as this email is not supervised.\n");
	        msg.append("\nCOPYRIGHT: EI102716GGV - SKILL SHARING\n");
	        if(reqList.size() > 0) {
	        	for(Request r : reqList) {
	        		dest.add(r.getEmail());
	        	}
	        }
	        if(dest.size() > 0) {
		        try {
					new Mail().sendMessage("SKILL SHARING - Disponible offer [DO NOT REPLY TO THIS EMAIL!]", msg.toString(), dest);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
	        }
	        */
			offer.setFechaAlta(now);
			offer.setFechaUltimoCambio(now);
			offer.setEstado(EstadoOferta.INTRODUCIDA);
			offer.setNumero(empresaDao.get(user.getId()).getProyectosTotal());
			ofertaProyectoDao.create(offer);
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
			case ADMIN:
		        return "offers/editAdmin";
			//break;
			case EMPRESA:
				//TODO: Check if he owns the resource
				model.addAttribute( "contactPersons", estanciaDao.getAllFromCompany(user.getId()) );
		        model.addAttribute("offer", ofertaProyectoDao.get(id));
				model.addAttribute("statuses", EstadoOferta.values());
		        model.addAttribute("target", "/" + id + "/update");
		        model.addAttribute("action", "Actualizar");
		        return "offers/edit";
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
			ofertaProyectoDao.update(id, new String[] {"tarea", "objetivo", "itinerario", "id_estancia", "fechaUltimoCambio"},
									 offer.getTarea(), offer.getObjetivo(), offer.getItinerario(), offer.getIdEstancia(), Utils.nowDate());
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
