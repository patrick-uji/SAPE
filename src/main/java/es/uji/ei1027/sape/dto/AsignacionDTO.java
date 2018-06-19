package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.model.ProfesorTutor;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PersonaContacto;
public class AsignacionDTO extends Asignacion
{
	private Alumno alumno;
	private Empresa empresa;
	private ProfesorTutor profesorTutor;
	private OfertaProyecto ofertaProyecto;
	private PersonaContacto personaContacto;
	public Alumno getAlumno()
	{
		return alumno;
	}
	public void setAlumno(Alumno alumno)
	{
		this.alumno = alumno;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public ProfesorTutor getProfesorTutor()
	{
		return profesorTutor;
	}
	public void setProfesorTutor(ProfesorTutor profesorTutor)
	{
		this.profesorTutor = profesorTutor;
	}
	public OfertaProyecto getOfertaProyecto()
	{
		return ofertaProyecto;
	}
	public void setOfertaProyecto(OfertaProyecto ofertaProyecto)
	{
		this.ofertaProyecto = ofertaProyecto;
	}
	public PersonaContacto getPersonaContacto()
	{
		return personaContacto;
	}
	public void setPersonaContacto(PersonaContacto personaContacto)
	{
		this.personaContacto = personaContacto;
	}
}
