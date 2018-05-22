package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.model.OfertaProyecto;
import org.springframework.jdbc.core.RowMapper;
public class OfertaProyectoMapper implements RowMapper<OfertaProyecto>
{
	@Override
    public OfertaProyecto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	OfertaProyecto ofertaProyecto = new OfertaProyecto();
    	mapRow(ofertaProyecto, resultSet, rowNum);
        return ofertaProyecto;
    }
	public void mapRow(OfertaProyecto ofertaProyecto, ResultSet resultSet, int rowNum) throws SQLException
	{
    	ofertaProyecto.setId(resultSet.getInt("id"));
    	ofertaProyecto.setNumero(resultSet.getInt("numero"));
    	ofertaProyecto.setTarea(resultSet.getString("tarea"));
    	ofertaProyecto.setObjetivo(resultSet.getString("objetivo"));
    	ofertaProyecto.setEstado( EstadoOferta.fromID(resultSet.getInt("id_EstadoOferta")) );
    	ofertaProyecto.setItinerario(resultSet.getString("itinerario"));
    	ofertaProyecto.setFechaAlta(resultSet.getDate("fechaAlta").toString());
    	ofertaProyecto.setFechaUltimoCambio(resultSet.getDate("fechaUltimoCambio").toString());
    	ofertaProyecto.setIdEstancia(resultSet.getInt("id_Estancia"));
	}
}
