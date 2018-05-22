package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Admin;
import es.uji.ei1027.sape.dao.AdminDao;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.dao.UsuarioDao;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import es.uji.ei1027.sape.validation.AdminValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admins")
public class AdminController
{
    private AdminDao adminDao;
    private UsuarioDao usuarioDao;
    @Autowired
    public void setAdminDao(AdminDao adminDao)
    {
        this.adminDao = adminDao;
    }
    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
    @RequestMapping
    public String list(HttpSession session, Model model)
    {
    	Utils.debugLog("Admin LIST");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("admins", adminDao.getAll());
            return "admins/list";
    	}
        return "error/401";
    }
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
    	Utils.debugLog("Admin ADD");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("user", new Usuario());
            model.addAttribute("admin", new Admin());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
            return "admins/edit";
    	}
		return "error/401";
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("user") Usuario user, @ModelAttribute("admin") Admin admin, HttpSession session, BindingResult bindingResult)
    {
    	Utils.debugLog("Admin CREATE");
    	if (Utils.isAdmin(session))
    	{
    		if (Utils.validateUser(user, bindingResult) &&
    			Utils.validate(new AdminValidator(), admin, bindingResult))
    		{
        		usuarioDao.create(user);
        		adminDao.create(admin);
            	return "redirect:admins";
    		}
			return "admins/edit";
    	}
		return "error/401";
    }
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog("Admin READ");
		Usuario user = Utils.getUser(session);
    	if (Utils.isAdmin(session))
    	{
			if (user != null && (user.esAdmin() || user.getId() == id))
			{
		        model.addAttribute("target", "students/update");
	            model.addAttribute("user", usuarioDao.get(id));
	            model.addAttribute("admin", adminDao.get(id));
		        model.addAttribute("action", "Actualizar");
				return "admins/edit";
			}
			return "error/404";
    	}
		return "error/401";
	}
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public String update(@PathVariable int id, @ModelAttribute("user") Usuario user, @ModelAttribute("admin") Admin admin, HttpSession session, BindingResult bindingResult)
	{
    	Utils.debugLog("Admin UPDATE");
    	if (Utils.isAdmin(session))
    	{
			if (Utils.validateUser(user, bindingResult) &&
	  			Utils.validate(new AdminValidator(), admin, bindingResult))
			{
		        adminDao.update(admin);
		        return "redirect:..";
			}
			return "admins/edit";
    	}
		return "error/401";
	}
	@RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session)
	{
    	Utils.debugLog("Admin DELETE");
    	if (Utils.isAdmin(session))
    	{
            adminDao.delete(id);
            return "redirect:..";
    	}
		return "error/401";
	}
	@RequestMapping("/delete")
    public String deleteAll(HttpSession session)
    {
    	Utils.debugLog("Admin DELETEALL");
    	if (Utils.isAdmin(session))
    	{
        	adminDao.deleteAll();
        	return "redirect:admins";
    	}
		return "error/401";
    }
}
