package es.uji.ei1027.sape.model;
public class Empresa extends ObjetoIdentificado
{
	public static final String SELECT_JOIN = "e.id AS e_id, e.cif AS e_cif, e.nombre AS e_nombre, e.proyectosTotal AS e_proyectosTotal, e.domicilio AS e_domicilio, e.telefonoPersonal AS e_telefonoPersonal";
	public static final String FROM_OFFER_JOIN = "JOIN PersonaContacto AS p ON o.id_PersonaContacto = p.id JOIN Empresa AS e ON p.id_Empresa = e.id";
	private String cif;
	private String nombre;
	private String domicilio;
	private int proyectosTotal;
	private String telefonoPersonal;
	public String getCif()
	{
		return cif;
	}
	public void setCif(String cif)
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
	public int getProyectosTotal()
	{
		return proyectosTotal;
	}
	public void setProyectosTotal(int proyectosTotal)
	{
		this.proyectosTotal = proyectosTotal;
	}
	public String getDomicilio()
	{
		return domicilio;
	}
	public void setDomicilio(String domicilio)
	{
		this.domicilio = domicilio;
	}
	public String getTelefonoPersonal()
	{
		return telefonoPersonal;
	}
	public void setTelefonoPersonal(String telefonoPersonal)
	{
		this.telefonoPersonal = telefonoPersonal;
	}
}
