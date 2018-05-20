package es.uji.ei1027.sape.model;
public class ProfesorTutor extends ObjetoIdentificado
{
	private String nombre;
	private String departamento;
	private String despacho;
	private String email;
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getDepartamento()
	{
		return departamento;
	}
	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}
	public String getDespacho()
	{
		return despacho;
	}
	public void setDespacho(String despacho)
	{
		this.despacho = despacho;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
}
