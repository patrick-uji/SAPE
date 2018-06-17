package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.model.PeticionRevision;
import es.uji.ei1027.sape.mappers.PeticionRevisionMapper;
@Component
public class PeticionRevisionDao extends AbstractDao<PeticionRevision>
{
	private PeticionRevisionMapper rowMapper;
	public PeticionRevisionDao()
	{
		this.rowMapper = new PeticionRevisionMapper();
	}
	@Override
    public PeticionRevision mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		return rowMapper.mapRow(resultSet, rowNum);
    }
    public List<PeticionRevision> getAllFromOffer(int offerID)
    {
    	return jdbcTemplate.query("SELECT * FROM PeticionRevision WHERE id_OfertaProyecto = ? ORDER BY id DESC", new Object[] {offerID}, this);
    }
    @Override
    public void create(PeticionRevision peticionRevision)
    {
        jdbcTemplate.update("INSERT INTO PeticionRevision (fecha, textoPeticion, id_OfertaProyecto, id_Admin) VALUES (?,?,?,?)",
        					Utils.stringToDate(peticionRevision.getFecha()), peticionRevision.getTextoPeticion(),
        					peticionRevision.getIDOfertaProyecto(), peticionRevision.getIDAdmin());
    }
    @Override
    public void update(PeticionRevision peticionRevision)
    {
        jdbcTemplate.update("UPDATE PeticionRevision SET fecha = ?, textoPeticion = ?, id_OfertaProyecto = ?, id_Admin = ? WHERE id = ?",
        					Utils.stringToDate(peticionRevision.getFecha()), peticionRevision.getTextoPeticion(),
        					peticionRevision.getIDOfertaProyecto(), peticionRevision.getIDAdmin(),
        					peticionRevision.getId());
    }
}
