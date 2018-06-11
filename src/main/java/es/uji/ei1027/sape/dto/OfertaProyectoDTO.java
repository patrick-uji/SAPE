package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PersonaContacto;
public class OfertaProyectoDTO extends OfertaProyecto
{
	private Empresa empresa;
	private PersonaContacto personaContacto;
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public PersonaContacto getPersonaContacto()
	{
		return personaContacto;
	}
	public void setPersonaContacto(PersonaContacto personaContacto)
	{
		this.personaContacto = personaContacto;
	}
}
