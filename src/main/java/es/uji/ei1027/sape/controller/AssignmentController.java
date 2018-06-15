package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.dao.AsignacionDao;
import es.uji.ei1027.sape.dao.AlumnoDao;
import es.uji.ei1027.sape.dao.ProfesorTutorDao;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import org.springframework.stereotype.Controller;
import es.uji.ei1027.sape.dao.dto.AsignacionDTODao;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/assignments")
public class AssignmentController
{
	private AlumnoDao alumnoDao;
	private AsignacionDao asignacionDao;
	private ProfesorTutorDao profesorTutorDao;
	private AsignacionDTODao asignacionDTODao;
	@Autowired
	public void setAlumnoDao(AlumnoDao alumnoDao)
	{
		this.alumnoDao = alumnoDao;
	}
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao)
	{
		this.asignacionDao = asignacionDao;
	}
	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao)
	{
		this.profesorTutorDao = profesorTutorDao;
	}
	@Autowired
	public void setAsignacionDTODao(AsignacionDTODao asignacionDTODao)
	{
		this.asignacionDTODao = asignacionDTODao;
	}
	@RequestMapping
    public String list(HttpSession session, Model model)
    {
		Utils.debugLog("Assignments LIST");
		Usuario user = Utils.getUser(session);
		if (user != null)
		{
			switch (user.getTipo())
			{
				case CCD:
				case BTC:
					model.addAttribute("assignments", asignacionDao.getAll());
					return "admins/assignments/list";
				//break;
				case ALUMNO:
					model.addAttribute( "assignment", asignacionDTODao.getActiveFromStudent(user.getId()) );
					return "students/assignments/view";
				//break;
				default: return "error/401";
			}
		}
		return "error/401";
    }
	@RequestMapping("/pending")
	public String listPending(HttpSession session, Model model)
	{
		Utils.debugLog("Assignments PENDINGLIST");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("students", alumnoDao.getAllPending());
			return "admins/assignments/listPending";
		}
		return "error/401";
	}
	@RequestMapping("/{id}/accept")
	public String accept(@PathVariable("id") int id, HttpSession session)
	{
		Usuario user = Utils.getUser(session);
		if (Utils.isStudent(user))
		{
			asignacionDao.update(id, new String[] {"id_EstadoAsignacion"}, EstadoAsignacion.ACEPTADA.getID());
			return "redirect:../../assignments";
		}
		return "error/401";
	}
	@RequestMapping("/{id}/reject")
	public String reject(@PathVariable("id") int id, HttpSession session)
	{
		Usuario user = Utils.getUser(session);
		if (Utils.isStudent(user))
		{
			asignacionDao.update(id, new String[] {"id_EstadoAsignacion"}, EstadoAsignacion.ACEPTADA.getID());
			return "redirect:../../assignments";
		}
		return "error/401";
	}
    @RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("Assignments READ");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("teacher", profesorTutorDao.get(id));
			Utils.setupUpdateModel(model, id);
	        return "admins/assignments/edit";
		}
		return "error/401";
    }
    @RequestMapping(value="/{id}/update", method=RequestMethod.POST)
    public String update(@PathVariable int id, @ModelAttribute("assignment") Asignacion assignment, HttpSession session, Model model, BindingResult bindingResult)
    {
		Utils.debugLog("Assignments UPDATE[" + id + "]");
		if (Utils.isAdmin(session))
		{
			if (Utils.validate(null, assignment, bindingResult))
			{
				asignacionDao.update(assignment);
		        return "redirect:../../assignments";
			}
			else
			{
				Utils.setupUpdateModel(model, id);
		        return "admins/assignments/edit";
			}
		}
		return "error/401";
    }
    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session)
    {
		Utils.debugLog("Assignments DELETE[" + id + "]");
		if (Utils.isAdmin(session))
		{
			asignacionDao.delete(id);
	        return "redirect:../../assignments";
		}
		return "error/401";
    }
    public String deleteAll(HttpSession session)
    {
		Utils.debugLog("Assignments DELETEALL");
		if (Utils.isAdmin(session))
		{
			asignacionDao.deleteAll();
	        return "redirect:../../assignments";
		}
		return "error/401";
    }
}
