package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.dao.UsuarioDao;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.dao.AsignacionDao;
import es.uji.ei1027.sape.dao.AlumnoDao;
import es.uji.ei1027.sape.dao.ProfesorTutorDao;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import es.uji.ei1027.sape.validation.StudentValidator;
import es.uji.ei1027.sape.dao.dto.PreferenciaAlumnoDTODao;
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
    private AlumnoDao alumnoDao;
	private AsignacionDao asignacionDao;
	private ProfesorTutorDao profesorTutorDao;
	private PreferenciaAlumnoDTODao preferenciaAlumnoDTODao;
    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
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
    public void setPreferenciaAlumnoDTODao(PreferenciaAlumnoDTODao preferenciaAlumnoDTODao)
	{
		this.preferenciaAlumnoDTODao = preferenciaAlumnoDTODao;
	}
    @RequestMapping
    public String list(HttpSession session, Model model)
    {
    	Utils.debugLog("Students LIST");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("students", alumnoDao.getAll());
            return "students/list";
    	}
        return "error/401";
    }
    @RequestMapping("/add")
    public String add(HttpSession session, Model model)
    {
    	Utils.debugLog("Students ADD");
    	if (Utils.isAdmin(session))
    	{
            model.addAttribute("student", new Alumno());
            model.addAttribute("action", "students/add");
            return "students/add";
    	}
		return "error/401";
    }
	@RequestMapping("/{id}/assign")
	public String addAssignment(@PathVariable("id") int id, HttpSession session, Model model)
    {
		Utils.debugLog("Assignments ADD");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("preferences", preferenciaAlumnoDTODao.getAllFromStudent(id));
			model.addAttribute("teachers", profesorTutorDao.getAll());
			model.addAttribute("student", alumnoDao.get(id));
			model.addAttribute("assignment", new Asignacion());
	        model.addAttribute("target", "/" + id + "/assign");
	        return "admins/assignments/edit";
		}
		return "error/401";
    }
    @RequestMapping(value="/{id}/assign", method=RequestMethod.POST)
    public String createAssignment(@PathVariable("id") int id, @ModelAttribute("assignment") Asignacion assignment, HttpSession session, BindingResult bindingResult)
    {
		Utils.debugLog("Assignments CREATE");
		if (Utils.isAdmin(session))
		{
			assignment.setEstado(EstadoAsignacion.ENVIADA);
			assignment.setFechaCreacion(Utils.now());
			assignment.setFechaUltimoCambio("");
			assignment.setIDAlumno(id);
			asignacionDao.create(assignment);
	        return "redirect:../../assignments/pending";
		}
		return "error/401";
    }
    @RequestMapping(method=RequestMethod.POST)
    public String create(@ModelAttribute("user") Usuario user, @ModelAttribute("student") Alumno student, HttpSession session, BindingResult bindingResult)
    {
    	Utils.debugLog("Students CREATE");
    	if (Utils.validateUser(user, bindingResult) &&
    		Utils.validate(null, student, bindingResult))
		{
    		usuarioDao.create(user);
    		alumnoDao.create(student);
        	return "redirect:students";
    	}
		return "students/edit";
    }
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog("Students READ");
		Usuario user = Utils.getUser(session);
		if (user != null && (user.esAdmin() || user.getId() == id))
		{
			model.addAttribute("student", alumnoDao.get(id));
			model.addAttribute("action", "students/update");
			return "students/update";
		}
		return "error/404";
	}
	@RequestMapping(value="/{id}/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("student") Alumno student, HttpSession session, BindingResult bindingResult)
	{
    	Utils.debugLog("Students UPDATE");
    	if (Utils.validate(new StudentValidator(), student, bindingResult))
		{
            alumnoDao.update(student);
            return "redirect:..";
		}
		return "student/update";
	}
}
