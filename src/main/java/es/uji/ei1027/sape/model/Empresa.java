package es.uji.ei1027.sape.model;
public class Empresa extends ObjetoIdentificado
{
	private String cif;
	private String nombre;
	private String domicilio;
	private String telefonoPrincipal;
	private int proyectosTotal;
	public String getCIF()
	{
		return cif;
	}
	public void setCIF(String cif)
	{
		this.cif = cif;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getDomicilio()
	{
		return domicilio;
	}
	public void setDomicilio(String domicilio)
	{
		this.domicilio = domicilio;
	}
	public String getTelefonoPrincipal()
	{
		return telefonoPrincipal;
	}
	public void setTelefonoPrincipal(String telefonoPrincipal)
	{
		this.telefonoPrincipal = telefonoPrincipal;
	}
	public int getProyectosTotal()
	{
		return proyectosTotal;
	}
	public void setProyectosTotal(int proyectosTotal)
	{
		this.proyectosTotal = proyectosTotal;
	}
}
