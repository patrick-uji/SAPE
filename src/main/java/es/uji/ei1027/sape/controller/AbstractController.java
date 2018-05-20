package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.dao.AdminDao;
import es.uji.ei1027.sape.dao.UsuarioDao;
import es.uji.ei1027.sape.dao.AbstractDao;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
public abstract class AbstractController<T extends ObjetoIdentificado>
{
    private AdminDao adminDao;
    private UsuarioDao usuarioDao;
    private String tNamePlural;
    private AbstractDao<T> dao;
    public AbstractController()
    {
    	this.tNamePlural = Utils.getTClass(this).getSimpleName().toLowerCase() + "s";
    }
    @RequestMapping("")
    public String list(HttpSession session, Model model)
    {
    	Utils.debugLog(tNamePlural + " LIST");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute(tNamePlural, dao.getAll());
            return tNamePlural + "/list";
    	}
    	else
    	{
            return "error/401";
    	}
    }
    @RequestMapping("/add")
    public abstract String add(HttpSession session, Model model);
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog(tNamePlural + " READ");
		T resource = dao.get(id);
		
		if (resource != null)
		{
			model.addAttribute("student", adminDao.get(id));
			model.addAttribute("action", "students/update");
			return "admins/edit";
		}
		else
		{
			return "error/404";
		}
	}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String delete(@PathVariable int id)
	{
    	Utils.debugLog(tNamePlural + " DELETE");
        dao.delete(id);
        return "redirect:..";
	}
	@RequestMapping(method=RequestMethod.DELETE)
    public String deleteAll(HttpSession session)
    {
    	Utils.debugLog("Students DELETEALL");
    	dao.deleteAll();
    	return "redirect:" + tNamePlural;
    }
}
