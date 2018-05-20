package es.uji.ei1027.sape.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.dao.EstanciaDao;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.model.Estancia;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.Usuario;

@Controller
@RequestMapping("/contactPersons")
public class ContactPersonController
{
	private EstanciaDao estanciaDao;
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao)
	{
		this.estanciaDao = estanciaDao;
	}
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons LIST");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			model.addAttribute( "contactPersons", estanciaDao.getAllFromCompany(user.getId()) );
			return "contactPersons/list";
		}
		else
		{
			return "error/401";
		}
    }
	@RequestMapping("/add")
	public String add(HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons ADD");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			model.addAttribute("contactPerson", new Estancia());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
	        return "contactPersons/edit";
		}
		else
		{
			return "error/401";
		}
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("contactPerson") Estancia contactPerson, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("ContactPersons CREATE");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			contactPerson.setIDEmpresa(user.getId());
			estanciaDao.create(contactPerson);
	        return "redirect:contactPersons";
		}
		else
		{
			return "error/401";
		}
    }
    @RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons READ");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			//TODO: Check if he owns the resource
			model.addAttribute("contactPerson", estanciaDao.get(id));
	        model.addAttribute("target", "/" + id + "/update");
	        model.addAttribute("action", "Actualizar");
	        return "contactPersons/edit";
		}
		else
		{
			return "error/401";
		}
    }
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable int id, @ModelAttribute("contactPerson") Estancia contactPerson, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("Offers UPDATE[" + id + "]");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
	        //if(bindingResult.hasErrors()) return "offers/update";
			contactPerson.setId(id);
			contactPerson.setIDEmpresa(user.getId());
	        estanciaDao.update(contactPerson);
	        return "redirect:../../contactPersons";
		}
		else
		{
			return "error/401";
		}
    }
    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session)
    {
		Utils.debugLog("ContactPerson DELETE[" + id + "]");
        estanciaDao.delete(id);
        return "redirect:../../offers";
    }
    public String deleteAll(HttpSession session)
    {
		Utils.debugLog("ContactPerson DELETEALL");
        estanciaDao.deleteAll();
        return "redirect:../../offers";
    }
}
