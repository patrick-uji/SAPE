package es.uji.ei1027.sape.model;
public class PersonaContacto extends ObjetoIdentificado
{
	public static final String SELECT_JOIN = "p.id AS p_id, p.nombre AS p_nombre, p.email AS p_email, p.descripcionPracticas AS p_descripcionPracticas, p.id_Empresa AS p_id_Empresa";
	private String email;
	private String nombre;
	private int idEmpresa;
	private String descripcionPracticas;
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public int getIDEmpresa()
	{
		return idEmpresa;
	}
	public void setIDEmpresa(int idEmpresa)
	{
		this.idEmpresa = idEmpresa;
	}
	public String getDescripcionPracticas()
	{
		return descripcionPracticas;
	}
	public void setDescripcionPracticas(String descripcionPracticas)
	{
		this.descripcionPracticas = descripcionPracticas;
	}
}
