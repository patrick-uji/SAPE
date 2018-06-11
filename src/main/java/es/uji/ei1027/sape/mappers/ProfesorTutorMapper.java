package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.ProfesorTutor;
import org.springframework.jdbc.core.RowMapper;
public class ProfesorTutorMapper extends PrefixableMapper implements RowMapper<ProfesorTutor>
{
	public ProfesorTutorMapper() { }
	public ProfesorTutorMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public ProfesorTutor mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		ProfesorTutor profesorTutor = new ProfesorTutor();
    	profesorTutor.setId(resultSet.getInt(prefix + "id"));
    	profesorTutor.setNombre(resultSet.getString(prefix + "nombre"));
    	profesorTutor.setDepartamento(resultSet.getString(prefix + "departamento"));
    	profesorTutor.setDespacho(resultSet.getString(prefix + "despacho"));
    	profesorTutor.setEmail(resultSet.getString(prefix + "email"));
        return profesorTutor;
    }
}
