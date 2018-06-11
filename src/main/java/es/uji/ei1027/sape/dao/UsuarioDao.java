package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.mappers.UsuarioMapper;
import org.springframework.stereotype.Component;
import org.springframework.dao.EmptyResultDataAccessException;
@Component
public class UsuarioDao extends AbstractDao<Usuario>
{
	private UsuarioMapper rowMapper;
	public UsuarioDao()
	{
		this.rowMapper = new UsuarioMapper();
	}
	@Override
    public Usuario mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		return rowMapper.mapRow(resultSet, rowNum);
    }
    public Usuario get(String email)
    {
    	try
    	{
    		return jdbcTemplate.queryForObject("SELECT * FROM Usuario WHERE email = ?", new Object[] {email.toLowerCase()}, this);
    	}
    	catch (EmptyResultDataAccessException ex)
    	{
    		return null;
    	}
    }
    @Override
    public void create(Usuario model)
    {
        jdbcTemplate.update("INSERT INTO Usuario (email, password, id_TipoUsuario) VALUES (?,?,?)",
        					model.getEmail(), model.getPassword(), model.getTipo().getID());
    }
    public void update(Usuario model)
    {
        jdbcTemplate.update("UPDATE Usuario SET email = ?, password = ?, id_TipoUsuario = ? WHERE id = ?",
        					model.getEmail(), model.getPassword(), model.getTipo().getID(),
        					model.getId());
    }
}
