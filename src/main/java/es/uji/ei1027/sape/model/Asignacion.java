package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
public class Asignacion extends ObjetoIdentificado
{
	private String fechaCreacion;
	private String fechaUltimoCambio;
	private EstadoAsignacion estado;
	private int idOfertaProyecto;
	private int idAlumno;
	private int idProfesorTutor;
	public String getFechaCreacion()
	{
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaUltimoCambio()
	{
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(String fechaUltimoCambio)
	{
		this.fechaUltimoCambio = fechaUltimoCambio;
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
	public int getIDAlumno()
	{
		return idAlumno;
	}
	public void setIDAlumno(int idAlumno)
	{
		this.idAlumno = idAlumno;
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
