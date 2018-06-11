package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.model.PeticionRevision;
public class PeticionRevisionDTO extends PeticionRevision
{
	private Usuario usuario;
	public Usuario getUsuario()
	{
		return usuario;
	}
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
}
