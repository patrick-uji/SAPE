package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.sape.model.PeticionRevision;
public class PeticionRevisionMapper implements RowMapper<PeticionRevision>
{
	@Override
    public PeticionRevision mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	PeticionRevision peticionRevision = new PeticionRevision();
    	mapRow(peticionRevision, resultSet, rowNum);
        return peticionRevision;
    }
	public void mapRow(PeticionRevision peticionRevision, ResultSet resultSet, int rowNum) throws SQLException
	{
    	peticionRevision.setId(resultSet.getInt("id"));
    	peticionRevision.setFecha( Utils.formatDate(resultSet.getDate("fecha")) );
    	peticionRevision.setTextoPeticion(resultSet.getString("textoPeticion"));
    	peticionRevision.setIDOfertaProyect(resultSet.getInt("id_OfertaProyecto"));
	}
}
