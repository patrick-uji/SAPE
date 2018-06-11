package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.enums.TipoUsuario;
public class Usuario extends ObjetoIdentificado
{
	public static final String SELECT_JOIN = "u.id AS u_id, u.email AS u_email, u.password AS u_password, u.id_TipoUsuario AS u_id_TipoUsuario";
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
	public boolean esAlumno()
	{
		return tipo == TipoUsuario.ALUMNO;
	}
	public boolean esEmpresa()
	{
		return tipo == TipoUsuario.EMPRESA;
	}
	public boolean esAdmin()
	{
		switch (tipo)
		{
			case CCD:
			case BTC: return true;
			default: return false;
		}
	}
	public boolean esSuperAdmin()
	{
		return tipo == TipoUsuario.BTC;
	}
}
