package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.dao.UsuarioDao;
import es.uji.ei1027.sape.model.Estudiante;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/users")
public class UserController
{
    private UsuarioDao usuarioDao;
    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao)
    {
        this.usuarioDao = usuarioDao;
    }
    @RequestMapping
    public String list(HttpSession session, Model model)
    {
    	Utils.debugLog("User LIST");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("users", usuarioDao.getAll());
            return "users/list";
    	}
        return "error/401";
    }
    @RequestMapping("/dashboard")
    public String dashboard(HttpSession session, Model model)
    {
    	Utils.debugLog("User DASHBOARD");
		Usuario user = Utils.getUser(session);
		if (user != null)
		{
			switch (user.getTipo())
			{
				case ESTUDIANTE: return "students/dashboard";
				case EMPRESA: return "companies/dashboard";
				case ADMIN: return "admins/dashboard";
			}
			return null; //Shouldn't happen...
		}
		return "error/401";
    }
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
    	Utils.debugLog("User ADD");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("student", new Estudiante());
            model.addAttribute("action", "Create");
            model.addAttribute("target", "");
            return "students/add";
    	}
		return "error/401";
    }
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog("User READ");
		Usuario user = Utils.getUser(session);
		if (user != null && (user.esAdmin() || user.getId() == id))
		{
			model.addAttribute("student", usuarioDao.get(id));
			model.addAttribute("action", "users/update");
			return "students/update";
		}
		return "error/401";
	}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String delete(@PathVariable int id)
	{
    	Utils.debugLog("User DELETE");
        usuarioDao.delete(id);
        return "redirect:..";
	}
	@RequestMapping(method=RequestMethod.DELETE)
    public String deleteAll(HttpSession session)
    {
    	Utils.debugLog("User DELETEALL");
    	usuarioDao.deleteAll();
    	return "redirect:users";
    }
}
