package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.model.PeticionRevision;
@Component
public class PeticionRevisionDao extends AbstractDao<PeticionRevision>
{
	@Override
    public PeticionRevision mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	PeticionRevision peticionRevision = new PeticionRevision();
    	peticionRevision.setId(resultSet.getInt("id"));
    	peticionRevision.setFecha(resultSet.getDate("fecha").toString());
    	peticionRevision.setTextoPeticion(resultSet.getString("textoPeticion"));
    	peticionRevision.setIDOfertaProyect(resultSet.getInt("id_OfertaProyecto"));
        return peticionRevision;
    }
    public List<PeticionRevision> getAllFromOffer(int offerID)
    {
    	return jdbcTemplate.query("SELECT * FROM PeticionRevision WHERE id_OfertaProyecto = ?", new Object[] {offerID}, this);
    }
    @Override
    public void create(PeticionRevision peticionRevision)
    {
        jdbcTemplate.update("INSERT INTO PeticionRevision (fecha, textoPeticion, id_OfertaProyecto) VALUES (?,?,?)",
        					Utils.stringToDate(peticionRevision.getFecha()), peticionRevision.getTextoPeticion(), peticionRevision.getIDOfertaProyecto());
    }
    @Override
    public void update(PeticionRevision peticionRevision)
    {
        jdbcTemplate.update("UPDATE PeticionRevision SET fecha = ?, textoPeticion = ? WHERE id = ?",
        					Utils.stringToDate(peticionRevision.getFecha()), peticionRevision.getTextoPeticion(), peticionRevision.getIDOfertaProyecto(),
        					peticionRevision.getId());
    }
}
