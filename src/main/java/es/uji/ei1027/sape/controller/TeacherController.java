package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.ProfesorTutor;
import es.uji.ei1027.sape.dao.ProfesorTutorDao;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/teachers")
public class TeacherController
{
	private ProfesorTutorDao profesorTutorDao;
	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao)
	{
		this.profesorTutorDao = profesorTutorDao;
	}
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("Teachers LIST");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("teachers", profesorTutorDao.getAll());
			return "admins/teachers/list";
		}
		return "error/401";
    }
	@RequestMapping("/add")
	public String add(HttpSession session, Model model)
    {
		Utils.debugLog("Teachers ADD");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("teacher", new ProfesorTutor());
	        model.addAttribute("action", "Crear");
	        model.addAttribute("target", "");
	        return "admins/teachers/edit";
		}
		return "error/401";
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("teacher") ProfesorTutor teacher, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("TutorTeacher CREATE");
		if (Utils.isAdmin(session))
		{
			profesorTutorDao.create(teacher);
	        return "redirect:teachers";
		}
		return "error/401";
    }
    @RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("Teachers READ");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("teacher", profesorTutorDao.get(id));
	        model.addAttribute("target", "/" + id + "/update");
	        model.addAttribute("action", "Actualizar");
	        return "admins/teachers/edit";
		}
		return "error/401";
    }
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable int id, @ModelAttribute("teacher") ProfesorTutor teacher, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("Offers UPDATE[" + id + "]");
		if (Utils.isAdmin(session))
		{
	        //if(bindingResult.hasErrors()) return "offers/update";
	        profesorTutorDao.update(teacher);
	        return "redirect:../../teachers";
		}
		return "error/401";
    }
    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session)
    {
		Utils.debugLog("Teachers DELETE[" + id + "]");
		if (Utils.isAdmin(session))
		{
			profesorTutorDao.delete(id);
	        return "redirect:../../teachers";
		}
		return "error/401";
    }
    public String deleteAll(HttpSession session)
    {
		Utils.debugLog("Teachers DELETEALL");
		if (Utils.isAdmin(session))
		{
	        profesorTutorDao.deleteAll();
	        return "redirect:../../offers";
		}
		return "error/401";
    }
}
