package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.sape.model.PersonaContacto;
public class PersonaContactoMapper extends PrefixableMapper implements RowMapper<PersonaContacto>
{
	public PersonaContactoMapper() { }
	public PersonaContactoMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public PersonaContacto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	PersonaContacto personaContacto = new PersonaContacto();
    	personaContacto.setId(resultSet.getInt(prefix + "id"));
    	personaContacto.setNombre(resultSet.getString(prefix + "nombre"));
    	personaContacto.setEmail(resultSet.getString(prefix + "email"));
    	personaContacto.setDescripcionPracticas(resultSet.getString(prefix + "descripcionPracticas"));
    	personaContacto.setIDEmpresa(resultSet.getInt(prefix + "id_Empresa"));
        return personaContacto;
    }
}
