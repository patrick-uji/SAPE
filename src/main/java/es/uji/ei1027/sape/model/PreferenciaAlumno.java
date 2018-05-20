package es.uji.ei1027.sape.model;
public class PreferenciaAlumno extends ObjetoIdentificado implements Comparable<PreferenciaAlumno>
{
	private int orden;
	private boolean abierta;
	private String fechaUltimoCambio;
	private int idOfertaProyecto;
	private int idEstudiante;
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
	public boolean getAbierta()
	{
		return abierta;
	}
	public void setAbierta(boolean abierta)
	{
		this.abierta = abierta;
	}
	public String getFechaUltimoCambio()
	{
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(String fechaUltimoCambio)
	{
		this.fechaUltimoCambio = fechaUltimoCambio;
	}
	public int getIDOfertaProyecto()
	{
		return idOfertaProyecto;
	}
	public void setIDOfertaProyecto(int idOfertaProyecto)
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
	@Override
	public int compareTo(PreferenciaAlumno o)
	{
		return Integer.compare(this.orden, o.orden);
	}
}
