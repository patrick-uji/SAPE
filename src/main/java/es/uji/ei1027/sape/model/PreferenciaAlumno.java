package es.uji.ei1027.sape.model;
public class PreferenciaAlumno extends ObjetoIdentificado implements Comparable<PreferenciaAlumno>
{
	private int orden;
	private String fechaUltimoCambio;
	private int idOfertaProyecto;
	private int idAlumno;
	public int getOrden()
	{
		return orden;
	}
	public void setOrden(int orden)
	{
		this.orden = orden;
	}
	public void incrementOrden()
	{
		orden++;
	}
	public void decrementOrden()
	{
		orden--;
	}
	public String getFechaUltimoCambio()
	{
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(String fechaUltimoCambio)
	{
		this.fechaUltimoCambio = fechaUltimoCambio;
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
	@Override
	public int compareTo(PreferenciaAlumno other)
	{
		return Integer.compare(this.orden, other.orden);
	}
}
