package es.uji.ei1027.sape.model;
public class Estancia extends ObjetoIdentificado
{
	private String personaContacto;
	private String emailPersonaContacto;
	private String descripcionPracticas;
	private int idEmpresa;
	public String getPersonaContacto()
	{
		return personaContacto;
	}
	public void setPersonaContacto(String personaContacto)
	{
		this.personaContacto = personaContacto;
	}
	public String getEmailPersonaContacto()
	{
		return emailPersonaContacto;
	}
	public void setEmailPersonaContacto(String emailPersonaContacto)
	{
		this.emailPersonaContacto = emailPersonaContacto;
	}
	public String getDescripcionPracticas()
	{
		return descripcionPracticas;
	}
	public void setDescripcionPracticas(String descripcionPracticas)
	{
		this.descripcionPracticas = descripcionPracticas;
	}
	public int getIDEmpresa()
	{
		return idEmpresa;
	}
	public void setIDEmpresa(int idEmpresa)
	{
		this.idEmpresa = idEmpresa;
	}
}
