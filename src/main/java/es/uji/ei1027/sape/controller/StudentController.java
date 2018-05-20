package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.validation.StudentValidator;
import es.uji.ei1027.sape.model.Estudiante;
import es.uji.ei1027.sape.dao.EstudianteDao;
import es.uji.ei1027.sape.dao.UsuarioDao;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/students")
public class StudentController
{
	private UsuarioDao usuarioDao;
    private EstudianteDao estudianteDao;
    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
    @Autowired
    public void setEstudianteDao(EstudianteDao estudianteDao)
    {
        this.estudianteDao = estudianteDao;
    }
    @RequestMapping
    public String list(HttpSession session, Model model)
    {
    	Utils.debugLog("Students LIST");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("students", estudianteDao.getAll());
            return "students/list";
    	}
    	else
    	{
            return "error/401";
    	}
    }
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
    	Utils.debugLog("Students ADD");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("student", new Estudiante());
            model.addAttribute("action", "students/add");
            return "students/add";
    	}
    	else
    	{
    		return "error/401";
    	}
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("user") Usuario user, @ModelAttribute("student") Estudiante student, HttpSession session, BindingResult bindingResult)
    {
    	Utils.debugLog("Students CREATE");
    	if (Utils.validateUser(user, bindingResult) &&
    		Utils.validate(null, student, bindingResult))
		{
    		usuarioDao.create(user);
    		estudianteDao.create(student);
        	return "redirect:students";
    	}
    	else
    	{
    		return "students/edit";
    	}
    }
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog("Students READ");
		Usuario user = Utils.getUser(session);
		if (user != null && (user.esAdmin() || user.getId() == id))
		{
			model.addAttribute("student", estudianteDao.get(id));
			model.addAttribute("action", "students/update");
			return "students/update";
		}
		else
		{
			return "error/404";
		}
	}
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("student") Estudiante student, HttpSession session, BindingResult bindingResult)
	{
    	Utils.debugLog("Students UPDATE");
    	if (Utils.validate(new StudentValidator(), student, bindingResult))
		{
            estudianteDao.update(student);
            return "redirect:..";
		}
    	else
    	{
    		return "student/update";
    	}
	}
}
