package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.model.Asignacion;
import org.springframework.jdbc.core.RowMapper;
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
    	asignacion.setFechaPropuesta(resultSet.getDate("fechaPropuesta").toString());
    	asignacion.setFechaAceptacion( Utils.safeToString(resultSet.getDate("fechaAceptacion")) );
    	asignacion.setFechaRechazo( Utils.safeToString(resultSet.getDate("fechaRechazo")) );
    	asignacion.setFechaTraspasoIGLU( Utils.safeToString(resultSet.getDate("fechaTraspasoIGLU")) );
    	asignacion.setComentarioCambio(resultSet.getString("comentarioCambio"));
    	asignacion.setEstado( EstadoAsignacion.fromID(resultSet.getInt("id_EstadoAsignacion")) );
    	asignacion.setIdOfertaProyecto(resultSet.getInt("id_OfertaProyecto"));
    	asignacion.setIDEstudiante(resultSet.getInt("id_Estudiante"));
    	asignacion.setIdProfesorTutor(resultSet.getInt("id_ProfesorTutor"));
	}
}
