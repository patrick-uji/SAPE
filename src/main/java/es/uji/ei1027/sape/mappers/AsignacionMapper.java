package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.model.Asignacion;
import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
public class AsignacionMapper implements RowMapper<Asignacion>
{
    @Override
    public Asignacion mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Asignacion asignacion = new Asignacion();
    	mapRow(asignacion, resultSet, rowNum);
        return asignacion;
    }
	public void mapRow(Asignacion asignacion, ResultSet resultSet, int rowNum) throws SQLException
	{
    	asignacion.setId(resultSet.getInt("id"));
    	asignacion.setFechaCreacion( Utils.formatDate(resultSet.getDate("fechaCreacion")) );
    	asignacion.setFechaUltimoCambio( Utils.safeFormatDate(resultSet.getDate("fechaUltimoCambio")) );
    	asignacion.setEstado( EstadoAsignacion.fromID(resultSet.getInt("id_EstadoAsignacion")) );
    	asignacion.setIdOfertaProyecto(resultSet.getInt("id_OfertaProyecto"));
    	asignacion.setIDAlumno(resultSet.getInt("id_Alumno"));
    	asignacion.setIdProfesorTutor(resultSet.getInt("id_ProfesorTutor"));
	}
}
