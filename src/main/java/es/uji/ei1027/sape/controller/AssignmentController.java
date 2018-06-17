package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.model.ProfesorTutor;
import es.uji.ei1027.sape.dao.AsignacionDao;
import es.uji.ei1027.sape.dao.OfertaProyectoDao;
import es.uji.ei1027.sape.dao.AlumnoDao;
import es.uji.ei1027.sape.dao.ProfesorTutorDao;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.enums.EstadoOferta;

import org.springframework.stereotype.Controller;
import es.uji.ei1027.sape.dao.dto.AsignacionDTODao;
import es.uji.ei1027.sape.dao.dto.PreferenciaAlumnoDTODao;

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
	public void setAsignacionDTODao(AsignacionDTODao asignacionDTODao)
	{
		this.asignacionDTODao = asignacionDTODao;
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
		Utils.debugLog("Assignments LIST");
		Usuario user = Utils.getUser(session);
		if (user != null)
		{
			switch (user.getTipo())
			{
				case CCD:
				case BTC:
					model.addAttribute("assignments", asignacionDTODao.getAll());
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
			unlinkAssignment(id, EstadoAsignacion.RECHAZADA);
			return "redirect:../../assignments";
		}
		return "error/401";
	}
	private void unlinkAssignment(int assignmentID, EstadoAsignacion newStatus)
	{
		Asignacion assignment = asignacionDao.get(assignmentID);
		ofertaProyectoDao.update(assignment.getIdOfertaProyecto(), new String[] {"id_EstadoOferta"}, EstadoOferta.VISIBLE.getID());
		asignacionDao.update(assignmentID, new String[] {"id_EstadoAsignacion"}, newStatus.getID());
	}
	@RequestMapping("/{id}/cancel")
	public String cancel(@PathVariable("id") int id, HttpSession session)
	{
		if (Utils.isSuperAdmin(session))
		{
			unlinkAssignment(id, EstadoAsignacion.ANULADA);
			return "redirect:../../assignments";
		}
		return "error/401";
	}
    @RequestMapping("/{id}")
    public String read(@PathVariable int id, HttpSession session, Model model)
    {
		Utils.debugLog("Assignments READ");
		Usuario user = Utils.getUser(session);
		if (Utils.isAdmin(user))
		{
			Asignacion assignment = asignacionDao.get(id);
			if (user.esSuperAdmin())
			{
				model.addAttribute( "preferences", preferenciaAlumnoDTODao.getAllAvailableFromStudent(assignment.getIDAlumno()) );
		        model.addAttribute("target", "/assignments/" + id + "/update");
				model.addAttribute("teachers", profesorTutorDao.getAll());
		        model.addAttribute("action", "Actualizar");
			}
			else //user == CCD
			{
		        model.addAttribute("action", "Ver");
			}
			model.addAttribute( "teacher", profesorTutorDao.get(assignment.getIdProfesorTutor()) );
			model.addAttribute( "student", alumnoDao.get(assignment.getIDAlumno()) );
			model.addAttribute("assignment", assignment);
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
			Asignacion oldAssignment = asignacionDao.get(id);
			int newAssignmentOfferID = assignment.getIdOfertaProyecto();
			int oldAssignmentOfferID = oldAssignment.getIdOfertaProyecto();
			if (newAssignmentOfferID != oldAssignmentOfferID)
			{
				ofertaProyectoDao.update(newAssignmentOfferID, new String[] {"id_EstadoOferta"}, EstadoOferta.ASIGNADA.getID());
				ofertaProyectoDao.update(oldAssignmentOfferID, new String[] {"id_EstadoOferta"}, EstadoOferta.VISIBLE.getID());
			}
			assignment.setFechaCreacion(oldAssignment.getFechaCreacion());
			assignment.setIDAlumno(oldAssignment.getIDAlumno());
			assignment.setEstado(EstadoAsignacion.ENVIADA);
			assignment.setFechaUltimoCambio(Utils.now());
			asignacionDao.update(assignment);
	        return "redirect:../../assignments";
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
