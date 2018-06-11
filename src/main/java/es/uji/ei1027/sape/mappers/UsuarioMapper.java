package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.enums.TipoUsuario;
import org.springframework.jdbc.core.RowMapper;
public class UsuarioMapper extends PrefixableMapper implements RowMapper<Usuario>
{
	public UsuarioMapper() { }
	public UsuarioMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public Usuario mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Usuario usuario = new Usuario();
    	usuario.setId(resultSet.getInt(prefix + "id"));
    	usuario.setEmail(resultSet.getString(prefix + "email"));
    	usuario.setPassword(resultSet.getString(prefix + "password"), false);
    	usuario.setTipo( TipoUsuario.fromID(resultSet.getInt(prefix + "id_TipoUsuario")) );
        return usuario;
    }
}
