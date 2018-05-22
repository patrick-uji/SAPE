package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
public class Asignacion extends ObjetoIdentificado
{
	private String fechaPropuesta;
	private String fechaAceptacion;
	private String fechaRechazo;
	private String fechaTraspasoIGLU;
	private String comentarioCambio; //TODO: renombrar a comentarioPeticion?
	private EstadoAsignacion estado;
	private int idOfertaProyecto;
	private int idEstudiante;
	private int idProfesorTutor;
	public String getFechaPropuesta()
	{
		return fechaPropuesta;
	}
	public void setFechaPropuesta(String fechaPropuesta)
	{
		this.fechaPropuesta = fechaPropuesta;
	}
	public String getFechaAceptacion()
	{
		return fechaAceptacion;
	}
	public void setFechaAceptacion(String fechaAceptacion)
	{
		this.fechaAceptacion = fechaAceptacion;
	}
	public String getFechaRechazo()
	{
		return fechaRechazo;
	}
	public void setFechaRechazo(String fechaRechazo)
	{
		this.fechaRechazo = fechaRechazo;
	}
	public String getFechaTraspasoIGLU()
	{
		return fechaTraspasoIGLU;
	}
	public void setFechaTraspasoIGLU(String fechaTraspasoIGLU)
	{
		this.fechaTraspasoIGLU = fechaTraspasoIGLU;
	}
	public String getComentarioCambio()
	{
		return comentarioCambio;
	}
	public void setComentarioCambio(String comentarioCambio)
	{
		this.comentarioCambio = comentarioCambio;
	}
	public EstadoAsignacion getEstado()
	{
		return estado;
	}
	public void setEstado(EstadoAsignacion estado)
	{
		this.estado = estado;
	}
	public int getIdOfertaProyecto()
	{
		return idOfertaProyecto;
	}
	public void setIdOfertaProyecto(int idOfertaProyecto)
	{
		this.idOfertaProyecto = idOfertaProyecto;
	}
	public int getIDEstudiante()
	{
		return idEstudiante;
	}
	public void setIDEstudiante(int idEstudiante)
	{
		this.idEstudiante = idEstudiante;
	}
	public int getIdProfesorTutor()
	{
		return idProfesorTutor;
	}
	public void setIdProfesorTutor(int idProfesorTutor)
	{
		this.idProfesorTutor = idProfesorTutor;
	}
}
