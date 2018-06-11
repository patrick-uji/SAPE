package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.model.PersonaContacto;
import es.uji.ei1027.sape.dao.PersonaContactoDao;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/contactPersons")
public class ContactPersonController
{
	private PersonaContactoDao personaContactoDao;
	@Autowired
	public void setPersonaContactoDao(PersonaContactoDao personaContactoDao)
	{
		this.personaContactoDao = personaContactoDao;
	}
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons LIST");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			model.addAttribute( "contactPersons", personaContactoDao.getAllFromCompany(user.getId()) );
			return "companies/contactPersons/list";
		}
		return "error/401";
    }
	@RequestMapping("/add")
	public String add(HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons ADD");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			model.addAttribute("contactPerson", new PersonaContacto());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
	        return "companies/contactPersons/edit";
		}
		return "error/401";
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("contactPerson") PersonaContacto contactPerson, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("ContactPersons CREATE");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			contactPerson.setIDEmpresa(user.getId());
			personaContactoDao.create(contactPerson);
	        return "redirect:contactPersons";
		}
		return "error/401";
    }
    @RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("ContactPersons READ");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
			//TODO: Check if he owns the resource
			model.addAttribute("contactPerson", personaContactoDao.get(id));
	        model.addAttribute("target", "/" + id + "/update");
	        model.addAttribute("action", "Actualizar");
	        return "companies/contactPersons/edit";
		}
		return "error/401";
    }
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable int id, @ModelAttribute("contactPerson") PersonaContacto contactPerson, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("Offers UPDATE[" + id + "]");
		Usuario user = Utils.getUser(session);
		if (user.esEmpresa())
		{
	        //if(bindingResult.hasErrors()) return "offers/update";
			contactPerson.setId(id);
			contactPerson.setIDEmpresa(user.getId());
	        personaContactoDao.update(contactPerson);
	        return "redirect:../../contactPersons";
		}
		return "error/401";
    }
    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session)
    {
		Utils.debugLog("ContactPerson DELETE[" + id + "]");
        personaContactoDao.delete(id);
        return "redirect:../../offers";
    }
    public String deleteAll(HttpSession session)
    {
		Utils.debugLog("ContactPerson DELETEALL");
        personaContactoDao.deleteAll();
        return "redirect:../../offers";
    }
}
