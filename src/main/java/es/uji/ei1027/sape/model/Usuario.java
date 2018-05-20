package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.enums.TipoUsuario;
public class Usuario extends ObjetoIdentificado
{
	private String email;
	private String password;
	private TipoUsuario tipo;
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password, boolean hash)
	{
		if (hash)
		{
			this.password = Utils.hashPassword(password);
		}
		else
		{
			this.password = password;
		}
	}
	public TipoUsuario getTipo()
	{
		return tipo;
	}
	public void setTipo(TipoUsuario tipo)
	{
		this.tipo = tipo;
	}
	public boolean esEstudiante()
	{
		return tipo == TipoUsuario.ESTUDIANTE;
	}
	public boolean esEmpresa()
	{
		return tipo == TipoUsuario.EMPRESA;
	}
	public boolean esAdmin()
	{
		return tipo == TipoUsuario.ADMIN;
	}
}
