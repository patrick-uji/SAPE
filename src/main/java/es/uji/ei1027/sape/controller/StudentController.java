package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.dao.AlumnoDao;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.dao.AsignacionDao;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dao.ProfesorTutorDao;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.enums.EstadoOferta;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    private AlumnoDao alumnoDao;
	private AsignacionDao asignacionDao;
    private ProfesorTutorDao profesorTutorDao;
    private OfertaProyectoDao ofertaProyectoDao;
    private PreferenciaAlumnoDTODao preferenciaAlumnoDTODao;
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
    public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao)
	{
		this.ofertaProyectoDao = ofertaProyectoDao;
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
	@RequestMapping("/{id}/assign")
	public String addAssignment(@PathVariable("id") int id, HttpSession session, Model model)
    {
		Utils.debugLog("Assignments ADD");
		if (Utils.isAdmin(session))
		{
			model.addAttribute("preferences", preferenciaAlumnoDTODao.getAllAvailableFromStudent(id));
	        model.addAttribute("target", "/students/" + id + "/assign");
			model.addAttribute("teachers", profesorTutorDao.getAll());
			model.addAttribute("assignment", new Asignacion());
			model.addAttribute("student", alumnoDao.get(id));
	        model.addAttribute("action", "Crear");
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
			ofertaProyectoDao.update(assignment.getIdOfertaProyecto(), new String[] {"id_EstadoOferta"}, EstadoOferta.ASIGNADA.getID());
			assignment.setEstado(EstadoAsignacion.ENVIADA);
			assignment.setFechaCreacion(Utils.now());
			assignment.setFechaUltimoCambio("");
			assignment.setIDAlumno(id);
			asignacionDao.create(assignment);
	        return "redirect:../../assignments/pending";
		}
		return "error/401";
    }
	@RequestMapping("/{id}")
	public String read(@PathVariable int id, HttpSession session, Model model)
	{
    	Utils.debugLog("Students READ");
		return "error/404";
	}
}
