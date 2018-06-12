package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.dao.EmpresaDao;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dao.PersonaContactoDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/companies")
public class CompanyController
{
	private EmpresaDao empresaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PersonaContactoDao personaContactoDao;
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao)
	{
		this.ofertaProyectoDao = ofertaProyectoDao;
	}
	@Autowired
	public void setPersonaContactoDao(PersonaContactoDao personaContactoDao)
	{
		this.personaContactoDao = personaContactoDao;
	}
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("Companies LIST");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("companies", empresaDao.getAll());
			return "admins/companies/list";
		}
		return "error/401";
    }
	@RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("Companies READ");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("contactPersons", personaContactoDao.getAllFromCompany(id));
			model.addAttribute("offers", ofertaProyectoDao.getAllFromCompany(id));
			model.addAttribute("company", empresaDao.get(id));
			return "admins/companies/view";
		}
		return "error/404";
    }
}
