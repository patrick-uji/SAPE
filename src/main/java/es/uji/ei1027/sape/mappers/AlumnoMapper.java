package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.enums.Itinerario;
import org.springframework.jdbc.core.RowMapper;
public class AlumnoMapper extends PrefixableMapper implements RowMapper<Alumno>
{
	public AlumnoMapper() { }
	public AlumnoMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public Alumno mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Alumno alumno = new Alumno();
    	alumno.setId(resultSet.getInt(prefix + "id"));
    	alumno.setDni(resultSet.getString(prefix + "dni"));
    	alumno.setNombre(resultSet.getString(prefix + "nombre"));
    	alumno.setNotaMedia(resultSet.getFloat(prefix + "notaMedia"));
    	alumno.setItinerario( Itinerario.fromID(resultSet.getInt(prefix + "id_Itinerario")) );
    	alumno.setNumeroCreditos(resultSet.getInt(prefix + "numeroCreditos"));
    	alumno.setAsignaturasPendientes(resultSet.getInt(prefix + "asignaturasPendientes"));
    	alumno.setSemestreInicioEstancia(resultSet.getInt(prefix + "semestreInicioEstancia"));
        return alumno;
    }
}
