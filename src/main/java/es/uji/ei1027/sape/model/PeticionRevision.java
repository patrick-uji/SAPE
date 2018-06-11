package es.uji.ei1027.sape.model;
public class PeticionRevision extends ObjetoIdentificado
{
	private String fecha;
	private String textoPeticion;
	private int idOfertaProyecto;
	private int idAdmin;
	public String getFecha()
	{
		return fecha;
	}
	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}
	public String getTextoPeticion()
	{
		return textoPeticion;
	}
	public void setTextoPeticion(String textoPeticion)
	{
		this.textoPeticion = textoPeticion;
	}
	public int getIDOfertaProyecto()
	{
		return idOfertaProyecto;
	}
	public void setIDOfertaProyect(int idOfertaProyecto)
	{
		this.idOfertaProyecto = idOfertaProyecto;
	}
	public int getIDAdmin()
	{
		return idAdmin;
	}
	public void setIDAdmin(int idAdmin)
	{
		this.idAdmin = idAdmin;
	}
}
