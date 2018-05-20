package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.sape.model.PreferenciaAlumno;
public class PreferenciaAlumnoMapper implements RowMapper<PreferenciaAlumno>
{
	@Override
    public PreferenciaAlumno mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	PreferenciaAlumno preferenciaAlumno = new PreferenciaAlumno();
    	mapRow(preferenciaAlumno, resultSet, rowNum);
        return preferenciaAlumno;
    }
	public void mapRow(PreferenciaAlumno preferenciaAlumno, ResultSet resultSet, int rowNum) throws SQLException
	{
    	preferenciaAlumno.setId(resultSet.getInt("id"));
    	preferenciaAlumno.setOrden(resultSet.getInt("orden"));
    	preferenciaAlumno.setAbierta(resultSet.getBoolean("abierta"));
    	preferenciaAlumno.setFechaUltimoCambio(resultSet.getDate("fechaUltimoCambio").toString());
    	preferenciaAlumno.setIDOfertaProyecto(resultSet.getInt("id_OfertaProyecto"));
    	preferenciaAlumno.setIDEstudiante(resultSet.getInt("id_Estudiante"));
	}
}
