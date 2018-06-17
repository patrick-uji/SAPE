package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.enums.Itinerario;
import es.uji.ei1027.sape.model.OfertaProyecto;
import org.springframework.jdbc.core.RowMapper;
public class OfertaProyectoMapper extends PrefixableMapper implements RowMapper<OfertaProyecto>
{
	public OfertaProyectoMapper() { }
	public OfertaProyectoMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public OfertaProyecto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	OfertaProyecto ofertaProyecto = new OfertaProyecto();
    	mapRow(ofertaProyecto, resultSet, rowNum);
        return ofertaProyecto;
    }
	public void mapRow(OfertaProyecto ofertaProyecto, ResultSet resultSet, int rowNum) throws SQLException
	{
    	ofertaProyecto.setId(resultSet.getInt(prefix + "id"));
    	ofertaProyecto.setNumero(resultSet.getInt(prefix + "numero"));
    	ofertaProyecto.setTitulo(resultSet.getString(prefix + "titulo"));
    	ofertaProyecto.setObjetivo(resultSet.getString(prefix + "objetivo"));
    	ofertaProyecto.setEstado( EstadoOferta.fromID(resultSet.getInt(prefix + "id_EstadoOferta")) );
    	ofertaProyecto.setEstadoPreAnulacion( EstadoOferta.fromID(resultSet.getInt(prefix + "id_EstadoOfertaPreAnulacion")) );
    	ofertaProyecto.setItinerario( Itinerario.fromID(resultSet.getInt(prefix + "id_Itinerario")) );
    	ofertaProyecto.setFechaAlta( Utils.formatDate(resultSet.getDate(prefix + "fechaAlta")) );
    	ofertaProyecto.setFechaUltimoCambio( Utils.formatDate(resultSet.getDate(prefix + "fechaUltimoCambio")) );
    	ofertaProyecto.setIdPersonaContacto(resultSet.getInt(prefix + "id_PersonaContacto"));
	}
}
